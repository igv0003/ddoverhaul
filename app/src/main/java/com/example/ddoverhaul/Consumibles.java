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

    public Consumibles(Consumibles C) {
        super(C.getId(), C.getNombre(), C.getTipo(), C.getDescripcion(), C.getIcono());
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
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
    public int getOperacion() {
        return operacion;
    }
    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }


}
