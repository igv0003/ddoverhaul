package com.example.ddoverhaul.personajeList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;

public class personajelist extends AppCompatActivity {

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
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacingItemDecoration(35));

        characters = helper.getChars();
        adapter = new PersonajeAdapter(characters);
        recyclerView.setAdapter(adapter);


    }
}