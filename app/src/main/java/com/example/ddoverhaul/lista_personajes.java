package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class lista_personajes  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personajes);

        Button buttonOpenPagina1 = findViewById(R.id.bv);
        buttonOpenPagina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gomenu();
            }
        });
    }

    public void gomenu() {
        Intent intent = new Intent(lista_personajes.this, Menu_principal.class);
        startActivity(intent);
    }

}

