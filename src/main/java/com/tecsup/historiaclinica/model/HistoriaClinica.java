package com.tecsup.historiaclinica.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_historia", nullable = false, unique = true)
    private String numeroHistoria;

    @Column(name = "fecha_apertura", nullable = false)
    private LocalDate fechaApertura;

    private String estado;

    @OneToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "id", unique = true)
    private Paciente paciente;

    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Atencion> atenciones = new java.util.ArrayList<>();

    // Constructor vacío (obligatorio para JPA)
    public HistoriaClinica() {
    }

    // Constructor con datos
    public HistoriaClinica(String numeroHistoria, LocalDate fechaApertura, String estado, Paciente paciente) {
        this.numeroHistoria = numeroHistoria;
        this.fechaApertura = fechaApertura;
        this.estado = estado;
        this.paciente = paciente;
    }

    // Getters y Setters
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

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Atencion> getAtenciones() {
        return atenciones;
    }

    public void setAtenciones(List<Atencion> atenciones) {
        this.atenciones = atenciones;
    }
}
