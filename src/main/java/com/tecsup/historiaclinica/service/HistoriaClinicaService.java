package com.tecsup.historiaclinica.service;

import com.tecsup.historiaclinica.model.Atencion;
import com.tecsup.historiaclinica.model.HistoriaClinica;
import com.tecsup.historiaclinica.model.Paciente;
import com.tecsup.historiaclinica.repository.AtencionRepository;
import com.tecsup.historiaclinica.repository.HistoriaClinicaRepository;
import com.tecsup.historiaclinica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@org.springframework.transaction.annotation.Transactional
public class HistoriaClinicaService {

    @Autowired
    private HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AtencionRepository atencionRepository;


    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=HistoriaClinica.class, operacion=com.tecsup.historiaclinica.auditoria.Operacion.REGISTRO)
    public HistoriaClinica crearHistoriaClinica(Long pacienteId) {
        if (historiaClinicaRepository.findByPacienteId(pacienteId).isPresent()) {
            throw new IllegalStateException("Este paciente ya tiene una historia clínica registrada.");
        }

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + pacienteId));

        String numeroHistoria = "HC-" + String.format("%06d", paciente.getId());

        HistoriaClinica historia = new HistoriaClinica();
        historia.setNumeroHistoria(numeroHistoria);
        historia.setFechaApertura(LocalDate.now());
        historia.setEstado("ACTIVA");
        historia.setPaciente(paciente);

        return historiaClinicaRepository.save(historia);
    }


    public Optional<HistoriaClinica> consultarPorPaciente(Long pacienteId) {
        return historiaClinicaRepository.findByPacienteId(pacienteId);
    }


    public List<HistoriaClinica> listarTodas() {
        return historiaClinicaRepository.findAll();
    }


    public Optional<HistoriaClinica> buscarPorId(Long id) {
        return historiaClinicaRepository.findById(id);
    }


    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=Atencion.class, operacion=com.tecsup.historiaclinica.auditoria.Operacion.REGISTRO)
    public Atencion registrarAtencion(Long historiaClinicaId, String motivo, String observaciones) {
        validarMotivo(motivo);
        HistoriaClinica historia = historiaClinicaRepository.findById(historiaClinicaId)
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada con id: " + historiaClinicaId));

        Atencion atencion = new Atencion();
        atencion.setFecha(LocalDate.now());
        atencion.setMotivo(motivo);
        atencion.setObservaciones(observaciones);
        atencion.setHistoriaClinica(historia);

        return atencionRepository.save(atencion);
    }


    public List<Atencion> listarAtencionesPorHistoria(Long historiaClinicaId) {
        return atencionRepository.findByHistoriaClinicaIdOrderByFechaDesc(historiaClinicaId);
    }

    private void validarMotivo(String motivo) {
        if(motivo==null || motivo.isBlank() || motivo.length()>255)
            throw new IllegalArgumentException("Motivo requerido (maximo 255 caracteres)");
    }
    private Atencion atencion(Long historiaId,Long id) {
        Atencion a=atencionRepository.findById(id).orElseThrow(()->new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));
        if(!a.getHistoriaClinica().getId().equals(historiaId))
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        return a;
    }
    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=Atencion.class, operacion=com.tecsup.historiaclinica.auditoria.Operacion.MODIFICACION)
    public Atencion editarAtencion(Long historiaId,Long id,String motivo,String observaciones) {
        validarMotivo(motivo);Atencion a=atencion(historiaId,id);a.setMotivo(motivo);a.setObservaciones(observaciones);
        return atencionRepository.save(a);
    }
    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=Atencion.class, operacion=com.tecsup.historiaclinica.auditoria.Operacion.ELIMINACION,idArgumento=1)
    public void eliminarAtencion(Long historiaId,Long id) {
        Atencion a=atencion(historiaId,id);
        a.getHistoriaClinica().getAtenciones().remove(a);
        atencionRepository.delete(a);
    }
}
