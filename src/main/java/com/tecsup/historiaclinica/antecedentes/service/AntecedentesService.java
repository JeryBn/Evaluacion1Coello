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

    public Antecedente registrarAntecedente(Long historiaId, Antecedente antecedente) {
        antecedente.setHistoriaClinica(historia(historiaId));
        return antecedenteRepository.save(antecedente);
    }
    public Alergia registrarAlergia(Long historiaId, Alergia alergia) {
        alergia.setHistoriaClinica(historia(historiaId));
        return alergiaRepository.save(alergia);
    }
    public List<Antecedente> listarAntecedentes(Long historiaId) { return antecedenteRepository.findByHistoriaClinicaId(historiaId); }
    public List<Alergia> listarAlergias(Long historiaId) { return alergiaRepository.findByHistoriaClinicaId(historiaId); }
    public void eliminarAntecedente(Long id) { antecedenteRepository.deleteById(id); }
    public void eliminarAlergia(Long id) { alergiaRepository.deleteById(id); }
    private HistoriaClinica historia(Long id) {
        return historiaClinicaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la historia clinica " + id));
    }
}
