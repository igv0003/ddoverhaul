package com.example.ddoverhaul.habilidadList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Login;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.personajeList.SpacingItemDecoration;

public class habilidadlist extends AppCompatActivity {
    // Variables necesarias para mostrar la lista
    private RecyclerView recyclerView;
    private HabilidadAdapter adapter;
    private JSONHelper helper;
    private Habilidades[] skills;

    @SuppressLint("MissingInflateId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habilidadlist);

        helper = new JSONHelper(getBaseContext());
        // Se prepara el recyclerView para mostrar la lista
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacingItemDecoration(35));

        // Se obtiene el array a mostrar en el recyclerView
        skills = helper.getAllSkills();
        // Se crea el HabilidadAdapter con el array obtenido
        adapter = new HabilidadAdapter(skills);
        // Se vincula el recyclerView con el adaptador
        recyclerView.setAdapter(adapter);

        // Se añade el evento OnClick, para poder ver un item en concreto. Se le pas a id para el siguiente Activity
        adapter.setOnClickListener(new HabilidadAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String id) {
                Intent intent = new Intent(habilidadlist.this, Login.class);
                intent.putExtra("habilidad",id);
            }
        });

    }
}