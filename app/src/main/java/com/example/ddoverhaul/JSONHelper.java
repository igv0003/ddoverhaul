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
import com.google.gson.JsonObject;

public class JSONHelper {
    Context context;
    Personaje p = new Personaje();
    String fileChar = "personaje.json";
    String fileObj = "objeto.json";
    String fileEvent = "evento.json";
    String fileSkill = "habilidad.json";

    public JSONHelper (Context c){
        context = c;
        p.setNivel(15);
        p.setNombre("dummy2");
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
            for (int i = 0; i < characters.length; i++) {
                if (characters[i] != null) {
                    Personaje p = new Personaje(characters[i]);
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
        Personaje[] sortChars = new Personaje[characters.length];
        int loops = sortChars.length;

        // Se recorre el nuevo array, añadiendo en las posiciones los personajes con id ordenada
        for (int i = 0; i < loops; i++) {
            // Si el personaje tiene la misma id que la posicion actual
            if (characters[i].getId() == i){
                // Se añade el personaje a la posicion
                Personaje p = new Personaje (characters[i]);
                sortChars[i] = p;
            } else {
                // Si no tiene la misma id, se buscará al personaje que tenga la misma id que la posicion actual
                for (int j = 0; j < loops; j++) {
                    // Si el personaje actual tiene la misma id que la posicion actual
                    if (characters[j].getId() == i){
                        Personaje p = new Personaje(characters[j]);
                        sortChars[i] = p;
                        j = loops;
                    }
                }
            }
        }

        // Devuelve el nuevo array con los personajes ordenados por id
        return sortChars;
    }




}
