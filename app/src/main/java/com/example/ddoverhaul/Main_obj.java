package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;


public class Main_obj extends BaseActivity {
    private static final int GALLERY_REQUEST_CODE = 1;
    private Spinner SpinnerTipo, SpinnerEquipoPos;
    private String opcionSeleccionada;
    private View EquipoLayout,ConsumibleLayout;
    private ImageView ImagenObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto);

        // Referencia al Spinner
        SpinnerTipo = findViewById(R.id.SpinnerTipo);
        SpinnerEquipoPos = findViewById(R.id.SpinnerPosicion);
        EquipoLayout = findViewById(R.id.equipoLayout);
        ConsumibleLayout = findViewById(R.id.consumibleLayout);
        ImagenObj = findViewById(R.id.ImageObj);

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"-", "Equipo", "Consumible"});
        SpinnerTipo.setAdapter(adapterTipo);
        ArrayAdapter<String> adapterEquipoPos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Cabeza", "Pecho", "Manos", "Piernas", "Pies", "Arma Principal", "Arma Secundaria"});
        SpinnerEquipoPos.setAdapter(adapterEquipoPos);

        ImagenObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });


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
                //Si no pone NADA
            }
        });
    }
    @Override //CUANDO SELECCIONE UNA IMAGEN DE LA GALERIA
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();

            ImagenObj.setImageURI(imageUri);
        }
    }
}


