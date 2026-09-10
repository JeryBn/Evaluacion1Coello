package com.historiaclinica.controller;


import com.historiaclinica.model.Antecedente;
import com.historiaclinica.service.AntecedenteService;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/antecedentes")
public class AntecedenteController {


    private final AntecedenteService service;


    public AntecedenteController(AntecedenteService service){

        this.service = service;

    }



    @GetMapping
    public List<Antecedente> listar(){

        return service.listar();

    }



    @GetMapping("/{id}")
    public Antecedente buscar(@PathVariable Long id){

        return service.buscarPorId(id)
                .orElse(null);

    }



    @PostMapping
    public Antecedente guardar(
            @RequestBody Antecedente antecedente){

        return service.guardar(antecedente);

    }



    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){

        service.eliminar(id);

    }

}