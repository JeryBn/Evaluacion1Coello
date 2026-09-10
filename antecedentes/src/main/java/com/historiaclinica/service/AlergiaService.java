package com.historiaclinica.service;

import com.historiaclinica.model.Alergia;
import com.historiaclinica.repository.AlergiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AlergiaService {


    private final AlergiaRepository repository;


    public AlergiaService(AlergiaRepository repository){

        this.repository = repository;

    }



    public List<Alergia> listar(){

        return repository.findAll();

    }



    public Optional<Alergia> buscarPorId(Long id){

        return repository.findById(id);

    }



    public Alergia guardar(Alergia alergia){

        return repository.save(alergia);

    }



    public void eliminar(Long id){

        repository.deleteById(id);

    }

}