package com.example.ddoverhaul;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONHelper {
    Context context;
    String fileChar = "personaje.json";
    String fileObj = "objeto.json";
    String fileEquip = "equipo.json";
    String fileCons = "consumible.json";
    String fileEvent = "evento.json";
    String fileSkill = "habilidad.json";

    public JSONHelper (Context c){
        context = c;
    }

    // Método que recibe nombre del archivo json y lo devuelve como string
    public String getJSON (String jsonFile){
        String json=null;
        try {
            AssetManager assetManager = context.getAssets();
            File file = new File(context.getFilesDir(),jsonFile);
            InputStream inputStream;
            if (file.exists()){
                inputStream = context.openFileInput(jsonFile);
            } else {
                inputStream = assetManager.open(jsonFile);
            }

            if (inputStream != null) {
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                json = new String(buffer, StandardCharsets.UTF_8);
                // Gson es una librería de Google que convierne archivos JSON en Objetos Json de manera sencilla
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(json);
        return json;
    }

    // Método que recibe el nombre del fichero y el json a guardar
    public void saveJsonToFile (String fileName, String json){
        try {
            File file = new File(context.getFilesDir(),fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // ----- MÉTODOS PARA OBJETO PERSONAJE -----

    // Método que devuelve un Personaje recibiendo su id por parámetro, devuelve null si no lo encuentra
    public Personaje getChar(int id){
        // Crea un array de Personaje partiendo del json
        String jsonStr = getJSON(fileChar);
        Gson gson = new Gson();
        // Se recoge el array de personajes usando el json
        Personaje[] characters = gson.fromJson(jsonStr,Personaje[].class);

        // Se recorre el array de Personaje, si el id coincide lo devuelve
        for (Personaje character : characters) {
            if (character.getId() == id) {
                return character;
            }
        }
        return null;
    }

    // Método que devuelve todos los Personajes
    public Personaje[] getChars(){
        // Crea un array de Personaje partiendo del json
        String jsonStr = getJSON(fileChar);
        Gson gson = new Gson();
        // Devuelve el array creado a partir del json
        return gson.fromJson(jsonStr,Personaje[].class);
    }

    // Método que añade un personaje nuevo al array de Personajes, recibe el personaje a guardar
    public void addCharacter (Personaje chara){
        String jsonStr = getJSON(fileChar);
        Gson gson = new Gson();
        // Se recoge el array de personajes usando el json
        Personaje[] characters = gson.fromJson(jsonStr,Personaje[].class);

        // Se crea un nuevo array que guardará el nuevo personaje
        Personaje[] newCharacters = new Personaje[characters.length +1];
        for (int i = 0; i < characters.length; i++) {
            Personaje p = new Personaje (characters[i]);
            newCharacters[i] = p;
        }

        // Se crea el nuevo personaje a añadir partiendo del personaje recibido
        Personaje newChar = new Personaje(chara);

        // Se recorren los personajes comprobando que las id concuerdan en orden, la última posicion siempre es null
        boolean exist = false;
        for (int i = 0; i < newCharacters.length-1; i++) {
            // Si una id no concuerda en orden, es que falta un personaje
            if (newCharacters[i].getId() != i) {
                // Se le añade la id faltante al nuevo personaje
                newChar.setId(i);
                i = newCharacters.length;
                exist = true;
            }
        }
        // Si las id concuerdan, entonces se le asigna la última id
        if (!exist) {
            newChar.setId(newCharacters.length-1);
        }
        // Se añade el personaje con la id asignada al array de personajes
        newCharacters[characters.length] = newChar;

        // Se ordena el array en caso de fallo de ids
        newCharacters = sortCharacters(newCharacters);

        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(newCharacters);
        saveJsonToFile(fileChar,prettyJson);
    }

    public void updateCharacter (Personaje chara){
        String jsonStr = getJSON(fileChar);
        Gson gson = new Gson();
        // Se recoge el array de personajes usando el json
        Personaje[] characters = gson.fromJson(jsonStr,Personaje[].class);
        // Se recorre el array, cuando el personaje coincida con el recibido, se actualiza en el array
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].getId() == chara.getId()){
                Personaje p = new Personaje (chara);
                characters[i] = p;
                i = characters.length;
            }
        }
        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(characters);
        saveJsonToFile(fileChar,prettyJson);
    }

    // Método que borra un personaje de la lista de caracteres, recibe un id por parámetro
    public void deleteCharacter(int id) {
        // Crea un array de Personaje partiendo del json
        String jsonStr = getJSON(fileChar);
        Gson gson = new Gson();
        // Se recoge el array de personajes usando el json
        Personaje[] characters = gson.fromJson(jsonStr,Personaje[].class);
        boolean deleted = false;
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].getId() == id) {
                deleted = true;
                characters[i] = null;
                i = characters.length;
            }
        }
        if (deleted) {
            // Se crea el nuevo array sin el personaje a borrar
            Personaje[] newCharacters = new Personaje[characters.length - 1];
            // Se recorre el nuevo array, si el personaje no es null, se guarda en el nuevo array
            int size = 0;
            for (Personaje character : characters) {
                if (character != null) {
                    Personaje p = new Personaje(character);
                    newCharacters[size] = p;
                    size++;
                }
            }

            // Se ordena el array en caso de fallo de ids
            newCharacters = sortCharacters(newCharacters);

            // No tocar, código encargado de preparar el String json y llamar al guardado
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(newCharacters);
            saveJsonToFile(fileChar,prettyJson);
        }
    }

    // Método que recibe un array de Personajes y los ordena por id
    public Personaje[] sortCharacters(Personaje[] characters){
        Personaje[] sortCharacters = new Personaje[characters.length];
        int loops = sortCharacters.length;
        // Se guarda un personaje para empezar a ordenar
        Personaje charToSave = new Personaje();
        for (int i = 0; i < characters.length; i++) {
            if (characters[i] != null) {
                charToSave = new Personaje(characters[i]);
                i = characters.length;
            }
        }
        int savedPos = 0;
        // Se recorre el nuevo array añadiendo los personajes ordenados
        for (int i = 0; i < sortCharacters.length; i++) {
            // En cada vuelta se recorre el array recibido buscando el id más bajo
            for (int j = 0; j < characters.length; j++) {
                if (characters[j] != null && characters[j].getId() < charToSave.getId()) {
                    charToSave = new Personaje(characters[j]);
                    savedPos = j;
                }
            }
            // Una vez conseguida la id más baja, se guarda en la posicion actual
            sortCharacters[i] = new Personaje(charToSave);
            // Se elimina el personaje del array recibido para que no se repita
            characters[savedPos] = null;
            // Se aumenta la id del personaje guardado para nunca tener la id más baja
            charToSave.setId(999);

        }

        // Devuelve el nuevo array con los personajes ordenados por id
        return sortCharacters;
    }


    // ----- MÉTODOS PARA OBJETOS TIPO OTROS -----

    // Método que devuelve un objeto recibiendo su id por parámetros
    public Objeto getObject(int id) {
        // Crea un array de Objetos partiendo del json
        String jsonStr = getJSON(fileObj);
        Gson gson = new Gson();
        // Se recoge el array de objetos usando el json
        Objeto[] objects = gson.fromJson(jsonStr,Objeto[].class);

        // Se recorre el array de objectos, si el id coincide lo devuelve
        for (Objeto obj : objects) {
            if (obj.getId() == id) {
                return obj;
            }
        }
        return null;
    }

    // Método que devuelve todos los Objetos
    public Objeto[] getObjects(){
        // Crea un array de Objetos partiendo del json
        String jsonStr = getJSON(fileObj);
        Gson gson = new Gson();
        // Devuelve el array creado a partir del json
        return gson.fromJson(jsonStr,Objeto[].class);
    }

    // Método que añade un objeto nuevo al array de Objetos de tipo Otros
    public void addObject(Objeto obj) {
        String jsonStr = getJSON(fileObj);
        Gson gson = new Gson();
        // Se recoge el array de objetos usando el json
        Objeto[] objects = gson.fromJson(jsonStr,Objeto[].class);

        // Se crea un nuevo array que guardará el nuevo objeto
        Objeto[] newObjects = new Objeto[objects.length +1];
        for (int i = 0; i < objects.length; i++) {
            Objeto o = new Objeto (objects[i]);
            newObjects[i] = o;
        }
        // Se crea el nuevo objeto a añadir partiendo del objeto recibido
        Objeto newObj = new Objeto(obj);

        // Se recorren los objetos comprobando que las id concuerdan en orden, la última posicion siempre es null
        boolean exist = false;
        for (int i = 0; i < newObjects.length-1; i++) {
            // Si una id no concuerda en orden, es que falta un personaje
            if (newObjects[i].getId() != i) {
                // Se le añade la id faltante al nuevo personaje
                newObj.setId(i);
                i = newObjects.length;
                exist = true;
            }
        }
        // Si las id concuerdan, entonces se le asigna la última id
        if (!exist) {
            newObj.setId(newObjects.length-1);
        }
        // Se añade el objeto con la id asignada al array de objetos
        newObjects[objects.length] = newObj;

        // Se ordena el array en caso de fallo de ids
        newObjects = sortObjects(newObjects);

        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(newObjects);
        saveJsonToFile(fileObj,prettyJson);
    }

    public void updateObject (Objeto o){
        String jsonStr = getJSON(fileObj);
        Gson gson = new Gson();
        // Se recoge el array de objetos usando el json
        Objeto[] objects = gson.fromJson(jsonStr,Objeto[].class);
        // Se recorre el array, cuando el objeto coincida con el recibido, se actualiza en el array
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].getId() == o.getId()){
                Objeto obj = new Objeto (o);
                objects[i] = obj;
                i = objects.length;
            }
        }
        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(objects);
        saveJsonToFile(fileObj,prettyJson);
    }

    // Método que borra un objeto de la lista de objetos, recibe un id por parámetro
    public void deleteObject(int id) {
        // Crea un array de Objeto partiendo del json
        String jsonStr = getJSON(fileObj);
        Gson gson = new Gson();
        // Se recoge el array de objetos usando el json
        Objeto[] objects = gson.fromJson(jsonStr,Objeto[].class);
        boolean deleted = false;
        for (int i = 0; i < objects.length; i++) {
            if (objects[i].getId() == id) {
                deleted = true;
                objects[i] = null;
                i = objects.length;
            }
        }
        if (deleted) {
            // Se crea el nuevo array sin el objeto a borrar
            Objeto[] newObjects = new Objeto[objects.length - 1];
            // Se recorre el nuevo array, si el objeto no es null, se guarda en el nuevo array
            int size = 0;
            for (Objeto object : objects) {
                if (object != null) {
                    Objeto obj = new Objeto(object);
                    newObjects[size] = obj;
                    size++;
                }
            }

            // Se ordena el array en caso de fallo de ids
            newObjects = sortObjects(newObjects);

            // No tocar, código encargado de preparar el String json y llamar al guardado
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(newObjects);
            saveJsonToFile(fileObj,prettyJson);
        }
    }

    // Método que recibe un array de Objetos y los ordena por id
    public Objeto[] sortObjects(Objeto[] objects){
        Objeto[] sortObjects = new Objeto[objects.length];
        int loops = sortObjects.length;
        // Se guarda un objeto para empezar a ordenar
        Objeto objToSave = new Objeto();
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                objToSave = new Objeto(objects[i]);
                i = objects.length;
            }
        }
        int savedPos = 0;
        // Se recorre el nuevo array añadiendo los objetos ordenados
        for (int i = 0; i < sortObjects.length; i++) {
            // En cada vuelta se recorre el array recibido buscando el id más bajo
            for (int j = 0; j < objects.length; j++) {
                if (objects[j] != null && objects[j].getId() < objToSave.getId()) {
                    objToSave = new Objeto(objects[j]);
                    savedPos = j;
                }
            }
            // Una vez conseguida la id más baja, se guarda en la posicion actual
            sortObjects[i] = new Objeto(objToSave);
            // Se elimina el objeto del array recibido para que no se repita
            objects[savedPos] = null;
            // Se aumenta la id del objeto guardado para nunca tener la id más baja
            objToSave.setId(999);

        }

        // Devuelve el nuevo array con los objetos ordenados por id
        return sortObjects;
    }


    // ----- MÉTODOS PARA OBJETOS TIPO EQUIPO -----

    // Método que devuelve un equipo recibiendo su id por parámetros
    public Equipo getEquip(int id) {
        // Crea un array de Equipos partiendo del json
        String jsonStr = getJSON(fileEquip);
        Gson gson = new Gson();
        // Se recoge el array de equipos usando el json
        Equipo[] equips = gson.fromJson(jsonStr,Equipo[].class);

        // Se recorre el array de equipos, si el id coincide lo devuelve
        for (Equipo e : equips) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    // Método que devuelve todos los Equipos
    public Equipo[] getEquips(){
        // Crea un array de Equipos partiendo del json
        String jsonStr = getJSON(fileEquip);
        Gson gson = new Gson();
        // Devuelve el array creado a partir del json
        return gson.fromJson(jsonStr,Equipo[].class);
    }

    // Método que añade un equipo nuevo al array de Equipo
    public void addEquip(Equipo e) {
        String jsonStr = getJSON(fileEquip);
        Gson gson = new Gson();
        // Se recoge el array de equipos usando el json
        Equipo[] equips = gson.fromJson(jsonStr,Equipo[].class);

        // Se crea un nuevo array que guardará el nuevo equipo
        Equipo[] newEquips = new Equipo[equips.length +1];
        for (int i = 0; i < equips.length; i++) {
            Equipo eq = new Equipo (equips[i]);
            newEquips[i] = eq;
        }
        // Se crea el nuevo equipo a añadir partiendo del equipo recibido
        Equipo newEquip = new Equipo(e);

        // Se recorren los equipos comprobando que las id concuerdan en orden, la última posicion siempre es null
        boolean exist = false;
        for (int i = 0; i < newEquips.length-1; i++) {
            // Si una id no concuerda en orden, es que falta un equipo
            if (newEquips[i].getId() != i) {
                // Se le añade la id faltante al nuevo equipo
                newEquip.setId(i);
                i = newEquips.length;
                exist = true;
            }
        }
        // Si las id concuerdan, entonces se le asigna la última id
        if (!exist) {
            newEquip.setId(newEquips.length-1);
        }
        // Se añade el equipo con la id asignada al array de equipos
        newEquips[equips.length] = newEquip;

        // Se ordena el array en caso de fallo de ids
        newEquips = sortEquips(newEquips);

        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(newEquips);
        saveJsonToFile(fileEquip,prettyJson);
    }

    public void updateEquip (Equipo e){
        String jsonStr = getJSON(fileEquip);
        Gson gson = new Gson();
        // Se recoge el array de equipos usando el json
        Equipo[] equips = gson.fromJson(jsonStr,Equipo[].class);
        // Se recorre el array, cuando el equipo coincida con el recibido, se actualiza en el array
        for (int i = 0; i < equips.length; i++) {
            if (equips[i].getId() == e.getId()){
                Equipo eq = new Equipo (e);
                equips[i] = eq;
                i = equips.length;
            }
        }
        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(equips);
        saveJsonToFile(fileEquip,prettyJson);
    }

    // Método que borra un equipo de la lista de equipos, recibe un id por parámetro
    public void deleteEquip(int id) {
        // Crea un array de Equipo partiendo del json
        String jsonStr = getJSON(fileEquip);
        Gson gson = new Gson();
        // Se recoge el array de equipos usando el json
        Equipo[] equips = gson.fromJson(jsonStr,Equipo[].class);
        boolean deleted = false;
        for (int i = 0; i < equips.length; i++) {
            if (equips[i].getId() == id) {
                deleted = true;
                equips[i] = null;
                i = equips.length;
            }
        }
        if (deleted) {
            // Se crea el nuevo array sin el equipo a borrar
            Equipo[] newEquips = new Equipo[equips.length - 1];
            // Se recorre el nuevo array, si el equipo no es null, se guarda en el nuevo array
            int size = 0;
            for (Equipo equip : equips) {
                if (equip != null) {
                    Equipo eq = new Equipo(equip);
                    newEquips[size] = eq;
                    size++;
                }
            }

            // Se ordena el array en caso de fallo de ids
            newEquips = sortEquips(newEquips);

            // No tocar, código encargado de preparar el String json y llamar al guardado
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(newEquips);
            saveJsonToFile(fileEquip,prettyJson);
        }
    }

    // Método que recibe un array de Equipos y los ordena por id
    public Equipo[] sortEquips(Equipo[] equips){
        Equipo[] sortEquips = new Equipo[equips.length];
        int loops = sortEquips.length;
        // Se guarda un equipo para empezar a ordenar
        Equipo equipToSave = new Equipo();
        for (int i = 0; i < equips.length; i++) {
            if (equips[i] != null) {
                equipToSave = new Equipo(equips[i]);
                i = equips.length;
            }
        }
        int savedPos = 0;
        // Se recorre el nuevo array añadiendo los equipos ordenados
        for (int i = 0; i < sortEquips.length; i++) {
            // En cada vuelta se recorre el array recibido buscando el id más bajo
            for (int j = 0; j < equips.length; j++) {
                if (equips[j] != null && equips[j].getId() < equipToSave.getId()) {
                    equipToSave = new Equipo(equips[j]);
                    savedPos = j;
                }
            }
            // Una vez conseguida la id más baja, se guarda en la posicion actual
            sortEquips[i] = new Equipo(equipToSave);
            // Se elimina el equipo del array recibido para que no se repita
            equips[savedPos] = null;
            // Se aumenta la id del equipo guardado para nunca tener la id más baja
            equipToSave.setId(999);

        }

        // Devuelve el nuevo array con los equipos ordenados por id
        return sortEquips;
    }

    // ----- MÉTODOS PARA OBJETOS TIPO CONSUMIBLE -----

    // Método que devuelve un consumible recibiendo su id por parámetros
    public Consumibles getCons(int id) {
        // Crea un array de Consumibles partiendo del json
        String jsonStr = getJSON(fileCons);
        Gson gson = new Gson();
        // Se recoge el array de consumibles usando el json
        Consumibles[] consumibles = gson.fromJson(jsonStr,Consumibles[].class);

        // Se recorre el array de consumibles, si el id coincide lo devuelve
        for (Consumibles c : consumibles) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // Método que devuelve todos los Consumibles
    public Consumibles[] getAllCons(){
        // Crea un array de Consumibles partiendo del json
        String jsonStr = getJSON(fileCons);
        Gson gson = new Gson();
        // Devuelve el array creado a partir del json
        return gson.fromJson(jsonStr,Consumibles[].class);
    }

    // Método que añade un conumible nuevo al array de Consumibles
    public void addCons(Consumibles c) {
        String jsonStr = getJSON(fileCons);
        Gson gson = new Gson();
        // Se recoge el array de consumibles usando el json
        Consumibles[] consumibles = gson.fromJson(jsonStr,Consumibles[].class);

        // Se crea un nuevo array que guardará el nuevo consumible
        Consumibles[] newConsumibles = new Consumibles[consumibles.length +1];
        for (int i = 0; i < consumibles.length; i++) {
            Consumibles cc = new Consumibles (consumibles[i]);
            newConsumibles[i] = cc;
        }
        // Se crea el nuevo consumible a añadir partiendo del consumible recibido
        Consumibles newCons = new Consumibles(c);

        // Se recorren los consumibles comprobando que las id concuerdan en orden, la última posicion siempre es null
        boolean exist = false;
        for (int i = 0; i < newConsumibles.length-1; i++) {
            // Si una id no concuerda en orden, es que falta un consumible
            if (newConsumibles[i].getId() != i) {
                // Se le añade la id faltante al nuevo consumible
                newCons.setId(i);
                i = newConsumibles.length;
                exist = true;
            }
        }
        // Si las id concuerdan, entonces se le asigna la última id
        if (!exist) {
            newCons.setId(newConsumibles.length-1);
        }
        // Se añade el consumible con la id asignada al array de consumibles
        newConsumibles[consumibles.length] = newCons;

        // Se ordena el array en caso de fallo de ids
        newConsumibles = sortCons(newConsumibles);

        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(newConsumibles);
        saveJsonToFile(fileCons,prettyJson);
    }

    public void updateCons (Consumibles c){
        String jsonStr = getJSON(fileCons);
        Gson gson = new Gson();
        // Se recoge el array de consumibles usando el json
        Consumibles[] consumibles = gson.fromJson(jsonStr,Consumibles[].class);
        // Se recorre el array, cuando el consumible coincida con el recibido, se actualiza en el array
        for (int i = 0; i < consumibles.length; i++) {
            if (consumibles[i].getId() == c.getId()){
                Consumibles cc = new Consumibles (c);
                consumibles[i] = cc;
                i = consumibles.length;
            }
        }
        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(consumibles);
        saveJsonToFile(fileCons,prettyJson);
    }

    // Método que borra un consumible de la lista de consumibles, recibe un id por parámetro
    public void deleteCons(int id) {
        // Crea un array de Consumibles partiendo del json
        String jsonStr = getJSON(fileCons);
        Gson gson = new Gson();
        // Se recoge el array de equipos usando el json
        Consumibles[] consumibles = gson.fromJson(jsonStr,Consumibles[].class);
        boolean deleted = false;
        for (int i = 0; i < consumibles.length; i++) {
            if (consumibles[i].getId() == id) {
                deleted = true;
                consumibles[i] = null;
                i = consumibles.length;
            }
        }
        if (deleted) {
            // Se crea el nuevo array sin el consumible a borrar
            Consumibles[] newCons = new Consumibles[consumibles.length - 1];
            // Se recorre el nuevo array, si el consumible no es null, se guarda en el nuevo array
            int size = 0;
            for (Consumibles consumible : consumibles) {
                if (consumible != null) {
                    Consumibles c = new Consumibles(consumible);
                    newCons[size] = c;
                    size++;
                }
            }

            // Se ordena el array en caso de fallo de ids
            newCons = sortCons(newCons);

            // No tocar, código encargado de preparar el String json y llamar al guardado
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(newCons);
            saveJsonToFile(fileCons,prettyJson);
        }
    }

    // Método que recibe un array de Consumibles y los ordena por id
    public Consumibles[] sortCons(Consumibles[] consumibles){
        Consumibles[] sortCons = new Consumibles[consumibles.length];
        int loops = sortCons.length;
        // Se guarda un consumible para empezar a ordenar
        Consumibles constToSave = new Consumibles();
        for (int i = 0; i < consumibles.length; i++) {
            if (consumibles[i] != null) {
                constToSave = new Consumibles(consumibles[i]);
                i = consumibles.length;
            }
        }
        int consSavedPos = 0;
        // Se recorre el nuevo array añadiendo los consumibles ordenados
        for (int i = 0; i < sortCons.length; i++) {
            // En cada vuelta se recorre el array recibido buscando el id más bajo
            for (int j = 0; j < consumibles.length; j++) {
                if (consumibles[j] != null && consumibles[j].getId() < constToSave.getId()) {
                    constToSave = new Consumibles(consumibles[j]);
                    consSavedPos = j;
                }
            }
            // Una vez conseguida la id más baja, se guarda en la posicion actual
            sortCons[i] = new Consumibles(constToSave);
            // Se elimina el consumible del array recibido para que no se repita
            consumibles[consSavedPos] = null;
            // Se aumenta la id del consumible guardado para nunca tener la id más baja
            constToSave.setId(999);

        }

        // Devuelve el nuevo array con los consumibles ordenados por id
        return sortCons;
    }

    // ----- MÉTODO QUE DEVUELVE TODOS LOS OBJETOS EN UN ARRAY DE OBJETOS USANDO POLIMORFISMO -----
    // ----- ¡¡¡¡¡¡¡ATENCIÓN!!!!!!!!
    // ----- ESTE MÉTODO ES SOLO USADO PARA MOSTRAR EN LISTA, AL QUERER VISUALIZAR EL OBJETO SE OBTENDRA
    // ----- SU ID Y TIPO DE ESTA LISTA Y SE UTILIZARÁ SU MÉTODO CONCRETO POR LA COMPLEJIDAD

    public Objeto[] getAllObjects(){
        // Primero se obtiene el array de los 3 tipos de objetos
        Objeto[] objects = getObjects();
        Equipo[] equips = getEquips();
        Consumibles[] consumibles = getAllCons();
        // Se suma el tamaño de los 3 arrays y se crea el array global que guardará todos los objetos
        int size = objects.length + equips.length + consumibles.length;
        Objeto[] allObjects = new Objeto[size];

        // Se recorre el array de objetos añadiendolos al array global
        int length = 0;
        for (int i = 0; i < objects.length; i++) {
            allObjects[length] = objects[i];
            length++;
        }
        for (int i = 0; i < equips.length; i++) {
            allObjects[length] = equips[i];
            length++;
        }
        for (int i = 0; i < consumibles.length; i++) {
            allObjects[length] = consumibles[i];
            length++;
        }

        // Una vez todos los objetos han sido añadidos ordenados por su id, se devuelve el array para su muestra en la lista
        return allObjects;
    }


    // ----- MÉTODOS PARA OBJETO HABILIDADES

    // Método que devuelve una habilidad recibiendo su id por parámetros
    public Habilidades getSkill(int id) {
        // Crea un array de Habilidades partiendo del json
        String jsonStr = getJSON(fileSkill);
        Gson gson = new Gson();
        // Se recoge el array de habilidades usando el json
        Habilidades[] skills = gson.fromJson(jsonStr,Habilidades[].class);

        // Se recorre el array de habilidades, si el id coincide lo devuelve
        for (Habilidades s : skills) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    // Método que devuelve todas las Habilidades
    public Habilidades[] getAllSkills(){
        // Crea un array de Habilidades partiendo del json
        String jsonStr = getJSON(fileSkill);
        Gson gson = new Gson();
        // Devuelve el array creado a partir del json
        return gson.fromJson(jsonStr,Habilidades[].class);
    }

    // Método que añade una skill nueva al array de Habilidades
    public void addSkill(Habilidades s) {

        // Se recoge el array de habilidades usando el json
        Habilidades[] skills = getAllSkills();

        // Se crea un nuevo array que guardará la nueva skill
        Habilidades[] newSkills = new Habilidades[skills.length +1];
        for (int i = 0; i < skills.length; i++) {
            Habilidades ss = new Habilidades (skills[i]);
            newSkills[i] = ss;
        }
        // Se crea el nuevo consumible a añadir partiendo del consumible recibido
        Habilidades newSkill = new Habilidades(s);

        // Se recorren las habilidades comprobando que las id concuerdan en orden, la última posicion siempre es null
        boolean exist = false;
        for (int i = 0; i < newSkills.length-1; i++) {
            // Si una id no concuerda en orden, es que falta una skill
            if (newSkills[i].getId() != i) {
                // Se le añade la id faltante a la nueva skill
                newSkill.setId(i);
                i = newSkills.length;
                exist = true;
            }
        }
        // Si las id concuerdan, entonces se le asigna la última id
        if (!exist) {
            newSkill.setId(newSkills.length-1);
        }
        // Se añade la habilidad con la id asignada al array de habilidades
        newSkills[skills.length] = newSkill;

        // Se ordena el array en caso de fallo de ids
        newSkills = sortSkills(newSkills);

        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(newSkills);
        saveJsonToFile(fileSkill,prettyJson);
    }

    public void updateSkill (Habilidades s){

        // Se recoge el array de habilidades usando el json
        Habilidades[] skills = getAllSkills();
        // Se recorre el array, cuando la habilidad coincida con el recibido, se actualiza en el array
        for (int i = 0; i < skills.length; i++) {
            if (skills[i].getId() == s.getId()){
                Habilidades ss = new Habilidades (s);
                skills[i] = ss;
                i = skills.length;
            }
        }
        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(skills);
        saveJsonToFile(fileSkill,prettyJson);
    }

    // Método que borra una habilidad de la lista de habilidades, recibe un id por parámetro
    public void deleteSkill(int id) {

        // Se recoge el array de equipos usando el json
        Habilidades[] skills = getAllSkills();
        boolean deleted = false;
        for (int i = 0; i < skills.length; i++) {
            if (skills[i].getId() == id) {
                deleted = true;
                skills[i] = null;
                i = skills.length;
            }
        }
        if (deleted) {
            // Se crea el nuevo array sin la habilidad a borrar
            Habilidades[] newSkills = new Habilidades[skills.length - 1];
            // Se recorre el nuevo array, si la habilidad no es null, se guarda en el nuevo array
            int size = 0;
            for (int i = 0; i < skills.length; i++) {
                if (skills[i] != null) {
                    Habilidades s = new Habilidades(skills[i]);
                    newSkills[size] = s;
                    size++;
                }
            }

            // Se ordena el array en caso de fallo de ids
            newSkills = sortSkills(newSkills);

            // No tocar, código encargado de preparar el String json y llamar al guardado
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(newSkills);
            saveJsonToFile(fileSkill,prettyJson);
        }
    }

    // Método que recibe un array de Habilidades y los ordena por id
    public Habilidades[] sortSkills(Habilidades[] skills){
        Habilidades[] sortSkills = new Habilidades[skills.length];
        int loops = sortSkills.length;
        // Se guarda una habilidad para empezar a ordenar
        Habilidades skillToSave = new Habilidades();
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] != null) {
                skillToSave = new Habilidades(skills[i]);
                i = skills.length;
            }
        }
        int skillSavedPos = 0;
        // Se recorre el nuevo array añadiendo las skills ordenadas
        for (int i = 0; i < sortSkills.length; i++) {
            // En cada vuelta se recorre el array recibido buscando el id más bajo
            for (int j = 0; j < skills.length; j++) {
                if (skills[j] != null && skills[j].getId() < skillToSave.getId()) {
                    skillToSave = new Habilidades(skills[j]);
                    skillSavedPos = j;
                }
            }
            // Una vez conseguida la id más baja, se guarda en la posicion actual
            sortSkills[i] = new Habilidades(skillToSave);
            // Se elimina la habilidad del array recibido para que no se repita
            skills[skillSavedPos] = null;
            // Se aumenta la id de la skill guardada para nunca tener la id más baja
            skillToSave.setId(999);

        }

        // Devuelve el nuevo array con las habilidades ordenados por id
        return sortSkills;
    }

    // ----- MÉTODOS PARA OBJETO EVENTO -----

    // Método que devuelve un evento recibiendo su id por parámetros
    public Evento getEvent(int id) {
        // Crea un array de Eventos partiendo del json
        String jsonStr = getJSON(fileEvent);
        Gson gson = new Gson();
        // Se recoge el array de eventos usando el json
        Evento[] events = gson.fromJson(jsonStr,Evento[].class);

        // Se recorre el array de eventos, si el id coincide lo devuelve
        for (Evento e : events) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    // Método que devuelve todos los Eventos
    public Evento[] getEvents(){
        // Crea un array de Eventos partiendo del json
        String jsonStr = getJSON(fileEvent);
        Gson gson = new Gson();
        // Devuelve el array creado a partir del json
        return gson.fromJson(jsonStr,Evento[].class);
    }

    // Método que añade un evento nuevo al array de Eventos
    public void addEvent(Evento e) {

        // Se recoge el array de eventos usando el json
        Evento[] events = getEvents();

        // Se crea un nuevo array que guardará la nueva skill
        Evento[] newEvents = new Evento[events.length +1];
        for (int i = 0; i < events.length; i++) {
            Evento ee = new Evento (events[i]);
            newEvents[i] = ee;
        }
        // Se crea el nuevo evento a añadir partiendo del evento recibido
        Evento newEvent = new Evento(e);

        // Se recorren los eventos comprobando que las id concuerdan en orden, la última posicion siempre es null
        boolean exist = false;
        for (int i = 0; i < newEvents.length-1; i++) {
            // Si una id no concuerda en orden, es que falta un evento
            if (newEvents[i].getId() != i) {
                // Se le añade la id faltante al nuevo evento
                newEvent.setId(i);
                i = newEvents.length;
                exist = true;
            }
        }
        // Si las id concuerdan, entonces se le asigna la última id
        if (!exist) {
            newEvent.setId(newEvents.length-1);
        }
        // Se añade el evento con la id asignada al array de eventos
        newEvents[events.length] = newEvent;

        // Se ordena el array en caso de fallo de ids
        newEvents = sortEvents(newEvents);

        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(newEvents);
        saveJsonToFile(fileEvent,prettyJson);
    }

    public void updateEvent (Evento e){

        // Se recoge el array de eventos usando el json
        Evento[] events = getEvents();
        // Se recorre el array, cuando el evento coincida con el recibido, se actualiza en el array
        for (int i = 0; i < events.length; i++) {
            if (events[i].getId() == e.getId()){
                Evento ee = new Evento (e);
                events[i] = ee;
                i = events.length;
            }
        }
        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(events);
        saveJsonToFile(fileEvent,prettyJson);
    }

    // Método que borra un evento de la lista de eventos, recibe un id por parámetro
    public void deleteEvent(int id) {

        // Se recoge el array de eventos usando el json
        Evento[] events = getEvents();
        boolean deleted = false;
        for (int i = 0; i < events.length; i++) {
            if (events[i].getId() == id) {
                deleted = true;
                events[i] = null;
                i = events.length;
            }
        }
        if (deleted) {
            // Se crea el nuevo array sin el evento a borrar
            Evento[] newEvents = new Evento[events.length - 1];
            // Se recorre el nuevo array, si el evento no es null, se guarda en el nuevo array
            int size = 0;
            for (Evento event : events) {
                if (event != null) {
                    Evento e = new Evento(event);
                    newEvents[size] = e;
                    size++;
                }
            }

            // Se ordena el array en caso de fallo de ids
            newEvents = sortEvents(newEvents);

            // No tocar, código encargado de preparar el String json y llamar al guardado
            Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = prettyGson.toJson(newEvents);
            saveJsonToFile(fileEvent,prettyJson);
        }
    }

    // Método que recibe un array de Eventos y los ordena por id
    public Evento[] sortEvents(Evento[] events){
        Evento[] sortEvents = new Evento[events.length];
        int loops = sortEvents.length;
        // Se recorre el nuevo array, añadiendo en las posiciones los eventos con id ordenada
        for (int i = 0; i < loops; i++) {
            // Si el evento tiene la misma id que la posicion actual
            if (events[i].getId() == i){
                // Se añade el evento a la posicion
                Evento e = new Evento(events[i]);
                sortEvents[i] = e;
            } else {
                // Si no tiene la misma id, se buscará el evento que tenga la misma id que la posicion actual
                for (int j = 0; j < loops; j++) {
                    // Si el evento actual tiene la misma id que la posicion actual
                    if (events[j].getId() == i){
                        Evento e = new Evento(events[j]);
                        sortEvents[i] = e;
                        j = loops;
                    }
                }
            }
        }
        // Devuelve el nuevo array con los eventos ordenados por id
        return sortEvents;
    }

}
