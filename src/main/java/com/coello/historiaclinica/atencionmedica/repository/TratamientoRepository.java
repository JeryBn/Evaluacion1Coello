package com.coello.historiaclinica.atencionmedica.repository;

import com.coello.historiaclinica.atencionmedica.entity.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    List<Tratamiento> findByConsultaMedicaId(Long consultaMedicaId);
}
