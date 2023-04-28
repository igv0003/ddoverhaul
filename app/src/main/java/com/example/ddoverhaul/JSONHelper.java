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
    String rootChar = "characters";
    String fileObj = "personaje.json";
    String fileEvent = "personaje.json";
    String fileSkill = "personaje.json";

    public JSONHelper (Context c){
        context = c;
        p.setNivel(30);
        p.setNombre("dummy");
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


    public Personaje getChar(int id){
        return p;
    }


    // Método que añade un personaje nuevo al array de Personajes, recibe el personaje a guardar
    public void addCharacter (){
        Gson gson = new Gson();
        // Se recoge el array de personajes usando el json
        Personaje[] characters = gson.fromJson(getJSON(fileChar),Personaje[].class);
        // Se crea un nuevo array que guardará el nuevo personaje
        Personaje[] newCharacters = new Personaje[characters.length +1];
        for (int i = 0; i < characters.length; i++) {
            newCharacters[i] = characters[i];
        }
        // Se crea el nuevo personaje a añadir partiendo del personaje recibido
        Personaje newChar = p;

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
            newChar.setId(newCharacters.length);
        }
        // Se añade el personaje con la id asignada al array de personajes
        newCharacters[characters.length] = newChar;

        // Se ordena el array en caso de fallo de ids
        //newCharacters = sortCharacters(newCharacters);

        // No tocar, código encargado de preparar el String json y llamar al guardado
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = prettyGson.toJson(newCharacters);
        saveJsonToFile(fileChar,prettyJson);
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
                sortChars[i] = characters[i];
            } else {
                // Si no tiene la misma id, se buscará al personaje que tenga la misma id que la posicion actual
                for (int j = 0; j < loops; j++) {
                    // Si el personaje actual tiene la misma id que la posicion actual
                    if (characters[j].getId() == i){
                        sortChars[i] = characters[j];
                        j = loops;
                    }
                }
            }
        }

        // Devuelve el nuevo array con los personajes ordenados por id
        return sortChars;
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

}
