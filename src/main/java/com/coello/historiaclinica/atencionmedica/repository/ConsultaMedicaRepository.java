package com.coello.historiaclinica.atencionmedica.repository;

import com.coello.historiaclinica.atencionmedica.entity.ConsultaMedica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaMedicaRepository extends JpaRepository<ConsultaMedica, Long> {
    List<ConsultaMedica> findByHistoriaClinicaIdOrderByFechaAtencionDesc(Long historiaClinicaId);
}
