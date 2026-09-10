package com.historiaclinica.controller;


import com.historiaclinica.model.Alergia;
import com.historiaclinica.service.AlergiaService;

import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/alergias")
public class AlergiaController {


    private final AlergiaService service;



    public AlergiaController(AlergiaService service){

        this.service = service;

    }



    @GetMapping
    public List<Alergia> listar(){

        return service.listar();

    }



    @GetMapping("/{id}")
    public Alergia buscar(@PathVariable Long id){

        return service.buscarPorId(id)
                .orElse(null);

    }



    @PostMapping
    public Alergia guardar(
            @RequestBody Alergia alergia){

        return service.guardar(alergia);

    }



    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){

        service.eliminar(id);

    }

}