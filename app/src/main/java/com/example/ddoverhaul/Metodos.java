package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;

public class Metodos extends AppCompatActivity {

    public void anadirObj(Objeto obj){};

    public void addObject(String nombre, String tipo, String descripcion, String icono){
        int getLastId=0; /*Metodo que obtiene el ultimo id*/
        int id = getLastId;
        Objeto obj = new Objeto(id, nombre, tipo, descripcion, icono);
        anadirObj(obj);
    }


    public void addObject(String nombre, String tipo, String descripcion){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/
        Objeto obj = new Objeto(id,nombre, tipo, descripcion);
        anadirObj(obj);
    }

    public void addEvento(String descripcion, int valor, int cantidad, char operacion){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/

    }

    public void addEvento( int valor, int cantidad, char operacion){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/
    }
}
