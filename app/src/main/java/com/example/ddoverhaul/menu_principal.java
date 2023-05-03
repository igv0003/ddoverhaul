package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Button buttonOpen = findViewById(R.id.blista_personajes
        );
        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });
    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.blista_personajes:
                Intent intent = new Intent(menu_principal.this, lista_personajes.class);
                startActivity(intent);

                break;
            case R.id.blista_objetos:
                Intent intent2 = new Intent(menu_principal.this, lista_objetos.class);
                startActivity(intent2);

                break;
            // Agrega más casos según la cantidad de botones que tengas
        }
    }

}