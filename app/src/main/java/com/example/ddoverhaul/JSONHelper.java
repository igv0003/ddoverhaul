package com.example.ddoverhaul;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONHelper {
    Context context;
    Personaje p = new Personaje();

    public JSONHelper (Context c){
        context = c;
        p.setNivel(30);
        p.setId(3);
        p.setNombre("Rio");
    }

    // Método que recibe nombre del json y lo devuelve como objeto
    public JsonObject getJSON (String jsonFile){
        JsonObject jsonObj = null;
        try {
            AssetManager assetManager = context.getAssets();
            //InputStream inputStream = assetManager.open(jsonFile);
            InputStream inputStream = context.openFileInput(jsonFile);
            if (inputStream != null) {
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                String json = new String(buffer, StandardCharsets.UTF_8);
                // Gson es una librería de Google que convierne archivos JSON en Objetos Json de manera sencilla
                Gson gson = new Gson();
                jsonObj = gson.fromJson(json,JsonObject.class);
            }


        } catch (IOException e){
            e.printStackTrace();
        }
        return jsonObj;
    }

    // Método que añade un personaje nuevo al array de Personajes, recibe el objetoJSON preparado
    public void addCharacter (JsonObject jsonObj){

        JsonArray characterArray = jsonObj.getAsJsonArray("characters");
        Personaje newC = p;
        JsonObject newObjectC = new JsonObject();
        newObjectC.addProperty("id",newC.getId());
        newObjectC.addProperty("level",newC.getNivel());
        newObjectC.addProperty("name",newC.getNombre());
        characterArray.add(newObjectC);

        Gson gson = new Gson();
        String newJson = gson.toJson(jsonObj);
        try {
            OutputStream outputStream = context.openFileOutput("personajes.json", Context.MODE_PRIVATE);
            outputStream.write(newJson.getBytes());
            outputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }



}
