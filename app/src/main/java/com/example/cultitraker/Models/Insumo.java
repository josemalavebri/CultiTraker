package com.example.cultitraker.Models;

public class Insumo {
    private int ID;
    private String Nombre;
    private String Tipo;
    private int Cantidad;
    private String Fecha;
    private String Proveedor;
    public Insumo() {
        ID = 0;
        Nombre = "";
        Tipo = "";
        Cantidad = 0;
        Fecha = "";
        Proveedor = "";
    }
    public Insumo(int ID, String Nombre, String Tipo, int Cantidad, String Fecha, String Proveedor) {
        this.ID = ID;
        this.Nombre = Nombre;
        this.Tipo = Tipo;
        this.Cantidad = Cantidad;
        this.Fecha = Fecha;
        this.Proveedor = Proveedor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String proveedor) {
        Proveedor = proveedor;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }
}
