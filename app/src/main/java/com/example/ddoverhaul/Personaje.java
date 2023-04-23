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
    private String raza; /*PROVISIONAL*/
    /*
    DADO
    */
    private String Clase;
    /*
    Estadisticas
    private int fuerza;
    private int destreza;
    private int constitucion;
    private int inteligencia;
    private int sabiduria;
    private int carsima;
    private int velocidad;
     */

    private Clase clase;

    private boolean vivo;

    private String icono;
    /*Equipo*/
    private Equipo Arma;
    private Equipo Arma_Sec;
    private Equipo Perchera;
    private Equipo Pantalones;
    private Equipo Guantes;
    private Equipo Pies;
    private Equipo Accesorios;

    private ArrayList<Objeto> inventario; /*Inventario es un arrayList de Objetos*/
    private ArrayList<Habilidades> habilidades;
    /*GET Y SET*/

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        
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

    public void setVida(int vida) {
        this.vida = vida;
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

    public void setClase(com.example.ddoverhaul.Clase clase) {
        this.clase = clase;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public Equipo getArma() {
        return Arma;
    }

    public void setArma(Equipo arma) {
        Arma = arma;
    }

    public Equipo getArma_Sec() {
        return Arma_Sec;
    }

    public void setArma_Sec(Equipo arma_Sec) {
        Arma_Sec = arma_Sec;
    }

    public Equipo getPerchera() {
        return Perchera;
    }

    public void setPerchera(Equipo perchera) {
        Perchera = perchera;
    }

    public Equipo getPantalones() {
        return Pantalones;
    }

    public void setPantalones(Equipo pantalones) {
        Pantalones = pantalones;
    }

    public Equipo getGuantes() {
        return Guantes;
    }

    public void setGuantes(Equipo guantes) {
        Guantes = guantes;
    }

    public Equipo getPies() {
        return Pies;
    }

    public void setPies(Equipo pies) {
        Pies = pies;
    }

    public Equipo getAccesorios() {
        return Accesorios;
    }

    public void setAccesorios(Equipo accesorios) {
        Accesorios = accesorios;
    }

    public ArrayList<Objeto> getInventario() {
        return inventario;
    }

    public void setInventario(ArrayList<Objeto> inventario) {
        this.inventario = inventario;
    }

    public ArrayList<Habilidades> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(ArrayList<Habilidades> habilidades) {
        this.habilidades = habilidades;
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

    public void setClase(String clase) {
        Clase = clase;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
    /*CONSTRUCTORES*/
    //sin icono
    public Personaje(int id, String nombre, String descripcion, int vida, int vida_Mx, int mana, int mana_Mx, String raza, String clase, com.example.ddoverhaul.Clase clase1, boolean vivo, Equipo arma, Equipo arma_Sec, Equipo perchera, Equipo pantalones, Equipo guantes, Equipo pies, Equipo accesorios, ArrayList<Objeto> inventario, ArrayList<Habilidades> habilidades) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.vida = vida;
        this.vida_Mx = vida_Mx;
        this.mana = mana;
        this.mana_Mx = mana_Mx;
        this.raza = raza;
        Clase = clase;
        this.clase = clase1;
        this.vivo = vivo;
        Arma = arma;
        Arma_Sec = arma_Sec;
        Perchera = perchera;
        Pantalones = pantalones;
        Guantes = guantes;
        Pies = pies;
        Accesorios = accesorios;
        this.inventario = inventario;
        this.habilidades = habilidades;
    }
    //con icono
    public Personaje(int id, String nombre, String descripcion, int vida, int vida_Mx, int mana, int mana_Mx, String raza, String clase, com.example.ddoverhaul.Clase clase1, boolean vivo, String icono, Equipo arma, Equipo arma_Sec, Equipo perchera, Equipo pantalones, Equipo guantes, Equipo pies, Equipo accesorios, ArrayList<Objeto> inventario, ArrayList<Habilidades> habilidades) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.vida = vida;
        this.vida_Mx = vida_Mx;
        this.mana = mana;
        this.mana_Mx = mana_Mx;
        this.raza = raza;
        Clase = clase;
        this.clase = clase1;
        this.vivo = vivo;
        this.icono = icono;
        Arma = arma;
        Arma_Sec = arma_Sec;
        Perchera = perchera;
        Pantalones = pantalones;
        Guantes = guantes;
        Pies = pies;
        Accesorios = accesorios;
        this.inventario = inventario;
        this.habilidades = habilidades;
    }
}
