package com.historiaclinica.repository;

import com.historiaclinica.model.Alergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlergiaRepository extends JpaRepository<Alergia, Long> {

}