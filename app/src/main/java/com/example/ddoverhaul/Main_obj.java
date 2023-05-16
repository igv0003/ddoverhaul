package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class Main_obj extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto);
    }
    private void mostrarOpcionesDesplegable(){
        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"-", "Equipo", "Consumible"});
        spinner.setAdapter(adapter);

        new AlertDialog.Builder(this).setTitle("Tipos").setView(spinner).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String opcionSeleccionada = spinner.getSelectedItem().toString();
            }
        }).setNegativeButton("Cancelar",null).show();
    }

}
