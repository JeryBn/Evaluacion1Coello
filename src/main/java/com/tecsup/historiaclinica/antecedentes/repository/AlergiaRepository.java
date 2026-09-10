package com.tecsup.historiaclinica.antecedentes.repository;

import com.tecsup.historiaclinica.antecedentes.entity.Alergia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlergiaRepository extends JpaRepository<Alergia, Long> {
    List<Alergia> findByHistoriaClinicaId(Long historiaClinicaId);
}
