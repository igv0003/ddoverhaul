package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class pagina1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina1);

        Button buttonOpenPagina1 = findViewById(R.id.botonv);
        buttonOpenPagina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmain();
            }
        });
    }

    public void openmain() {
        Intent intent = new Intent(pagina1.this, MainActivity.class);
        startActivity(intent);
    }

}
