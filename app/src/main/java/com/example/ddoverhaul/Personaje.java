package com.example.ddoverhaul;

import java.util.ArrayList;

public class Personaje {

    private int id;
    private int nivel;
    private String nombre;
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
    private String imagen;/*imagen del personaje*/

    /*Equipo*/
    Objeto arma;
    Objeto arma_sec;
    Objeto cabeza;
    Objeto perchera;
    Objeto pantalones;
    Objeto guantes;
    Objeto pies;
    Objeto[] accesorios = new Objeto[2];

    ArrayList<Objeto> inventario; /*Inventario es un gran arrayList de Objetos(Objeto)*/
    ArrayList<Habilidades> habilidades; /*Habilidades es un arrayList de Habilidades(Objeto)*/

    /*Get Y Set*/

    public int getNivel() {
        return nivel;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
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
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /*CONSTRUCTORES*/

    public Personaje() {

    }

    public Personaje(int id, String nombre, int vida, int mana, String raza, String clase, int fuerza, int destreza, int constitucion, int inteligencia, int sabiduria, int carsima, int velocidad, boolean vivo, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.vida = vida;
        this.vida_Mx = vida;
        this.mana = mana;
        this.mana_Mx = mana;
        this.raza = raza;
        Clase = clase;
        this.fuerza = fuerza;
        this.destreza = destreza;
        this.constitucion = constitucion;
        this.inteligencia = inteligencia;
        this.sabiduria = sabiduria;
        this.carsima = carsima;
        this.velocidad = velocidad;
        this.vivo = vivo;
        this.imagen = imagen;
    }
    /*Constructor sin imagen*/
    public Personaje(int id, String nombre, int vida, int mana, String raza, String clase, int fuerza, int destreza, int constitucion, int inteligencia, int sabiduria, int carsima, int velocidad, boolean vivo) {
        this.id = id;
        this.nombre = nombre;
        this.vida = vida;
        this.vida_Mx = vida;
        this.mana = mana;
        this.mana_Mx = mana;
        this.raza = raza;
        Clase = clase;
        this.fuerza = fuerza;
        this.destreza = destreza;
        this.constitucion = constitucion;
        this.inteligencia = inteligencia;
        this.sabiduria = sabiduria;
        this.carsima = carsima;
        this.velocidad = velocidad;
        this.vivo = vivo;
    }
}
