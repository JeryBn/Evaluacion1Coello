package com.tecsup.historiaclinica.usuarios;

import jakarta.persistence.*;

@Entity @Table(name="roles")
public class Rol {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true, length=40) private String codigo;
    @Column(nullable=false, length=80) private String nombre;
    @Column(nullable=false) private boolean activo = true;
    public Long getId(){return id;}
    public String getCodigo(){return codigo;}
    public void setCodigo(String v){codigo=v;}
    public String getNombre(){return nombre;}
    public void setNombre(String v){nombre=v;}
    public boolean isActivo(){return activo;}
    public void setActivo(boolean v){activo=v;}
}
