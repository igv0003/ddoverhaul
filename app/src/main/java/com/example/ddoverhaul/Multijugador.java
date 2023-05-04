package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Multijugador  extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_multijugador);

            Button buttonOpenPagina1 = findViewById(R.id.bv);
            buttonOpenPagina1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    gomenu();
                }
            });
        }

        public void gomenu() {
            Intent intent = new Intent(com.example.ddoverhaul.Multijugador.this, Menu_principal.class);
            startActivity(intent);
        }

    }

