package com.historiaclinica.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String numeroHistoria;


    private String fechaCreacion;


    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;



    @OneToMany(
            mappedBy = "historiaClinica",
            cascade = CascadeType.ALL
    )
    private List<Antecedente> antecedentes;



    @OneToMany(
            mappedBy="historiaClinica",
            cascade=CascadeType.ALL
    )
    @JsonManagedReference
    private List<Alergia> alergias;



    public HistoriaClinica() {
    }



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNumeroHistoria() {
        return numeroHistoria;
    }


    public void setNumeroHistoria(String numeroHistoria) {
        this.numeroHistoria = numeroHistoria;
    }


    public String getFechaCreacion() {
        return fechaCreacion;
    }


    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public Paciente getPaciente() {
        return paciente;
    }


    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }


    public List<Antecedente> getAntecedentes() {
        return antecedentes;
    }


    public void setAntecedentes(List<Antecedente> antecedentes) {
        this.antecedentes = antecedentes;
    }


    public List<Alergia> getAlergias() {
        return alergias;
    }


    public void setAlergias(List<Alergia> alergias) {
        this.alergias = alergias;
    }
}