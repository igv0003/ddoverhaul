package com.example.ddoverhaul;

import java.util.Objects;

public class Objeto {
    private int id;
    private String nombre;
    private String tipo; /* Equipo, Consumible, Otro */
    private String descripcion;
    private String icono;

    /*Get Y Set*/
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
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Objeto(Objeto O) {
        this.id = O.id;
        this.nombre = O.nombre;
        this.tipo = O.tipo;
        this.descripcion = O.descripcion;
        this.icono = O.icono;
    }
    public Objeto(int id, String nombre, String tipo, String descripcion, String icono) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.icono = icono;
    }
    /*Constructor sin icono*/
    public Objeto(int id, String nombre, String tipo, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }
    public Objeto(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Objeto objeto = (Objeto) o;
        return id == objeto.id && Objects.equals(nombre, objeto.nombre) && Objects.equals(tipo, objeto.tipo) && Objects.equals(descripcion, objeto.descripcion) && Objects.equals(icono, objeto.icono);
    }


}
