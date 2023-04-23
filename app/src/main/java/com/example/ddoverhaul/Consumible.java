package com.example.ddoverhaul;

public class Consumible extends Objeto{
    private int valor;
    private int cantidad;
    char operacion;

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
    //Contructor Sin Icono
    public Consumible(int id, String nombre, String tipo, String descripcion, int valor, int cantidad, char operacion) {
        super(id, nombre, tipo, descripcion);
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
    }
    //Constructor con Icono
    public Consumible(int id, String nombre, String tipo, String descripcion, String icono, int valor, int cantidad, char operacion) {
        super(id, nombre, tipo, descripcion, icono);
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
    }
}
