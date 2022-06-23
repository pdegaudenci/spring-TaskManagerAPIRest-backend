package com.example.springbackendAPIREST.entidades;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Entity
@Table(name ="Tareas")
@ApiModel("Representa un conjunto de tareas personales almacenadas para su mejor gestion ")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave primaria autoincremental de tipo Long")
    private Long id;
    private String nombre;
    private String descripcion;
    @ApiModelProperty("Niveles permitidos: NORMAL, URGENTE, BLOQUEANTE")
    private LEVELS nivel;
    @Column(name="Estado_de_finalizacion")
    private Boolean completado;

    public Tarea() {

    }

    public Tarea(Long id, String nombre, String descripcion, LEVELS nivel, boolean completado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.completado = completado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LEVELS getNivel() {
        return nivel;
    }

    public void setNivel(LEVELS nivel) {
        this.nivel = nivel;
    }

    public Boolean isCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nivel=" + nivel +
                ", completado=" + completado +
                '}';
    }
}
