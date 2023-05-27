package com.example.ddoverhaul;

import java.io.Serializable;
import java.util.ArrayList;

public class Personaje implements Serializable {

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
    private int carisma;
    private int velocidad;
    private boolean vivo;
    private String imagen;/*imagen del personaje*/

    /*Equipo*/
    private Equipo arma;
    private Equipo arma_sec;
    private Equipo cabeza;
    private Equipo perchera;
    private Equipo pantalones;
    private Equipo guantes;
    private Equipo pies;

    ArrayList<Objeto> accesorios;
    private ArrayList<Objeto> inventario; /*Inventario es un gran arrayList de Objetos(Objeto)*/
    private ArrayList<Habilidades> habilidades; /*Habilidades es un arrayList de Habilidades(Objeto)*/

    /*Get Y Set*/

    public void setArma(Equipo obj){
        this.arma = obj;
    }
    public void setArma_sec(Equipo obj){this.arma_sec = obj;}
    public void setCabeza(Equipo obj){
        this.cabeza = obj;
    }
    public void setPerchera(Equipo obj){
        this.perchera = obj;
    }
    public void setPantalones(Equipo obj){
        this.pantalones = obj;
    }
    public void setGuantes(Equipo obj){
        this.guantes = obj;
    }
    public void setPies(Equipo obj){
        this.pies = obj;
    }

    public Equipo getArma() { return this.arma; }
    public Equipo getArma_sec() { return this.arma_sec; }
    public Equipo getCabeza() { return this.cabeza; }
    public Equipo getPerchera() { return this.perchera; }
    public Equipo getPantalones() { return this.pantalones; }
    public Equipo getGuantes() { return this.guantes; }
    public Equipo getPies() { return this.pies; }

    public ArrayList<Objeto> getInventario() { return this.inventario; }
    public void addToInventory(Objeto obj) { this.inventario.add(obj); }
    public void removeFromInventory(Objeto obj) { this.inventario.remove(obj); }

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
    public int getCarisma() {
        return carisma;
    }
    public void setCarisma(int carisma) {
        this.carisma = carisma;
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

    public Personaje() {}

    public Personaje(String nombre, int vida, int mana, String raza, String clase, int fuerza, int destreza, int constitucion, int inteligencia, int sabiduria, int carisma, int velocidad, boolean vivo, String imagen) {
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
        this.carisma = carisma;
        this.velocidad = velocidad;
        this.vivo = vivo;
        this.imagen = imagen;
    }
    /*Constructor sin imagen*/
    public Personaje(int id, String nombre, int vida, int mana, String raza, String clase, int fuerza, int destreza, int constitucion, int inteligencia, int sabiduria, int carisma, int velocidad, boolean vivo) {
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
        this.carisma = carisma;
        this.velocidad = velocidad;
        this.vivo = vivo;
    }

    public Personaje(Personaje P) {
        this.id = P.id;
        this.nombre = P.nombre;
        this.vida = P.vida;
        this.vida_Mx = P.vida;
        this.mana = P.mana;
        this.mana_Mx = P.mana;
        this.raza = P.raza;
        Clase = P.Clase;
        this.fuerza = P.fuerza;
        this.destreza = P.destreza;
        this.constitucion = P.constitucion;
        this.inteligencia = P.inteligencia;
        this.sabiduria = P.sabiduria;
        this.carisma = P.carisma;
        this.velocidad = P.velocidad;
        this.vivo = P.vivo;
    }
}
