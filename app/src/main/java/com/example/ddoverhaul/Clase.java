package com.example.ddoverhaul;

public class Clase {
    private int id;
    private String nombre;
    private int fuerza;
    private int destreza;
    private int constitucion;
    private int inteligencia;
    private int sabiduria;
    private int carsima;
    private int velocidad;

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
    public int getFuerza() {
        return fuerza;
    }
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }
    public int getDestreza() {
        return destreza;
    }
    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }
    public int getConstitucion() {
        return constitucion;
    }
    public void setConstitucion(int constitucion) {
        this.constitucion = constitucion;
    }
    public int getInteligencia() {
        return inteligencia;
    }
    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }
    public int getSabiduria() {
        return sabiduria;
    }
    public void setSabiduria(int sabiduria) {
        this.sabiduria = sabiduria;
    }
    public int getCarsima() {
        return carsima;
    }
    public void setCarsima(int carsima) {
        this.carsima = carsima;
    }
    public int getVelocidad() {
        return velocidad;
    }
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public Clase(int id, String nombre, int fuerza, int destreza, int constitucion, int inteligencia, int sabiduria, int carsima, int velocidad) {
        this.id = id;
        this.nombre = nombre;
        this.fuerza = fuerza;
        this.destreza = destreza;
        this.constitucion = constitucion;
        this.inteligencia = inteligencia;
        this.sabiduria = sabiduria;
        this.carsima = carsima;
        this.velocidad = velocidad;
    }
}
