package com.example.ddoverhaul;

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


    public Equipo(int id, String nombre, String tipo, String descripcion, int danio, int armadura, int posicion) {
        super(id, nombre, tipo, descripcion);
        this.danio = danio;
        this.armadura = armadura;
        this.posicion = posicion;
    }

    public Equipo(int id, String nombre, String tipo, String descripcion, String icono, int danio, int armadura, int posicion) {
        super(id, nombre, tipo, descripcion, icono);
        this.danio = danio;
        this.armadura = armadura;
        this.posicion = posicion;

    }
}
