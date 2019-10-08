package com.home.frvajoao.picoyplacaapp.model;

public class Multa {

    int id;
    String placa, fechaRegistro;

    public Multa() {
    }

    public Multa(int id, String placa, String fechaRegistro) {
        this.id = id;
        this.placa = placa;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
