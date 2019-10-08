package com.home.frvajoao.picoyplacaapp.model;

public class DiaSemana {

    String id, descripcion;

    public DiaSemana() {
    }

    public DiaSemana(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
