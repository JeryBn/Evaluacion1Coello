package com.tecsup.historiaclinica.controller;


import com.tecsup.historiaclinica.service.HistoriaClinicaService;
import com.tecsup.historiaclinica.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/historias-clinicas")
public class HistoriaClinicaViewController {

    @Autowired
    private HistoriaClinicaService historiaClinicaService;

    @Autowired
    private PacienteService pacienteService;


    @GetMapping
    public String listar(Model model) {
        model.addAttribute("historias", historiaClinicaService.listarTodas());
        return "historias-lista";
    }


    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("pacientes", pacienteService.listarTodos());
        return "historia-form";
    }


    @PostMapping("/nueva")
    public String crear(@RequestParam Long pacienteId, RedirectAttributes redirectAttributes) {
        try {
            historiaClinicaService.crearHistoriaClinica(pacienteId);
            return "redirect:/historias-clinicas";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/historias-clinicas/nueva";
        }
    }


    @GetMapping("/paciente/{pacienteId}")
    public String consultarPorPaciente(@PathVariable Long pacienteId, Model model) {
        historiaClinicaService.consultarPorPaciente(pacienteId)
                .ifPresent(h -> model.addAttribute("historia", h));
        return "historia-detalle";
    }

    @GetMapping("/{historiaClinicaId}/atenciones")
    public String verAtenciones(@PathVariable Long historiaClinicaId, Model model) {
        model.addAttribute("historiaClinicaId", historiaClinicaId);
        model.addAttribute("atenciones", historiaClinicaService.listarAtencionesPorHistoria(historiaClinicaId));
        return "atenciones-lista";
    }


    @PostMapping("/{historiaClinicaId}/atenciones")
    public String registrarAtencion(@PathVariable Long historiaClinicaId,
                                    @RequestParam String motivo,
                                    @RequestParam(required = false) String observaciones) {
        historiaClinicaService.registrarAtencion(historiaClinicaId, motivo, observaciones);
        return "redirect:/historias-clinicas/" + historiaClinicaId + "/atenciones";
    }
}