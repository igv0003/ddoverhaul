package com.example.ddoverhaul;

public class Consumibles extends Objeto {
    private int valor;
    private int cantidad;
    private int operacion;

    /*Contructor*/
    public Consumibles(int id, String nombre, String tipo, String descripcion, String icono, int valor, int cantidad, int operacion) {
        super(id, nombre, tipo, descripcion, icono);
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
    }
}
