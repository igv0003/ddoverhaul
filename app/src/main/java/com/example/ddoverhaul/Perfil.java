package com.example.ddoverhaul;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

    public class Perfil  extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_perfil);

            Button buttonOpenPagina1 = findViewById(R.id.bv);
            buttonOpenPagina1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    gomenu();
                }
            });
        }

        public void gomenu() {
            Intent intent = new Intent(Perfil.this, Menu_principal.class);
            startActivity(intent);
        }

    }

