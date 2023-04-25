package com.example.ddoverhaul;

public class Evento {
    private int id;
    private String descripcion;
    private int valor;
    private int cantidad;
    private char operacion;
    private Objeto obj;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public char getOperacion() {
        return operacion;
    }
    public void setOperacion(char operacion) {
        this.operacion = operacion;
    }
    public Objeto getObj() {
        return obj;
    }
    public void setObj(Objeto obj) {
        this.obj = obj;
    }

    /*Metodo que recibe id y Objeto*/
    public Evento(int id, String descripcion, int valor, int cantidad, char operacion, Objeto obj) {
        this.id = id;
        this.descripcion = descripcion;
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
        this.obj = obj;
    }
    /*Metodo que no recibe Objeto*/
    public Evento(int id, String descripcion, int valor, int cantidad, char operacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
    }
}
