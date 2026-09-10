package com.historiaclinica.controller;


import com.historiaclinica.model.Antecedente;
import com.historiaclinica.model.HistoriaClinica;
import com.historiaclinica.repository.HistoriaClinicaRepository;
import com.historiaclinica.service.AntecedenteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
public class WebAntecedenteController {


    private final AntecedenteService antecedenteService;

    private final HistoriaClinicaRepository historiaClinicaRepository;


    public WebAntecedenteController(
            AntecedenteService antecedenteService,
            HistoriaClinicaRepository historiaClinicaRepository
    ){

        this.antecedenteService = antecedenteService;
        this.historiaClinicaRepository = historiaClinicaRepository;

    }



    // Ruta normal para abrir formulario personal
    @GetMapping("/antecedentes/personales")
    public String personales(Model model){

        model.addAttribute("historiaClinicaId", 1L);

        return "antecedentes/personales";

    }



    // Ruta normal para abrir formulario familiar
    @GetMapping("/antecedentes/familiares")
    public String familiares(Model model){

        model.addAttribute("historiaClinicaId", 1L);

        return "antecedentes/familiares";

    }



    // Ruta con ID para cuando luego lo conectemos con pacientes
    @GetMapping("/antecedentes/personales/{id}")
    public String personalesId(
            @PathVariable Long id,
            Model model
    ){

        model.addAttribute(
                "historiaClinicaId",
                id
        );

        return "antecedentes/personales";

    }




    @GetMapping("/antecedentes/familiares/{id}")
    public String familiaresId(
            @PathVariable Long id,
            Model model
    ){

        model.addAttribute(
                "historiaClinicaId",
                id
        );

        return "antecedentes/familiares";

    }



    @PostMapping("/antecedentes/guardar")
    public String guardarAntecedente(

            @RequestParam String tipo,

            @RequestParam String descripcion,

            @RequestParam String fechaRegistro,

            @RequestParam Long historiaClinicaId

    ){


        HistoriaClinica historiaClinica =
                historiaClinicaRepository
                        .findById(historiaClinicaId)
                        .orElse(null);



        Antecedente antecedente = new Antecedente();


        antecedente.setTipo(tipo);

        antecedente.setDescripcion(descripcion);

        antecedente.setFechaRegistro(
                LocalDate.parse(fechaRegistro)
        );


        antecedente.setHistoriaClinica(historiaClinica);



        antecedenteService.guardar(antecedente);


        return "redirect:/";

    }


}