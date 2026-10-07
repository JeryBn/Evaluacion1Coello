package com.tecsup.historiaclinica.auditoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditoriaService {
    private final AuditoriaRepository repository;
    public AuditoriaService(AuditoriaRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public Page<AuditoriaRegistro> listar(int pagina) {
        return repository.findAll(PageRequest.of(Math.max(0, pagina), 25, Sort.by("id").descending()));
    }
}
