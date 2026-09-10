package com.tecsup.historiaclinica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioViewController {

    @GetMapping("/")
    public String mostrarInicio() {
        return "inicio";
    }
}
