package com.example.cultitraker.Models;

public class Cultivo {
    private int id;
    private String nombre;
    private String tipo;
    private String fechaSiembra;


    public Cultivo(){
    }
    public Cultivo(int id, String nombre, String tipo, String fechaSiembra) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fechaSiembra = fechaSiembra;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getFechaSiembra() { return fechaSiembra; }
    public void setFechaSiembra(String fechaSiembra) { this.fechaSiembra = fechaSiembra; }

    @Override
    public String toString() {
        return nombre; // o cualquier campo legible como "tipo" o "variedad"
    }
}
