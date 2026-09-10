package com.tecsup.historiaclinica.controller;

import com.tecsup.historiaclinica.model.Atencion;
import com.tecsup.historiaclinica.model.HistoriaClinica;
import com.tecsup.historiaclinica.service.HistoriaClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historias-clinicas")
public class HistoriaClinicaController {

    @Autowired
    private HistoriaClinicaService historiaClinicaService;


    @PostMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinica> crear(@PathVariable Long pacienteId) {
        HistoriaClinica creada = historiaClinicaService.crearHistoriaClinica(pacienteId);
        return ResponseEntity.ok(creada);
    }


    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinica> consultarPorPaciente(@PathVariable Long pacienteId) {
        return historiaClinicaService.consultarPorPaciente(pacienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<HistoriaClinica>> listarTodas() {
        return ResponseEntity.ok(historiaClinicaService.listarTodas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinica> buscarPorId(@PathVariable Long id) {
        return historiaClinicaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{historiaClinicaId}/atenciones")
    public ResponseEntity<Atencion> registrarAtencion(
            @PathVariable Long historiaClinicaId,
            @RequestParam String motivo,
            @RequestParam(required = false) String observaciones) {
        Atencion atencion = historiaClinicaService.registrarAtencion(historiaClinicaId, motivo, observaciones);
        return ResponseEntity.ok(atencion);
    }


    @GetMapping("/{historiaClinicaId}/atenciones")
    public ResponseEntity<List<Atencion>> listarAtenciones(@PathVariable Long historiaClinicaId) {
        return ResponseEntity.ok(historiaClinicaService.listarAtencionesPorHistoria(historiaClinicaId));
    }
}