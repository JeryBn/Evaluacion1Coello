package com.historiaclinica.service;

import com.historiaclinica.model.Paciente;
import com.historiaclinica.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PacienteService {


    private final PacienteRepository repository;


    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }



    public List<Paciente> listar(){

        return repository.findAll();

    }



    public Optional<Paciente> buscarPorId(Long id){

        return repository.findById(id);

    }



    public Paciente guardar(Paciente paciente){

        return repository.save(paciente);

    }



    public void eliminar(Long id){

        repository.deleteById(id);

    }

}