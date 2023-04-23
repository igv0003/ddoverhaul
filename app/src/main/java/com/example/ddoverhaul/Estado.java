package com.example.ddoverhaul;

public class Estado {
    private int id;
    private String nombre;
    private int valor;
    private int cantidad;
    private char operacion;
    private int turnos;
    private String icono;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public int getTurnos() {
        return turnos;
    }
    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }
    public String getIcono() {
        return icono;
    }
    public void setIcono(String icono) {
        this.icono = icono;
    }

    //Contructor sin icono
    public Estado(int id, String nombre, int valor, int cantidad, char operacion, int turnos) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
        this.turnos = turnos;
    }
    //Contructor con icono
    public Estado(int id, String nombre, int valor, int cantidad, char operacion, int turnos, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
        this.turnos = turnos;
        this.icono = icono;
    }
}
