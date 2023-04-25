package com.example.ddoverhaul;
/*METODOS PARA POSTERIORES MOVIMIENTOS*/
import androidx.appcompat.app.AppCompatActivity;

public class Metodos extends AppCompatActivity {
    /*OBJETOS*/
    public void anadirObj(Objeto obj){
        /*METER OBJETO MEDIANTE JSON*/
    };
    /*Metodo con icono*/
    public void addObject(String nombre, String tipo, String descripcion, String icono){
        int getLastId=0; /*Metodo que obtiene el ultimo id*/
        int id = getLastId;
        Objeto obj = new Objeto(id, nombre, tipo, descripcion, icono);
        anadirObj(obj);
    }
    /*Metodo sin icono*/
    public void addObject(String nombre, String tipo, String descripcion){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/
        Objeto obj = new Objeto(id,nombre, tipo, descripcion);
        anadirObj(obj);
    }

    /*EVENTOS*/
    public void anadirEvent(Evento evn){
        /*METER OBJETO MEDIANTE JSON*/
    };
    /*Metodo con objeto*/
    public void addEvento(String descripcion, int valor, int cantidad, char operacion, Objeto obj){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/
        Evento event = new Evento(id,  descripcion, valor, cantidad, operacion, obj);
        anadirEvent(event);
    }
    /*Metodo sin Objeto*/
    public void addEvento(String descripcion, int valor, int cantidad, char operacion){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/
        Evento event = new Evento(id,  descripcion, valor, cantidad, operacion);
        anadirEvent(event);
    }

    /*HEBILIDADES*/
    public void anadirHabili(Habilidades habili){
        /*METER OBJETO MEDIANTE JSON*/
    };
    /*Metodo con Icono*/
    public void addHabili(String nombre, int coste, int danio, String problema_estado, int porcentaje, String descripcion, String icono){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/
        Habilidades habili = new Habilidades(id, nombre, coste,danio, problema_estado, porcentaje,descripcion, icono);
        anadirHabili(habili);
    }
    /*Metodo sin Icono*/
    public void addHabili(String nombre, int coste, int danio, String problema_estado, int porcentaje, String descripcion){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/
        Habilidades habili = new Habilidades(id, nombre, coste,danio, problema_estado, porcentaje,descripcion);
        anadirHabili(habili);
    }

    /*EQUIPO*/
    public void anadirEquip(Equipo equip){
        /*METER OBJETO MEDIANTE JSON*/
    };
    /*Metodo con Icono*/
    public void addEquip(String nombre,String tipo, String descripcion, String icono, int danio, int posicion, int armadura){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/
        Equipo equip = new Equipo(id, nombre, tipo, descripcion, icono, danio, posicion, armadura);
        anadirEquip(equip);
    }
    /*Metodo sin Icono*/
    public void addEquip(String nombre,String tipo, String descripcion, int danio, int posicion, int armadura){
        int getLastId=0;
        int id = getLastId;/*Metodo que obtiene el ultimo id*/
        Equipo equip = new Equipo(id, nombre, tipo, descripcion, danio, posicion, armadura);
        anadirEquip(equip);
    }

}
