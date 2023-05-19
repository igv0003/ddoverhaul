package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ddoverhaul.objetoList.lista_objetos;
import com.example.ddoverhaul.personajeList.personajelist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Multijugador  extends BaseActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_multijugador);

            Button buttonOpenPagina1 = findViewById(R.id.bv);
            buttonOpenPagina1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //gomenu();
                }
            });


        }



    }

