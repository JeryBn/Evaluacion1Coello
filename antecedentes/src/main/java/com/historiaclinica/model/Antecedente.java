package com.historiaclinica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "antecedente")
public class Antecedente {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String tipo;


    private String descripcion;


    private LocalDate fechaRegistro;



    @ManyToOne
    @JoinColumn(name="historia_clinica_id")
    @JsonBackReference
    private HistoriaClinica historiaClinica;



    public Antecedente() {
    }



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTipo() {
        return tipo;
    }


    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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