package com.example.ddoverhaul;

public class Objeto {

    private int id;
    private String nombre;
    private String tipo; /* Equipo, Consumible, Otro */
    private String descripcion;
    private String icono;
    
    /*Get Y Set*/
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
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getIcono() {
        return icono;
    }
    public void setIcono(String icono) {
        this.icono = icono;
    }
    //Constructor sin icono
    public Objeto(int id, String nombre, String tipo, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }
    //Constructor con icono
    public Objeto(int id, String nombre, String tipo, String descripcion, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.icono = icono;
    }
}
