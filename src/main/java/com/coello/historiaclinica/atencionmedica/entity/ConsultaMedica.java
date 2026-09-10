package com.coello.historiaclinica.atencionmedica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tecsup.historiaclinica.model.HistoriaClinica;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultas_medicas")
public class ConsultaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_clinica_id", nullable = false)
    @JsonIgnore
    private HistoriaClinica historiaClinica;

    @Column(length = 30)
    private String codigoCita;

    @Column(nullable = false)
    private Long medicoId;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombreMedico;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String especialidad;

    @Column(nullable = false)
    private LocalDateTime fechaAtencion;

    @NotBlank
    @Column(nullable = false, length = 300)
    private String motivoConsulta;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String anamnesis;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String examenFisico;

    @Column(columnDefinition = "TEXT")
    private String evaluacionClinica;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoConsulta estado = EstadoConsulta.ABIERTA;

    @JsonIgnore
    @OneToOne(mappedBy = "consultaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private SignosVitales signosVitales;

    @JsonIgnore
    @OneToMany(mappedBy = "consultaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diagnostico> diagnosticos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "consultaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tratamiento> tratamientos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "consultaMedica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvolucionMedica> evoluciones = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public HistoriaClinica getHistoriaClinica() { return historiaClinica; }
    public void setHistoriaClinica(HistoriaClinica historiaClinica) { this.historiaClinica = historiaClinica; }
    public String getCodigoCita() { return codigoCita; }
    public void setCodigoCita(String codigoCita) { this.codigoCita = codigoCita; }
    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }
    public String getNombreMedico() { return nombreMedico; }
    public void setNombreMedico(String nombreMedico) { this.nombreMedico = nombreMedico; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public LocalDateTime getFechaAtencion() { return fechaAtencion; }
    public void setFechaAtencion(LocalDateTime fechaAtencion) { this.fechaAtencion = fechaAtencion; }
    public String getMotivoConsulta() { return motivoConsulta; }
    public void setMotivoConsulta(String motivoConsulta) { this.motivoConsulta = motivoConsulta; }
    public String getAnamnesis() { return anamnesis; }
    public void setAnamnesis(String anamnesis) { this.anamnesis = anamnesis; }
    public String getExamenFisico() { return examenFisico; }
    public void setExamenFisico(String examenFisico) { this.examenFisico = examenFisico; }
    public String getEvaluacionClinica() { return evaluacionClinica; }
    public void setEvaluacionClinica(String evaluacionClinica) { this.evaluacionClinica = evaluacionClinica; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public EstadoConsulta getEstado() { return estado; }
    public void setEstado(EstadoConsulta estado) { this.estado = estado; }
}
