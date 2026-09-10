package com.coello.historiaclinica.atencionmedica.controller;

import com.coello.historiaclinica.atencionmedica.dto.ConsultaMedicaRequest;
import com.coello.historiaclinica.atencionmedica.dto.DiagnosticoRequest;
import com.coello.historiaclinica.atencionmedica.dto.EvolucionMedicaRequest;
import com.coello.historiaclinica.atencionmedica.dto.HistoriaClinicaRequest;
import com.coello.historiaclinica.atencionmedica.dto.SignosVitalesRequest;
import com.coello.historiaclinica.atencionmedica.dto.TratamientoRequest;
import com.coello.historiaclinica.atencionmedica.entity.ConsultaMedica;
import com.coello.historiaclinica.atencionmedica.entity.Diagnostico;
import com.coello.historiaclinica.atencionmedica.entity.EvolucionMedica;
import com.coello.historiaclinica.atencionmedica.entity.HistoriaClinica;
import com.coello.historiaclinica.atencionmedica.entity.SignosVitales;
import com.coello.historiaclinica.atencionmedica.entity.Tratamiento;
import com.coello.historiaclinica.atencionmedica.service.AtencionMedicaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/atencion-medica")
public class AtencionMedicaController {

    private final AtencionMedicaService atencionMedicaService;

    public AtencionMedicaController(AtencionMedicaService atencionMedicaService) {
        this.atencionMedicaService = atencionMedicaService;
    }

    @PostMapping("/historias-clinicas")
    @ResponseStatus(HttpStatus.CREATED)
    public HistoriaClinica crearHistoriaClinicaBase(@Valid @RequestBody HistoriaClinicaRequest request) {
        return atencionMedicaService.crearHistoriaClinicaBase(request);
    }

    @PostMapping("/consultas")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaMedica registrarConsulta(@Valid @RequestBody ConsultaMedicaRequest request) {
        return atencionMedicaService.registrarConsulta(request);
    }

    @GetMapping("/historias-clinicas/{historiaClinicaId}/consultas")
    public List<ConsultaMedica> listarConsultas(@PathVariable Long historiaClinicaId) {
        return atencionMedicaService.listarConsultasPorHistoria(historiaClinicaId);
    }

    @GetMapping("/consultas/{consultaId}/signos-vitales")
    public SignosVitales consultarSignosVitales(@PathVariable Long consultaId) {
        return atencionMedicaService.consultarSignosVitales(consultaId);
    }

    @GetMapping("/consultas/{consultaId}/diagnosticos")
    public List<Diagnostico> listarDiagnosticos(@PathVariable Long consultaId) {
        return atencionMedicaService.listarDiagnosticos(consultaId);
    }

    @GetMapping("/consultas/{consultaId}/tratamientos")
    public List<Tratamiento> listarTratamientos(@PathVariable Long consultaId) {
        return atencionMedicaService.listarTratamientos(consultaId);
    }

    @GetMapping("/consultas/{consultaId}/evoluciones")
    public List<EvolucionMedica> listarEvoluciones(@PathVariable Long consultaId) {
        return atencionMedicaService.listarEvoluciones(consultaId);
    }

    @PostMapping("/consultas/{consultaId}/signos-vitales")
    @ResponseStatus(HttpStatus.CREATED)
    public SignosVitales registrarSignosVitales(
            @PathVariable Long consultaId,
            @Valid @RequestBody SignosVitalesRequest request
    ) {
        return atencionMedicaService.registrarSignosVitales(consultaId, request);
    }

    @PostMapping("/consultas/{consultaId}/diagnosticos")
    @ResponseStatus(HttpStatus.CREATED)
    public Diagnostico registrarDiagnostico(
            @PathVariable Long consultaId,
            @Valid @RequestBody DiagnosticoRequest request
    ) {
        return atencionMedicaService.registrarDiagnostico(consultaId, request);
    }

    @PostMapping("/consultas/{consultaId}/tratamientos")
    @ResponseStatus(HttpStatus.CREATED)
    public Tratamiento registrarTratamiento(
            @PathVariable Long consultaId,
            @Valid @RequestBody TratamientoRequest request
    ) {
        return atencionMedicaService.registrarTratamiento(consultaId, request);
    }

    @PostMapping("/consultas/{consultaId}/evoluciones")
    @ResponseStatus(HttpStatus.CREATED)
    public EvolucionMedica registrarEvolucion(
            @PathVariable Long consultaId,
            @Valid @RequestBody EvolucionMedicaRequest request
    ) {
        return atencionMedicaService.registrarEvolucion(consultaId, request);
    }

    @PatchMapping("/consultas/{consultaId}/cerrar")
    public ConsultaMedica cerrarConsulta(@PathVariable Long consultaId) {
        return atencionMedicaService.cerrarConsulta(consultaId);
    }

    @PutMapping("/consultas/{consultaId}")
    public ConsultaMedica actualizarConsulta(
            @PathVariable Long consultaId,
            @Valid @RequestBody ConsultaMedicaRequest request
    ) {
        return atencionMedicaService.actualizarConsulta(consultaId, request);
    }

    @PutMapping("/diagnosticos/{diagnosticoId}")
    public Diagnostico actualizarDiagnostico(
            @PathVariable Long diagnosticoId,
            @Valid @RequestBody DiagnosticoRequest request
    ) {
        return atencionMedicaService.actualizarDiagnostico(diagnosticoId, request);
    }

    @PutMapping("/signos-vitales/{signosVitalesId}")
    public SignosVitales actualizarSignosVitales(
            @PathVariable Long signosVitalesId,
            @Valid @RequestBody SignosVitalesRequest request
    ) {
        return atencionMedicaService.actualizarSignosVitales(signosVitalesId, request);
    }

    @PutMapping("/tratamientos/{tratamientoId}")
    public Tratamiento actualizarTratamiento(
            @PathVariable Long tratamientoId,
            @Valid @RequestBody TratamientoRequest request
    ) {
        return atencionMedicaService.actualizarTratamiento(tratamientoId, request);
    }

    @PutMapping("/evoluciones/{evolucionId}")
    public EvolucionMedica actualizarEvolucion(
            @PathVariable Long evolucionId,
            @Valid @RequestBody EvolucionMedicaRequest request
    ) {
        return atencionMedicaService.actualizarEvolucion(evolucionId, request);
    }

    @DeleteMapping("/consultas/{consultaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarConsulta(@PathVariable Long consultaId) {
        atencionMedicaService.eliminarConsulta(consultaId);
    }

    @DeleteMapping("/diagnosticos/{diagnosticoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarDiagnostico(@PathVariable Long diagnosticoId) {
        atencionMedicaService.eliminarDiagnostico(diagnosticoId);
    }

    @DeleteMapping("/signos-vitales/{signosVitalesId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarSignosVitales(@PathVariable Long signosVitalesId) {
        atencionMedicaService.eliminarSignosVitales(signosVitalesId);
    }

    @DeleteMapping("/tratamientos/{tratamientoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarTratamiento(@PathVariable Long tratamientoId) {
        atencionMedicaService.eliminarTratamiento(tratamientoId);
    }

    @DeleteMapping("/evoluciones/{evolucionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEvolucion(@PathVariable Long evolucionId) {
        atencionMedicaService.eliminarEvolucion(evolucionId);
    }
}
