package com.tecsup.historiaclinica.usuarios;
import org.springframework.data.jpa.repository.*;
import jakarta.persistence.LockModeType;
import java.util.Optional;
public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByCodigo(String codigo);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Rol r where r.codigo = 'ADMINISTRADOR'")
    Optional<Rol> bloquearAdministrador();
}
