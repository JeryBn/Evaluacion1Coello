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

import java.time.LocalDateTime;

@Entity
@Table(name = "evoluciones_medicas")
public class EvolucionMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_medica_id", nullable = false)
    private ConsultaMedica consultaMedica;

    @Column(nullable = false)
    private LocalDateTime fechaEvolucion;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String plan;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String medicoResponsable;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ConsultaMedica getConsultaMedica() { return consultaMedica; }
    public void setConsultaMedica(ConsultaMedica consultaMedica) { this.consultaMedica = consultaMedica; }
    public LocalDateTime getFechaEvolucion() { return fechaEvolucion; }
    public void setFechaEvolucion(LocalDateTime fechaEvolucion) { this.fechaEvolucion = fechaEvolucion; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }
    public String getMedicoResponsable() { return medicoResponsable; }
    public void setMedicoResponsable(String medicoResponsable) { this.medicoResponsable = medicoResponsable; }
}
