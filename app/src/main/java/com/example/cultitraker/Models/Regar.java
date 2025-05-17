package com.example.cultitraker.Models;

public class Regar {
    private int id;
    private String fecha; // Fecha del evento de riego
    private String hora; // Hora opcional
    private double cantidadAgua; // Litros o m³
    private String metodoRiego; // Ej: goteo, aspersión, manual
    private int parcelaId; // Relación directa con Parcela

    // Constructor
    public Regar(int id, String fecha, String hora, double cantidadAgua, String metodoRiego, int parcelaId) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.cantidadAgua = cantidadAgua;
        this.metodoRiego = metodoRiego;
        this.parcelaId = parcelaId;
    }

    public Regar() {

    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getCantidadAgua() {
        return cantidadAgua;
    }

    public void setCantidadAgua(double cantidadAgua) {
        this.cantidadAgua = cantidadAgua;
    }

    public String getMetodoRiego() {
        return metodoRiego;
    }

    public void setMetodoRiego(String metodoRiego) {
        this.metodoRiego = metodoRiego;
    }

    public int getParcelaId() {
        return parcelaId;
    }

    public void setParcelaId(int parcelaId) {
        this.parcelaId = parcelaId;
    }
}
