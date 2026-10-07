package com.tecsup.historiaclinica.auditoria;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "auditoria_registros", indexes = @Index(name = "idx_auditoria_fecha", columnList = "fecha_hora"))
public class AuditoriaRegistro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100, updatable = false)
    private String usuario;
    @Column(name = "fecha_hora", nullable = false, updatable = false)
    private Instant fechaHora;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, updatable = false)
    private Operacion operacion;
    @Column(nullable = false, length = 80, updatable = false)
    private String entidad;
    @Column(name = "registro_id", nullable = false, updatable = false)
    private Long registroId;

    protected AuditoriaRegistro() {}
    public AuditoriaRegistro(String usuario, Operacion operacion, String entidad, Long registroId) {
        this.usuario = usuario;
        this.fechaHora = Instant.now();
        this.operacion = operacion;
        this.entidad = entidad;
        this.registroId = registroId;
    }
    public Long getId() { return id; }
    public String getUsuario() { return usuario; }
    public Instant getFechaHora() { return fechaHora; }
    public Operacion getOperacion() { return operacion; }
    public String getEntidad() { return entidad; }
    public Long getRegistroId() { return registroId; }
}
