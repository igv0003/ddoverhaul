package com.example.ddoverhaul;

public class Evento {
    private int id;
    private String nombre;
    private String descripcion;
    private int valor;
    private int cantidad;
    private char operacion;
    private Objeto objeto;

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
    public Objeto getObjeto() {
        return objeto;
    }
    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }
    //Contructor sin Icono
    public Evento(int id, String nombre, String descripcion, int valor, int cantidad, char operacion, Objeto objeto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
        this.objeto = objeto;
    }
    //Constructor con Icono
    public Evento(int id, String nombre, String descripcion, int valor, int cantidad, char operacion, Objeto objeto, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.cantidad = cantidad;
        this.operacion = operacion;
        this.objeto = objeto;
        this.icono = icono;
    }
}
