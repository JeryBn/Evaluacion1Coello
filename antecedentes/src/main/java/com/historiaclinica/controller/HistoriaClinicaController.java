package com.historiaclinica.controller;


import com.historiaclinica.model.HistoriaClinica;
import com.historiaclinica.service.HistoriaClinicaService;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/historias")
public class HistoriaClinicaController {


    private final HistoriaClinicaService service;


    public HistoriaClinicaController(HistoriaClinicaService service){

        this.service = service;

    }



    @GetMapping
    public List<HistoriaClinica> listar(){

        return service.listar();

    }



    @GetMapping("/{id}")
    public HistoriaClinica buscar(@PathVariable Long id){

        return service.buscarPorId(id)
                .orElse(null);

    }



    @PostMapping
    public HistoriaClinica guardar(
            @RequestBody HistoriaClinica historiaClinica){

        return service.guardar(historiaClinica);

    }



    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){

        service.eliminar(id);

    }

}