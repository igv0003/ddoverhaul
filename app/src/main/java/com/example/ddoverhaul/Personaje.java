package com.example.ddoverhaul;

public class Personaje {
    String nombre;
    private int vida;
    private int vida_Mx;
    private int mana;
    private int mana_Mx;
    private String raza;
    private String Clase;
    /*Estadisticas*/
    private int fuerza;
    private int destreza;
    private int constitucion;
    private int inteligencia;
    private int sabiduria;
    private int carsima;
    private int velocidad;
    private boolean vivo;
    /*Equipo*/
    Objeto Arma;
    Objeto Arma_Sec;
    Objeto Perchera;
    Objeto Pantalones;
    Objeto Guantes;
    Objeto Pies;
    Objeto Accesorios;

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVida() {
        return vida;
    }

    public int getVida_Mx() {
        return vida_Mx;
    }

    public void setVida_Mx(int vida_Mx) {
        this.vida_Mx = vida_Mx;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana_Mx() {
        return mana_Mx;
    }

    public void setMana_Mx(int mana_Mx) {
        this.mana_Mx = mana_Mx;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getClase() {
        return Clase;
    }

    public void setClase(String clase) {
        Clase = clase;
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

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
}
