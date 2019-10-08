package com.home.frvajoao.picoyplacaapp.model;

public class Bitacora {

    int id, infraccion;
    String fechaRegistro, horaRegistro, placa, fechaConsulta, horaConsulta;

    public Bitacora() {
    }

    public Bitacora(int id, String fechaRegistro, String horaRegistro, String placa, String fechaConsulta, String horaConsulta, int infraccion) {
        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.horaRegistro = horaRegistro;
        this.placa = placa;
        this.fechaConsulta = fechaConsulta;
        this.horaConsulta = horaConsulta;
        this.infraccion = infraccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getHoraRegistro() {
        return horaRegistro;
    }

    public void setHoraRegistro(String horaRegistro) {
        this.horaRegistro = horaRegistro;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(String fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getHoraConsulta() {
        return horaConsulta;
    }

    public void setHoraConsulta(String horaConsulta) {
        this.horaConsulta = horaConsulta;
    }

    public int getInfraccion() {
        return infraccion;
    }

    public void setInfraccion(int infraccion) {
        this.infraccion = infraccion;
    }
}
