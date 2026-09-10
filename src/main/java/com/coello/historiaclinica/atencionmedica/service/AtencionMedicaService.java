package com.coello.historiaclinica.atencionmedica.service;

import com.coello.historiaclinica.atencionmedica.dto.ConsultaMedicaRequest;
import com.coello.historiaclinica.atencionmedica.dto.DiagnosticoRequest;
import com.coello.historiaclinica.atencionmedica.dto.EvolucionMedicaRequest;
import com.coello.historiaclinica.atencionmedica.dto.HistoriaClinicaRequest;
import com.coello.historiaclinica.atencionmedica.dto.SignosVitalesRequest;
import com.coello.historiaclinica.atencionmedica.dto.TratamientoRequest;
import com.coello.historiaclinica.atencionmedica.entity.ConsultaMedica;
import com.coello.historiaclinica.atencionmedica.entity.Diagnostico;
import com.coello.historiaclinica.atencionmedica.entity.EstadoConsulta;
import com.coello.historiaclinica.atencionmedica.entity.EvolucionMedica;
import com.coello.historiaclinica.atencionmedica.entity.HistoriaClinica;
import com.coello.historiaclinica.atencionmedica.entity.SignosVitales;
import com.coello.historiaclinica.atencionmedica.entity.Tratamiento;
import com.coello.historiaclinica.atencionmedica.repository.ConsultaMedicaRepository;
import com.coello.historiaclinica.atencionmedica.repository.DiagnosticoRepository;
import com.coello.historiaclinica.atencionmedica.repository.EvolucionMedicaRepository;
import com.coello.historiaclinica.atencionmedica.repository.HistoriaClinicaRepository;
import com.coello.historiaclinica.atencionmedica.repository.SignosVitalesRepository;
import com.coello.historiaclinica.atencionmedica.repository.TratamientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AtencionMedicaService {

    private final HistoriaClinicaRepository historiaClinicaRepository;
    private final ConsultaMedicaRepository consultaMedicaRepository;
    private final SignosVitalesRepository signosVitalesRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final TratamientoRepository tratamientoRepository;
    private final EvolucionMedicaRepository evolucionMedicaRepository;

    public AtencionMedicaService(
            HistoriaClinicaRepository historiaClinicaRepository,
            ConsultaMedicaRepository consultaMedicaRepository,
            SignosVitalesRepository signosVitalesRepository,
            DiagnosticoRepository diagnosticoRepository,
            TratamientoRepository tratamientoRepository,
            EvolucionMedicaRepository evolucionMedicaRepository
    ) {
        this.historiaClinicaRepository = historiaClinicaRepository;
        this.consultaMedicaRepository = consultaMedicaRepository;
        this.signosVitalesRepository = signosVitalesRepository;
        this.diagnosticoRepository = diagnosticoRepository;
        this.tratamientoRepository = tratamientoRepository;
        this.evolucionMedicaRepository = evolucionMedicaRepository;
    }

    public HistoriaClinica crearHistoriaClinicaBase(HistoriaClinicaRequest request) {
        HistoriaClinica historia = new HistoriaClinica();
        historia.setNumeroHistoria(request.numeroHistoria());
        historia.setPacienteId(request.pacienteId());
        historia.setFechaApertura(LocalDate.now());
        historia.setEstado("ACTIVA");
        return historiaClinicaRepository.save(historia);
    }

    public ConsultaMedica registrarConsulta(ConsultaMedicaRequest request) {
        HistoriaClinica historia = obtenerHistoriaClinica(request.historiaClinicaId());
        ConsultaMedica consulta = new ConsultaMedica();
        consulta.setHistoriaClinica(historia);
        consulta.setCodigoCita(request.codigoCita());
        consulta.setMedicoId(request.medicoId());
        consulta.setNombreMedico(request.nombreMedico());
        consulta.setEspecialidad(request.especialidad());
        consulta.setFechaAtencion(request.fechaAtencion() == null ? LocalDateTime.now() : request.fechaAtencion());
        consulta.setMotivoConsulta(request.motivoConsulta());
        consulta.setAnamnesis(request.anamnesis());
        consulta.setExamenFisico(request.examenFisico());
        consulta.setEvaluacionClinica(request.evaluacionClinica());
        consulta.setObservaciones(request.observaciones());
        consulta.setEstado(EstadoConsulta.ABIERTA);
        return consultaMedicaRepository.save(consulta);
    }

    @Transactional(readOnly = true)
    public List<ConsultaMedica> listarConsultasPorHistoria(Long historiaClinicaId) {
        return consultaMedicaRepository.findByHistoriaClinicaIdOrderByFechaAtencionDesc(historiaClinicaId);
    }

    @Transactional(readOnly = true)
    public SignosVitales consultarSignosVitales(Long consultaId) {
        return signosVitalesRepository.findByConsultaMedicaId(consultaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("La consulta no tiene signos vitales registrados"));
    }

    @Transactional(readOnly = true)
    public List<Diagnostico> listarDiagnosticos(Long consultaId) {
        return diagnosticoRepository.findByConsultaMedicaId(consultaId);
    }

    @Transactional(readOnly = true)
    public List<Tratamiento> listarTratamientos(Long consultaId) {
        return tratamientoRepository.findByConsultaMedicaId(consultaId);
    }

    @Transactional(readOnly = true)
    public List<EvolucionMedica> listarEvoluciones(Long consultaId) {
        return evolucionMedicaRepository.findByConsultaMedicaIdOrderByFechaEvolucionDesc(consultaId);
    }

    public SignosVitales registrarSignosVitales(Long consultaId, SignosVitalesRequest request) {
        ConsultaMedica consulta = obtenerConsulta(consultaId);
        SignosVitales signos = signosVitalesRepository.findByConsultaMedicaId(consultaId).orElse(new SignosVitales());
        signos.setConsultaMedica(consulta);
        signos.setPesoKg(request.pesoKg());
        signos.setTallaMetros(request.tallaMetros());
        signos.setPresionArterial(request.presionArterial());
        signos.setFrecuenciaCardiaca(request.frecuenciaCardiaca());
        signos.setFrecuenciaRespiratoria(request.frecuenciaRespiratoria());
        signos.setTemperatura(request.temperatura());
        signos.setSaturacionOxigeno(request.saturacionOxigeno());
        signos.setFechaRegistro(LocalDateTime.now());
        return signosVitalesRepository.save(signos);
    }

    public SignosVitales actualizarSignosVitales(Long signosVitalesId, SignosVitalesRequest request) {
        SignosVitales signos = signosVitalesRepository.findById(signosVitalesId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existen signos vitales con id " + signosVitalesId));
        signos.setPesoKg(request.pesoKg());
        signos.setTallaMetros(request.tallaMetros());
        signos.setPresionArterial(request.presionArterial());
        signos.setFrecuenciaCardiaca(request.frecuenciaCardiaca());
        signos.setFrecuenciaRespiratoria(request.frecuenciaRespiratoria());
        signos.setTemperatura(request.temperatura());
        signos.setSaturacionOxigeno(request.saturacionOxigeno());
        signos.setFechaRegistro(LocalDateTime.now());
        return signosVitalesRepository.save(signos);
    }

    public Diagnostico registrarDiagnostico(Long consultaId, DiagnosticoRequest request) {
        ConsultaMedica consulta = obtenerConsulta(consultaId);
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setConsultaMedica(consulta);
        diagnostico.setCodigoCie10(request.codigoCie10());
        diagnostico.setDescripcion(request.descripcion());
        diagnostico.setTipoDiagnostico(request.tipoDiagnostico());
        diagnostico.setObservaciones(request.observaciones());
        return diagnosticoRepository.save(diagnostico);
    }

    public Tratamiento registrarTratamiento(Long consultaId, TratamientoRequest request) {
        ConsultaMedica consulta = obtenerConsulta(consultaId);
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setConsultaMedica(consulta);
        tratamiento.setTratamientoIndicado(request.tratamientoIndicado());
        tratamiento.setIndicaciones(request.indicaciones());
        tratamiento.setDuracion(request.duracion());
        tratamiento.setRecomendaciones(request.recomendaciones());
        tratamiento.setFechaInicio(request.fechaInicio());
        tratamiento.setFechaFinalizacion(request.fechaFinalizacion());
        tratamiento.setObservaciones(request.observaciones());
        return tratamientoRepository.save(tratamiento);
    }

    public EvolucionMedica registrarEvolucion(Long consultaId, EvolucionMedicaRequest request) {
        ConsultaMedica consulta = obtenerConsulta(consultaId);
        EvolucionMedica evolucion = new EvolucionMedica();
        evolucion.setConsultaMedica(consulta);
        evolucion.setFechaEvolucion(LocalDateTime.now());
        evolucion.setDescripcion(request.descripcion());
        evolucion.setPlan(request.plan());
        evolucion.setMedicoResponsable(request.medicoResponsable());
        return evolucionMedicaRepository.save(evolucion);
    }

    public ConsultaMedica cerrarConsulta(Long consultaId) {
        ConsultaMedica consulta = obtenerConsulta(consultaId);
        consulta.setEstado(EstadoConsulta.CERRADA);
        return consultaMedicaRepository.save(consulta);
    }

    public ConsultaMedica actualizarConsulta(Long consultaId, ConsultaMedicaRequest request) {
        ConsultaMedica consulta = obtenerConsulta(consultaId);
        consulta.setCodigoCita(request.codigoCita());
        consulta.setMedicoId(request.medicoId());
        consulta.setNombreMedico(request.nombreMedico());
        consulta.setEspecialidad(request.especialidad());
        consulta.setFechaAtencion(request.fechaAtencion() == null ? consulta.getFechaAtencion() : request.fechaAtencion());
        consulta.setMotivoConsulta(request.motivoConsulta());
        consulta.setAnamnesis(request.anamnesis());
        consulta.setExamenFisico(request.examenFisico());
        consulta.setEvaluacionClinica(request.evaluacionClinica());
        consulta.setObservaciones(request.observaciones());
        return consultaMedicaRepository.save(consulta);
    }

    public Diagnostico actualizarDiagnostico(Long diagnosticoId, DiagnosticoRequest request) {
        Diagnostico diagnostico = diagnosticoRepository.findById(diagnosticoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el diagnostico con id " + diagnosticoId));
        diagnostico.setCodigoCie10(request.codigoCie10());
        diagnostico.setDescripcion(request.descripcion());
        diagnostico.setTipoDiagnostico(request.tipoDiagnostico());
        diagnostico.setObservaciones(request.observaciones());
        return diagnosticoRepository.save(diagnostico);
    }

    public Tratamiento actualizarTratamiento(Long tratamientoId, TratamientoRequest request) {
        Tratamiento tratamiento = tratamientoRepository.findById(tratamientoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el tratamiento con id " + tratamientoId));
        tratamiento.setTratamientoIndicado(request.tratamientoIndicado());
        tratamiento.setIndicaciones(request.indicaciones());
        tratamiento.setDuracion(request.duracion());
        tratamiento.setRecomendaciones(request.recomendaciones());
        tratamiento.setFechaInicio(request.fechaInicio());
        tratamiento.setFechaFinalizacion(request.fechaFinalizacion());
        tratamiento.setObservaciones(request.observaciones());
        return tratamientoRepository.save(tratamiento);
    }

    public EvolucionMedica actualizarEvolucion(Long evolucionId, EvolucionMedicaRequest request) {
        EvolucionMedica evolucion = evolucionMedicaRepository.findById(evolucionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la evolucion medica con id " + evolucionId));
        evolucion.setDescripcion(request.descripcion());
        evolucion.setPlan(request.plan());
        evolucion.setMedicoResponsable(request.medicoResponsable());
        return evolucionMedicaRepository.save(evolucion);
    }

    public void eliminarConsulta(Long consultaId) {
        if (!consultaMedicaRepository.existsById(consultaId)) {
            throw new RecursoNoEncontradoException("No existe la consulta medica con id " + consultaId);
        }
        consultaMedicaRepository.deleteById(consultaId);
    }

    public void eliminarSignosVitales(Long signosVitalesId) {
        if (!signosVitalesRepository.existsById(signosVitalesId)) {
            throw new RecursoNoEncontradoException("No existen signos vitales con id " + signosVitalesId);
        }
        signosVitalesRepository.deleteById(signosVitalesId);
    }

    public void eliminarDiagnostico(Long diagnosticoId) {
        if (!diagnosticoRepository.existsById(diagnosticoId)) {
            throw new RecursoNoEncontradoException("No existe el diagnostico con id " + diagnosticoId);
        }
        diagnosticoRepository.deleteById(diagnosticoId);
    }

    public void eliminarTratamiento(Long tratamientoId) {
        if (!tratamientoRepository.existsById(tratamientoId)) {
            throw new RecursoNoEncontradoException("No existe el tratamiento con id " + tratamientoId);
        }
        tratamientoRepository.deleteById(tratamientoId);
    }

    public void eliminarEvolucion(Long evolucionId) {
        if (!evolucionMedicaRepository.existsById(evolucionId)) {
            throw new RecursoNoEncontradoException("No existe la evolucion medica con id " + evolucionId);
        }
        evolucionMedicaRepository.deleteById(evolucionId);
    }

    private HistoriaClinica obtenerHistoriaClinica(Long historiaClinicaId) {
        return historiaClinicaRepository.findById(historiaClinicaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la historia clinica con id " + historiaClinicaId));
    }

    private ConsultaMedica obtenerConsulta(Long consultaId) {
        return consultaMedicaRepository.findById(consultaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe la consulta medica con id " + consultaId));
    }
}
