package com.example.ddoverhaul;

public class Consumibles extends Objeto {
    private int valor;
    private int cantidad;
    private char operacion;

    /*Contructor*/
    public Consumibles(int id, String nombre, String tipo, String descripcion, String icono, int valor, int cantidad, char operacion) {
        super(id, nombre, tipo, descripcion, icono);
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
    }
    /*Constructor sin Icono*/
    public Consumibles(int id, String nombre, String tipo, String descripcion, int valor, int cantidad, char operacion) {
        super(id, nombre, tipo, descripcion, null);
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
    }

    public Consumibles(Consumibles C) {
        super(C.getId(), C.getNombre(), C.getTipo(), C.getDescripcion(), C.getIcono());
        this.valor = C.valor;
        this.cantidad = C.cantidad;
        this.operacion = C.operacion;
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
    public Consumibles(){}

}
