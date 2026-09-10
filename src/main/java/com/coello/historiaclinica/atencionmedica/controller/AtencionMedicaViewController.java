package com.coello.historiaclinica.atencionmedica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AtencionMedicaViewController {

    @GetMapping("/atencion-medica")
    public String mostrarAtencionMedica() {
        return "atencion-medica";
    }
}
