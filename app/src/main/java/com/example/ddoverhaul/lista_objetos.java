package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class lista_objetos extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_objetos);

        Button buttonOpen = findViewById(R.id.botonv);
        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gomenu();
            }
        });
    }

    public void gomenu() {
        Intent intent = new Intent(lista_objetos.this, menu_principal.class);
        startActivity(intent);
    }
}
