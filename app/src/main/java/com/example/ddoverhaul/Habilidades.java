package com.example.ddoverhaul;

public class Habilidades {
    private int id;
    private String nombre;
    private int coste;
    private int danio;
    private Estado estado;
    private int probabilidad;
    private String descripcion;
    private String icono;

    /*Get Y Set*/
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCoste() {
        return coste;
    }
    public void setCoste(int coste) {
        this.coste = coste;
    }
    public int getDanio() {
        return danio;
    }
    public void setDanio(int danio) {
        this.danio = danio;
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public int getProbabilidad() {
        return probabilidad;
    }
    public void setProbabilidad(int probabilidad) {
        this.probabilidad = probabilidad;
    }
    //Constructor sin Icono
    public Habilidades(int id, String nombre, int coste, int danio, Estado estado, int probabilidad, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.coste = coste;
        this.danio = danio;
        this.estado = estado;
        this.probabilidad = probabilidad;
        this.descripcion = descripcion;
    }
    //Contructor con icono

    public Habilidades(int id, String nombre, int coste, int danio, Estado estado, int probabilidad, String descripcion, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.coste = coste;
        this.danio = danio;
        this.estado = estado;
        this.probabilidad = probabilidad;
        this.descripcion = descripcion;
        this.icono = icono;
    }
}
