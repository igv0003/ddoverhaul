package com.example.ddoverhaul;

public class Habilidades {
    private int id;
    private String nombre;
    private int coste;
    private int danio;
    private String problema_estado;
    private int porcentaje;
    private String descripcion;
    private String icono; /*Imagen de la Habilidad*/

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
    public String getProblema_estado() {
        return problema_estado;
    }
    public void setProblema_estado(String problema_estado) {
        this.problema_estado = problema_estado;
    }
    public int getPorcentaje() {
        return porcentaje;
    }
    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
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

    public Habilidades(int id, String nombre, int coste, int danio, String problema_estado, int porcentaje, String descripcion, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.coste = coste;
        this.danio = danio;
        this.problema_estado = problema_estado;
        this.porcentaje = porcentaje;
        this.descripcion = descripcion;
        this.icono = icono;
    }

    /*Constructor sin icono*/
    public Habilidades(int id, String nombre, int coste, int danio, String problema_estado, int porcentaje, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.coste = coste;
        this.danio = danio;
        this.problema_estado = problema_estado;
        this.porcentaje = porcentaje;
        this.descripcion = descripcion;
        this.icono = null;
    }

    public Habilidades(Habilidades H) {
        this.id = H.id;
        this.nombre = H.nombre;
        this.coste = H.coste;
        this.danio = H.danio;
        this.problema_estado = H.problema_estado;
        this.porcentaje = H.porcentaje;
        this.descripcion = H.descripcion;
        this.icono = H.icono;
    }

    public Habilidades(){}


}
