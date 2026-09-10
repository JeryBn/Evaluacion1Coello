package com.historiaclinica.repository;

import com.historiaclinica.model.Antecedente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AntecedenteRepository extends JpaRepository<Antecedente, Long> {

}