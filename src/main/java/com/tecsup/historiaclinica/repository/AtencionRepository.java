package com.tecsup.historiaclinica.repository;

import com.tecsup.historiaclinica.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Long> {

    List<Atencion> findByHistoriaClinicaIdOrderByFechaDesc(Long historiaClinicaId);
}