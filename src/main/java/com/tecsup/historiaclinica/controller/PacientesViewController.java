package com.tecsup.historiaclinica.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.tecsup.historiaclinica.service.PacienteService;
@Controller public class PacientesViewController {
    private final PacienteService service;
    public PacientesViewController(PacienteService service){this.service=service;}
    @GetMapping("/pacientes") public String listar(Model model){model.addAttribute("pacientes",service.listarTodos());return "pacientes";}
}
