package com.example.cultitraker.Models;

public class Parcela {
    public int id;
    public String nombre;
    public double tamano;
    public String cultivo;
    public int cantidadCultivo;
    public Parcela(int id, String nombre, double tamano, String cultivo, int cantidadCultivo) {
        this.id = id;
        this.nombre = nombre;
        this.tamano = tamano;
        this.cultivo = cultivo;
        this.cantidadCultivo = cantidadCultivo;
    }

    public Parcela() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTamano() {
        return tamano;
    }

    public void setTamano(double tamano) {
        this.tamano = tamano;
    }

    public String getCultivo() {
        return cultivo;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }

    public int getCantidadCultivo() {
        return cantidadCultivo;
    }

    public void setCantidadCultivo(int cantidadCultivo) {
        this.cantidadCultivo = cantidadCultivo;
    }

}