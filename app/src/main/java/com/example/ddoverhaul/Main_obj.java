package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class Main_obj extends BaseActivity {

    private Spinner SpinnerTipo, SpinnerEquipoPos;
    private String opcionSeleccionada;
    private View EquipoLayout,ConsumibleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto);

        // Referencia al Spinner
        SpinnerTipo = findViewById(R.id.SpinnerTipo);
        SpinnerEquipoPos = findViewById(R.id.SpinnerPosicion);
        EquipoLayout = findViewById(R.id.equipoLayout);
        ConsumibleLayout = findViewById(R.id.consumibleLayout);

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"-", "Equipo", "Consumible"});
        SpinnerTipo.setAdapter(adapterTipo);
        ArrayAdapter<String> adapterEquipoPos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Cabeza", "Pecho", "Manos", "Piernas", "Pies", "Arma Principal", "Arma Secundaria"});
        SpinnerEquipoPos.setAdapter(adapterEquipoPos);

        // Aquí configuras el escuchador de elementos seleccionados para el Spinner
        SpinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opcionSeleccionada = parent.getItemAtPosition(position).toString();
                ConsumibleLayout.setVisibility(View.INVISIBLE);
                EquipoLayout.setVisibility(View.INVISIBLE);
                if(opcionSeleccionada.equals("Equipo")){
                    EquipoLayout.setVisibility(View.VISIBLE);
                }else if(opcionSeleccionada.equals("Consumible")){
                    ConsumibleLayout.setVisibility(View.VISIBLE);
                }else{}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Aquí puedes hacer algo si no se selecciona nada
            }
        });
    }
}


