package com.coello.historiaclinica.atencionmedica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "diagnosticos")
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_medica_id", nullable = false)
    private ConsultaMedica consultaMedica;

    @NotBlank
    @Column(nullable = false, length = 15)
    private String codigoCie10;

    @NotBlank
    @Column(nullable = false, length = 180)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoDiagnostico tipoDiagnostico;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ConsultaMedica getConsultaMedica() { return consultaMedica; }
    public void setConsultaMedica(ConsultaMedica consultaMedica) { this.consultaMedica = consultaMedica; }
    public String getCodigoCie10() { return codigoCie10; }
    public void setCodigoCie10(String codigoCie10) { this.codigoCie10 = codigoCie10; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public TipoDiagnostico getTipoDiagnostico() { return tipoDiagnostico; }
    public void setTipoDiagnostico(TipoDiagnostico tipoDiagnostico) { this.tipoDiagnostico = tipoDiagnostico; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
