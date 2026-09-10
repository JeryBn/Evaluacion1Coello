package com.tecsup.historiaclinica.antecedentes.controller;

import com.tecsup.historiaclinica.antecedentes.entity.Alergia;
import com.tecsup.historiaclinica.antecedentes.entity.Antecedente;
import com.tecsup.historiaclinica.antecedentes.service.AntecedentesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AntecedentesViewController {
    private final AntecedentesService service;
    public AntecedentesViewController(AntecedentesService service) { this.service = service; }

    @GetMapping("/historias-clinicas/{historiaId}/antecedentes")
    public String verModulo(@PathVariable Long historiaId, Model model) {
        model.addAttribute("historiaId", historiaId);
        model.addAttribute("antecedentes", service.listarAntecedentes(historiaId));
        model.addAttribute("alergias", service.listarAlergias(historiaId));
        return "antecedentes";
    }

    @PostMapping("/historias-clinicas/{historiaId}/antecedentes")
    public String guardarAntecedente(@PathVariable Long historiaId, @RequestParam String tipo,
                                     @RequestParam String descripcion, @RequestParam String fechaRegistro) {
        Antecedente antecedente = new Antecedente();
        antecedente.setTipo(tipo);
        antecedente.setDescripcion(descripcion);
        antecedente.setFechaRegistro(java.time.LocalDate.parse(fechaRegistro));
        service.registrarAntecedente(historiaId, antecedente);
        return "redirect:/historias-clinicas/" + historiaId + "/antecedentes";
    }

    @PostMapping("/historias-clinicas/{historiaId}/alergias")
    public String guardarAlergia(@PathVariable Long historiaId, @RequestParam String alergia, @RequestParam String tipo,
                                 @RequestParam String reaccion, @RequestParam(required = false) String observacion,
                                 @RequestParam String fechaRegistro) {
        Alergia nueva = new Alergia();
        nueva.setAlergia(alergia);
        nueva.setTipo(tipo);
        nueva.setReaccion(reaccion);
        nueva.setObservacion(observacion);
        nueva.setFechaRegistro(java.time.LocalDate.parse(fechaRegistro));
        service.registrarAlergia(historiaId, nueva);
        return "redirect:/historias-clinicas/" + historiaId + "/antecedentes";
    }
}
