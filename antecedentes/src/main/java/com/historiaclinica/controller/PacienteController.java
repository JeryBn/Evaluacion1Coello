package com.historiaclinica.controller;


import com.historiaclinica.model.Paciente;
import com.historiaclinica.service.PacienteService;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {


    private final PacienteService service;


    public PacienteController(PacienteService service){
        this.service = service;
    }



    @GetMapping
    public List<Paciente> listar(){

        return service.listar();

    }



    @GetMapping("/{id}")
    public Paciente buscar(@PathVariable Long id){

        return service.buscarPorId(id)
                .orElse(null);

    }



    @PostMapping
    public Paciente guardar(@RequestBody Paciente paciente){

        return service.guardar(paciente);

    }



    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){

        service.eliminar(id);

    }

}