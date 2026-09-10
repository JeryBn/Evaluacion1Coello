package com.historiaclinica.controller;


import com.historiaclinica.model.Alergia;
import com.historiaclinica.model.HistoriaClinica;
import com.historiaclinica.repository.HistoriaClinicaRepository;
import com.historiaclinica.service.AlergiaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;



@Controller
public class WebAlergiaController {



    private final AlergiaService alergiaService;

    private final HistoriaClinicaRepository historiaClinicaRepository;



    public WebAlergiaController(
            AlergiaService alergiaService,
            HistoriaClinicaRepository historiaClinicaRepository
    ){

        this.alergiaService = alergiaService;
        this.historiaClinicaRepository = historiaClinicaRepository;

    }





    @GetMapping("/alergias")
    public String mostrarFormulario(Model model){


        model.addAttribute(
                "historiaClinicaId",
                1L
        );


        return "alergias/formulario";

    }




    @PostMapping("/alergias/guardar")
    public String guardarAlergia(

            @RequestParam String alergia,

            @RequestParam String tipo,

            @RequestParam String reaccion,

            @RequestParam String observacion,

            @RequestParam String fechaRegistro,

            @RequestParam Long historiaClinicaId

    ){


        HistoriaClinica historiaClinica =
                historiaClinicaRepository
                        .findById(historiaClinicaId)
                        .orElse(null);



        Alergia nueva = new Alergia();


        nueva.setAlergia(alergia);


        nueva.setTipo(tipo);


        nueva.setReaccion(reaccion);


        nueva.setObservacion(observacion);


        nueva.setFechaRegistro(
                LocalDate.parse(fechaRegistro)
        );


        nueva.setHistoriaClinica(historiaClinica);



        alergiaService.guardar(nueva);



        return "redirect:/";

    }


}