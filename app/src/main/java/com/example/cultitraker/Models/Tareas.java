package com.example.cultitraker.Models;

public class Tareas {
    public int id;
    public String tipoActividad;
    public String descripcion;
    public String estado;
    public String fecha;

    public Tareas(int id,String tipoActividad, String descripcion, String estado, String fecha) {
        this.id=id;
        this.tipoActividad = tipoActividad;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Tareas(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
