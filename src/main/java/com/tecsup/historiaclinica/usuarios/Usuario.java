package com.tecsup.historiaclinica.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity @Table(name="usuarios")
public class Usuario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=50) private String username;
    @JsonIgnore @Column(nullable=false, name="password_hash", length=100) private String passwordHash;
    @Column(nullable=false) private boolean activo = true;
    @ManyToOne(optional=false) @JoinColumn(name="rol_id", nullable=false) private Rol rol;
    public Long getId(){return id;}
    public String getUsername(){return username;}
    public void setUsername(String v){username=v;}
    public String getPasswordHash(){return passwordHash;}
    public void setPasswordHash(String v){passwordHash=v;}
    public boolean isActivo(){return activo;}
    public void setActivo(boolean v){activo=v;}
    public Rol getRol(){return rol;}
    public void setRol(Rol v){rol=v;}
}
