package com.historiaclinica.service;

import com.historiaclinica.model.HistoriaClinica;
import com.historiaclinica.repository.HistoriaClinicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class HistoriaClinicaService {


    private final HistoriaClinicaRepository repository;


    public HistoriaClinicaService(HistoriaClinicaRepository repository){

        this.repository = repository;

    }



    public List<HistoriaClinica> listar(){

        return repository.findAll();

    }



    public Optional<HistoriaClinica> buscarPorId(Long id){

        return repository.findById(id);

    }



    public HistoriaClinica guardar(HistoriaClinica historiaClinica){

        return repository.save(historiaClinica);

    }



    public void eliminar(Long id){

        repository.deleteById(id);

    }

}