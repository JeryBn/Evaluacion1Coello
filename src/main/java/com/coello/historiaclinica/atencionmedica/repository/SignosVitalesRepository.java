package com.coello.historiaclinica.atencionmedica.repository;

import com.coello.historiaclinica.atencionmedica.entity.SignosVitales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SignosVitalesRepository extends JpaRepository<SignosVitales, Long> {
    Optional<SignosVitales> findByConsultaMedicaId(Long consultaMedicaId);
}
