package com.example.cultitraker.Models;

public class Insumo {
    private int id;
    private String nombre;
    private String tipo;
    private int cantidad;
    private String fecha;
    private String proveedor;
    public Insumo() {
        id = 0;
        nombre = "";
        tipo = "";
        cantidad = 0;
        fecha = "";
        proveedor = "";
    }
    public Insumo(int id, String Nombre, String Tipo, int Cantidad, String Fecha, String Proveedor) {
        this.id = id;
        this.nombre = Nombre;
        this.tipo = Tipo;
        this.cantidad = Cantidad;
        this.fecha = Fecha;
        this.proveedor = Proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
