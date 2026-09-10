package com.historiaclinica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "alergia")
public class Alergia {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String alergia;


    private String tipo;


    private String reaccion;


    private String observacion;


    private LocalDate fechaRegistro;



    @ManyToOne
    @JoinColumn(name="historia_clinica_id")
    @JsonBackReference
    private HistoriaClinica historiaClinica;



    public Alergia() {
    }



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getAlergia() {
        return alergia;
    }


    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getReaccion() {
        return reaccion;
    }


    public void setReaccion(String reaccion) {
        this.reaccion = reaccion;
    }


    public String getObservacion() {
        return observacion;
    }


    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }


    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }


    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    public HistoriaClinica getHistoriaClinica() {
        return historiaClinica;
    }


    public void setHistoriaClinica(HistoriaClinica historiaClinica) {
        this.historiaClinica = historiaClinica;
    }
}