package com.coello.historiaclinica.atencionmedica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "tratamientos")
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_medica_id", nullable = false)
    private ConsultaMedica consultaMedica;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String tratamientoIndicado;

    @Column(columnDefinition = "TEXT")
    private String indicaciones;

    @Column(length = 80)
    private String duracion;

    @Column(columnDefinition = "TEXT")
    private String recomendaciones;

    private LocalDate fechaInicio;
    private LocalDate fechaFinalizacion;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ConsultaMedica getConsultaMedica() { return consultaMedica; }
    public void setConsultaMedica(ConsultaMedica consultaMedica) { this.consultaMedica = consultaMedica; }
    public String getTratamientoIndicado() { return tratamientoIndicado; }
    public void setTratamientoIndicado(String tratamientoIndicado) { this.tratamientoIndicado = tratamientoIndicado; }
    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String recomendaciones) { this.recomendaciones = recomendaciones; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFinalizacion() { return fechaFinalizacion; }
    public void setFechaFinalizacion(LocalDate fechaFinalizacion) { this.fechaFinalizacion = fechaFinalizacion; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
