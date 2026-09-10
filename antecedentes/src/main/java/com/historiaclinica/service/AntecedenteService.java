package com.historiaclinica.service;

import com.historiaclinica.model.Antecedente;
import com.historiaclinica.repository.AntecedenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AntecedenteService {


    private final AntecedenteRepository repository;


    public AntecedenteService(AntecedenteRepository repository){

        this.repository = repository;

    }



    public List<Antecedente> listar(){

        return repository.findAll();

    }



    public Optional<Antecedente> buscarPorId(Long id){

        return repository.findById(id);

    }



    public Antecedente guardar(Antecedente antecedente){

        return repository.save(antecedente);

    }



    public void eliminar(Long id){

        repository.deleteById(id);

    }

}