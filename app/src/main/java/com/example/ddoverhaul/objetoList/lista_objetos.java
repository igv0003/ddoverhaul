package com.example.ddoverhaul.objetoList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Login;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.personajeList.SpacingItemDecoration;

public class lista_objetos extends BaseActivity {
    // Variables necesarias para mostrar la lista
    private RecyclerView recyclerView;
    private ObjetoAdapter adapter;
    private JSONHelper helper;
    private Objeto[] objects;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_objetos);

        helper = new JSONHelper(getBaseContext());
        // Se prepara el recyclerView para mostrar la lista
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacingItemDecoration(35));

        // Se obtiene el array a mostrar en el recyclerView
        objects = helper.getAllObjects();
        // Se crea el ObjetoAdapter con el array obtenido
        adapter = new ObjetoAdapter(objects);
        // Se vincula el recyclerView con el adaptador
        recyclerView.setAdapter(adapter);

        // Se añade el evento OnClick, para poder ver un item en concreto. Se le pasa la id para el siguiente Activity
        adapter.setOnClickListener(new ObjetoAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String id, String type) {
                Intent intent = new Intent(lista_objetos.this, Objeto_View.class);
                intent.putExtra("objeto", id);
                intent.putExtra("type", id);
                startActivity(intent);
            }
        });




    }

}
