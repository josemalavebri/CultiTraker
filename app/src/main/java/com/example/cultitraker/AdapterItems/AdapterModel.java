package com.example.cultitraker.AdapterItems;

public class AdapterModel {
    public String titulo ;
    public String subTitulo ;
    public String parrafo ;
    public String detail ;

    public AdapterModel() {
        this.titulo = "";
        this.subTitulo = "";
        this.parrafo = "";
        this.detail = "";
    }

    public AdapterModel(String titulo, String subTitulo, String parrafo, String detail) {
        this.titulo = titulo;
        this.subTitulo = subTitulo;
        this.parrafo = parrafo;
        this.detail = detail;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubTitulo() {
        return subTitulo;
    }

    public void setSubTitulo(String subTitulo) {
        this.subTitulo = subTitulo;
    }

    public String getParrafo() {
        return parrafo;
    }

    public void setParrafo(String parrafo) {
        this.parrafo = parrafo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
