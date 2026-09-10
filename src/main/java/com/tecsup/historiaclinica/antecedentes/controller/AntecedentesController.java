package com.tecsup.historiaclinica.antecedentes.controller;

import com.tecsup.historiaclinica.antecedentes.entity.Alergia;
import com.tecsup.historiaclinica.antecedentes.entity.Antecedente;
import com.tecsup.historiaclinica.antecedentes.service.AntecedentesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/historias-clinicas/{historiaId}")
public class AntecedentesController {
    private final AntecedentesService service;
    public AntecedentesController(AntecedentesService service) { this.service = service; }
    @GetMapping("/antecedentes") public List<Antecedente> listarAntecedentes(@PathVariable Long historiaId) { return service.listarAntecedentes(historiaId); }
    @PostMapping("/antecedentes") @ResponseStatus(HttpStatus.CREATED)
    public Antecedente crearAntecedente(@PathVariable Long historiaId, @RequestBody Antecedente antecedente) { return service.registrarAntecedente(historiaId, antecedente); }
    @DeleteMapping("/antecedentes/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarAntecedente(@PathVariable Long id) { service.eliminarAntecedente(id); }
    @GetMapping("/alergias") public List<Alergia> listarAlergias(@PathVariable Long historiaId) { return service.listarAlergias(historiaId); }
    @PostMapping("/alergias") @ResponseStatus(HttpStatus.CREATED)
    public Alergia crearAlergia(@PathVariable Long historiaId, @RequestBody Alergia alergia) { return service.registrarAlergia(historiaId, alergia); }
    @DeleteMapping("/alergias/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarAlergia(@PathVariable Long id) { service.eliminarAlergia(id); }
}
