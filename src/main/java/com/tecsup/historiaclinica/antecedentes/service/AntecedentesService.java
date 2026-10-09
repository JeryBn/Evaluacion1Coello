package com.tecsup.historiaclinica.antecedentes.service;

import com.tecsup.historiaclinica.antecedentes.entity.Alergia;
import com.tecsup.historiaclinica.antecedentes.entity.Antecedente;
import com.tecsup.historiaclinica.antecedentes.repository.AlergiaRepository;
import com.tecsup.historiaclinica.antecedentes.repository.AntecedenteRepository;
import com.tecsup.historiaclinica.model.HistoriaClinica;
import com.tecsup.historiaclinica.repository.HistoriaClinicaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@org.springframework.transaction.annotation.Transactional
public class AntecedentesService {
    private final AntecedenteRepository antecedenteRepository;
    private final AlergiaRepository alergiaRepository;
    private final HistoriaClinicaRepository historiaClinicaRepository;

    public AntecedentesService(AntecedenteRepository antecedenteRepository, AlergiaRepository alergiaRepository,
                               HistoriaClinicaRepository historiaClinicaRepository) {
        this.antecedenteRepository = antecedenteRepository;
        this.alergiaRepository = alergiaRepository;
        this.historiaClinicaRepository = historiaClinicaRepository;
    }

    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=Antecedente.class,operacion=com.tecsup.historiaclinica.auditoria.Operacion.REGISTRO)
    public Antecedente registrarAntecedente(Long historiaId, Antecedente antecedente) {
        antecedente.setId(null);
        antecedente.setHistoriaClinica(historia(historiaId));
        return antecedenteRepository.save(antecedente);
    }
    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=Alergia.class,operacion=com.tecsup.historiaclinica.auditoria.Operacion.REGISTRO)
    public Alergia registrarAlergia(Long historiaId, Alergia alergia) {
        alergia.setId(null);
        alergia.setHistoriaClinica(historia(historiaId));
        return alergiaRepository.save(alergia);
    }
    public List<Antecedente> listarAntecedentes(Long historiaId) { return antecedenteRepository.findByHistoriaClinicaId(historiaId); }
    public List<Alergia> listarAlergias(Long historiaId) { return alergiaRepository.findByHistoriaClinicaId(historiaId); }
    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=Antecedente.class,operacion=com.tecsup.historiaclinica.auditoria.Operacion.ELIMINACION,idArgumento=1)
    public void eliminarAntecedente(Long historiaId,Long id) { antecedenteRepository.delete(antecedente(historiaId,id)); }
    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=Alergia.class,operacion=com.tecsup.historiaclinica.auditoria.Operacion.ELIMINACION,idArgumento=1)
    public void eliminarAlergia(Long historiaId,Long id) { alergiaRepository.delete(alergia(historiaId,id)); }
    private Antecedente antecedente(Long historiaId,Long id) {
        var a=antecedenteRepository.findById(id).orElseThrow(()->new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));
        comprobarHistoria(historiaId,a.getHistoriaClinica().getId());return a;
    }
    private Alergia alergia(Long historiaId,Long id) {
        var a=alergiaRepository.findById(id).orElseThrow(()->new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));
        comprobarHistoria(historiaId,a.getHistoriaClinica().getId());return a;
    }
    private void comprobarHistoria(Long esperado,Long actual) {
        if(!esperado.equals(actual))throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
    }
    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=Antecedente.class,operacion=com.tecsup.historiaclinica.auditoria.Operacion.MODIFICACION)
    public Antecedente editarAntecedente(Long historiaId,Long id,Antecedente datos) {
        var a=antecedente(historiaId,id);a.setTipo(datos.getTipo());a.setDescripcion(datos.getDescripcion());a.setFechaRegistro(datos.getFechaRegistro());return antecedenteRepository.save(a);
    }
    @com.tecsup.historiaclinica.auditoria.Auditar(entidad=Alergia.class,operacion=com.tecsup.historiaclinica.auditoria.Operacion.MODIFICACION)
    public Alergia editarAlergia(Long historiaId,Long id,Alergia datos) {
        var a=alergia(historiaId,id);a.setAlergia(datos.getAlergia());a.setTipo(datos.getTipo());a.setReaccion(datos.getReaccion());a.setObservacion(datos.getObservacion());a.setFechaRegistro(datos.getFechaRegistro());return alergiaRepository.save(a);
    }
    private HistoriaClinica historia(Long id) {
        return historiaClinicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la historia clinica " + id));
    }
}
