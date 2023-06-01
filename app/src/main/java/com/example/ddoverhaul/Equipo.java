package com.example.ddoverhaul;

import java.util.Objects;

public class Equipo extends Objeto{
    private int danio;
    private int armadura;
    private int posicion;/*1.cabeza, 2.pecho, 3.guantes, 4.piernas, 5.pies, 6.arma_principal, 7.arma_secundaria*//*TEMPORAL*/

    /*GET Y SET*/
    public int getDanio() {
        return danio;
    }
    public void setDanio(int danio) {
        this.danio = danio;
    }
    public int getArmadura() {
        return armadura;
    }
    public void setArmadura(int armadura) {
        this.armadura = armadura;
    }
    public int getPosicion() {
        return posicion;
    }
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Equipo(int id, String nombre, String tipo, String descripcion, String icono, int danio, int posicion, int armadura) {
        super(id,nombre,tipo,descripcion,icono);
        this.danio = danio;
        this.posicion = posicion;
        this.armadura = armadura;
    }
    /*Constructor sin icono*/
    public Equipo(int id, String nombre, String tipo, String descripcion, int danio, int posicion, int armadura) {
        super(id,nombre,tipo,descripcion);
        this.danio = danio;
        this.posicion = posicion;
        this.armadura = armadura;
    }

    public Equipo(Equipo E) {
        super(E.getId(), E.getNombre(), E.getTipo(), E.getDescripcion(), E.getIcono());
        this.danio = E.danio;
        this.posicion = E.posicion;
        this.armadura = E.armadura;
    }
    public Equipo(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Equipo equipo = (Equipo) o;
        return danio == equipo.danio && armadura == equipo.armadura && posicion == equipo.posicion;
    }

}
