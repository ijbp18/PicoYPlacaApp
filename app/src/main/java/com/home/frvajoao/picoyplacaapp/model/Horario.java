package com.home.frvajoao.picoyplacaapp.model;

public class Horario {

    int id;
    String diaSemana, placaIni, placaFin;

    public Horario() {
    }

    public Horario(int id, String diaSemana, String placaIni, String placaFin) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.placaIni = placaIni;
        this.placaFin = placaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getPlacaIni() {
        return placaIni;
    }

    public void setPlacaIni(String placaIni) {
        this.placaIni = placaIni;
    }

    public String getPlacaFin() {
        return placaFin;
    }

    public void setPlacaFin(String placaFin) {
        this.placaFin = placaFin;
    }
}
