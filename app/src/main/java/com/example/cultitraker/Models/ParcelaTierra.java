package com.example.cultitraker.Models;

public class ParcelaTierra {
    public int id;
    public String nombre;
    public int tamano;
    public String cultivo;
    public int cantidadCultivo;
    public ParcelaTierra(int id, String nombre, int tamano, String cultivo, int cantidadCultivo) {
        this.id = id;
        this.nombre = nombre;
        this.tamano = tamano;
        this.cultivo = cultivo;
        this.cantidadCultivo = cantidadCultivo;
    }

    public ParcelaTierra() {

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

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
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
