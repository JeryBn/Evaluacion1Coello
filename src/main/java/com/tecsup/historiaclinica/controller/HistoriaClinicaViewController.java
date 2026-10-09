package com.tecsup.historiaclinica.controller;


import com.tecsup.historiaclinica.service.HistoriaClinicaService;
import com.tecsup.historiaclinica.service.PacienteService;
import com.tecsup.historiaclinica.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/historias-clinicas")
public class HistoriaClinicaViewController {
    @PostMapping("/{historiaId}/atenciones/{id}/editar")
    public String editarAtencion(@PathVariable Long historiaId,@PathVariable Long id,@RequestParam String motivo,@RequestParam(required=false) String observaciones) {
        historiaClinicaService.editarAtencion(historiaId,id,motivo,observaciones);
        return "redirect:/historias-clinicas/"+historiaId+"/atenciones";
    }
    @PostMapping("/{historiaId}/atenciones/{id}/eliminar")
    public String eliminarAtencion(@PathVariable Long historiaId,@PathVariable Long id) {
        historiaClinicaService.eliminarAtencion(historiaId,id);
        return "redirect:/historias-clinicas/"+historiaId+"/atenciones";
    }

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

    @GetMapping("/pacientes/nuevo")
    public String mostrarFormularioPaciente(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "paciente-form";
    }

    @PostMapping("/pacientes/nuevo")
    public String crearPaciente(@ModelAttribute Paciente paciente) {
        pacienteService.crear(paciente);
        return "redirect:/pacientes";
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
