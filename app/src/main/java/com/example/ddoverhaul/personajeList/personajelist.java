package com.example.ddoverhaul.personajeList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Login;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;

public class personajelist extends BaseActivity {
    // Variables necesarias para mostrar la lista
    private RecyclerView recyclerView;
    private PersonajeAdapter adapter;
    private JSONHelper helper;
    private Personaje[] characters;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personajelist);

        helper = new JSONHelper(getBaseContext());
        // Se prepara el recyclerView para mostrar la lista
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacingItemDecoration(35));

        // Se obtiene el array a mostrar en el recyclerView
        characters = helper.getChars();
        // Se crea el PersonajeAdapter con el array obtenido
        adapter = new PersonajeAdapter(characters);
        // Se vincula el recyclerView con el adaptador
        recyclerView.setAdapter(adapter);

        // Se añade el evento OnClick, para poder ver un item en concreto. Se le pasa la id para el siguiente Activity
        adapter.setOnClickListener(new PersonajeAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String id) {
                Intent intent = new Intent(personajelist.this, personaleviewprueba.class);
                intent.putExtra("personaje", id);
                startActivity(intent);
            }
        });


    }



}