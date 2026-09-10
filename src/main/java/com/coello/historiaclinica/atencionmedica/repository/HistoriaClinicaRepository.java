package com.coello.historiaclinica.atencionmedica.repository;

import com.coello.historiaclinica.atencionmedica.entity.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("atencionHistoriaClinicaRepository")
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
    Optional<HistoriaClinica> findByPacienteId(Long pacienteId);
    boolean existsByNumeroHistoria(String numeroHistoria);
}
