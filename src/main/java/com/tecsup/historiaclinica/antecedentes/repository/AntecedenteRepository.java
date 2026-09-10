package com.tecsup.historiaclinica.antecedentes.repository;

import com.tecsup.historiaclinica.antecedentes.entity.Antecedente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AntecedenteRepository extends JpaRepository<Antecedente, Long> {
    List<Antecedente> findByHistoriaClinicaId(Long historiaClinicaId);
}
