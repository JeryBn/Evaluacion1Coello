package com.coello.historiaclinica.atencionmedica.repository;

import com.coello.historiaclinica.atencionmedica.entity.EvolucionMedica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvolucionMedicaRepository extends JpaRepository<EvolucionMedica, Long> {
    List<EvolucionMedica> findByConsultaMedicaIdOrderByFechaEvolucionDesc(Long consultaMedicaId);
}
