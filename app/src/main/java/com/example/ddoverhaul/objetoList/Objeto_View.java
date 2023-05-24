package com.example.ddoverhaul.objetoList;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.R;

public class Objeto_View extends BaseActivity {
    private TextView editName,editDescription,editDamage, editArmor, editValor, editCuantiti, editOperation;
    private Spinner SpinnerTipo, SpinnerEquipoPos, SpinnerValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto_view);

        editName = findViewById(R.id.caja_objeto);
        SpinnerEquipoPos = findViewById(R.id.SpinnerPosicion);
        editDamage = findViewById(R.id.caja_danio);
        editArmor = findViewById(R.id.caja_arm);
        SpinnerValor = findViewById(R.id.Spinnervalor);
        editDescription = findViewById(R.id.caja_descripcion);
        editCuantiti = findViewById(R.id.caja_cantidad);
        editOperation = findViewById(R.id.caja_operacion);
        SpinnerEquipoPos = findViewById(R.id.SpinnerPosicion);

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"-", "Equipo", "Consumible"});
        SpinnerTipo.setAdapter(adapterTipo);
        ArrayAdapter<String> adapterEquipoPos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Cabeza", "Pecho", "Manos", "Piernas", "Pies", "Arma Principal", "Arma Secundaria"});
        SpinnerEquipoPos.setAdapter(adapterEquipoPos);
        ArrayAdapter<String> adapterValor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Vida", "Mana", "Fuerza", "Destreza", "Constitucion", "Inteligencia","Sabiduria","Carisma","Velocidad"});
        SpinnerValor.setAdapter(adapterValor);


    }
}
