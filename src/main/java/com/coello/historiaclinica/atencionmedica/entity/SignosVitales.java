package com.coello.historiaclinica.atencionmedica.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "signos_vitales")
public class SignosVitales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_medica_id", nullable = false, unique = true)
    private ConsultaMedica consultaMedica;

    @DecimalMin("0.1")
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal pesoKg;

    @DecimalMin("0.1")
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal tallaMetros;

    @Column(nullable = false, length = 20)
    private String presionArterial;

    @Column(nullable = false)
    private Integer frecuenciaCardiaca;

    @Column(nullable = false)
    private Integer frecuenciaRespiratoria;

    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal temperatura;

    @Column(nullable = false)
    private Integer saturacionOxigeno;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public BigDecimal calcularImc() {
        return pesoKg.divide(tallaMetros.multiply(tallaMetros), 2, RoundingMode.HALF_UP);
    }

    public String interpretarImc() {
        BigDecimal imc = calcularImc();
        if (imc.compareTo(new BigDecimal("18.50")) < 0) {
            return "Bajo peso";
        }
        if (imc.compareTo(new BigDecimal("25.00")) < 0) {
            return "Peso normal";
        }
        if (imc.compareTo(new BigDecimal("30.00")) < 0) {
            return "Sobrepeso";
        }
        return "Obesidad";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ConsultaMedica getConsultaMedica() { return consultaMedica; }
    public void setConsultaMedica(ConsultaMedica consultaMedica) { this.consultaMedica = consultaMedica; }
    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }
    public BigDecimal getTallaMetros() { return tallaMetros; }
    public void setTallaMetros(BigDecimal tallaMetros) { this.tallaMetros = tallaMetros; }
    public String getPresionArterial() { return presionArterial; }
    public void setPresionArterial(String presionArterial) { this.presionArterial = presionArterial; }
    public Integer getFrecuenciaCardiaca() { return frecuenciaCardiaca; }
    public void setFrecuenciaCardiaca(Integer frecuenciaCardiaca) { this.frecuenciaCardiaca = frecuenciaCardiaca; }
    public Integer getFrecuenciaRespiratoria() { return frecuenciaRespiratoria; }
    public void setFrecuenciaRespiratoria(Integer frecuenciaRespiratoria) { this.frecuenciaRespiratoria = frecuenciaRespiratoria; }
    public BigDecimal getTemperatura() { return temperatura; }
    public void setTemperatura(BigDecimal temperatura) { this.temperatura = temperatura; }
    public Integer getSaturacionOxigeno() { return saturacionOxigeno; }
    public void setSaturacionOxigeno(Integer saturacionOxigeno) { this.saturacionOxigeno = saturacionOxigeno; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
