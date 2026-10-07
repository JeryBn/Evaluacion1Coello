package com.tecsup.historiaclinica.auditoria;

import jakarta.persistence.EntityManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Order(0)
public class AuditoriaAspect {
    private final AuditoriaRepository repository;
    private final EntityManager entityManager;
    private final TransactionTemplate transaction;

    public AuditoriaAspect(AuditoriaRepository repository, EntityManager entityManager,
                          PlatformTransactionManager manager) {
        this.repository = repository;
        this.entityManager = entityManager;
        this.transaction = new TransactionTemplate(manager);
    }

    @Around("@annotation(auditar)")
    public Object registrar(ProceedingJoinPoint invocation, Auditar auditar) throws Throwable {
        try {
            return transaction.execute(status -> {
                Operacion operacion = auditar.operacion();
                if (!auditar.buscarExistentePor().isEmpty()) {
                    // La propiedad proviene de la anotacion del codigo, no de entrada HTTP.
                    var existentes = entityManager.createQuery("select e.id from "
                            + auditar.entidad().getSimpleName() + " e where e."
                            + auditar.buscarExistentePor() + " = :id", Long.class)
                            .setParameter("id", invocation.getArgs()[0]).setMaxResults(1).getResultList();
                    if (!existentes.isEmpty()) operacion = Operacion.MODIFICACION;
                }
                Object resultado;
                try {
                    resultado = invocation.proceed();
                } catch (Throwable error) {
                    throw new FalloOperacion(error);
                }
                Long id = auditar.idArgumento() >= 0
                        ? (Long) invocation.getArgs()[auditar.idArgumento()]
                        : (Long) entityManager.getEntityManagerFactory().getPersistenceUnitUtil()
                                .getIdentifier(resultado);
                repository.save(new AuditoriaRegistro(usuarioActual(), operacion,
                        auditar.entidad().getSimpleName(), id));
                return resultado;
            });
        } catch (FalloOperacion error) {
            throw error.getCause();
        }
    }

    private String usuarioActual() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
            var principal = attributes.getRequest().getUserPrincipal();
            if (principal != null) return principal.getName();
        }
        return "NO_AUTENTICADO";
    }

    private static class FalloOperacion extends RuntimeException {
        FalloOperacion(Throwable cause) { super(cause); }
    }
}
