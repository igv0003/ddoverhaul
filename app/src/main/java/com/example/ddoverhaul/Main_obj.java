package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class Main_obj extends BaseActivity {

    private Spinner mySpinner;
    private String opcionSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto);

        // Referencia al Spinner
        mySpinner = findViewById(R.id.SpinnerTipo);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"-", "Equipo", "Consumible"});
        mySpinner.setAdapter(adapter);

        // Aquí configuras el escuchador de elementos seleccionados para el Spinner
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opcionSeleccionada = parent.getItemAtPosition(position).toString();
                // Aquí puedes hacer algo con la opción seleccionada
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aquí puedes hacer algo si no se selecciona nada
            }
        });
    }
}


