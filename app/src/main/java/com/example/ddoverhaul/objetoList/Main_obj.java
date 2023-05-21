package com.example.ddoverhaul.objetoList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.Consumibles;
import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.R;


public class Main_obj extends BaseActivity {
    private static final int GALLERY_REQUEST_CODE = 1;
    private Spinner SpinnerTipo, SpinnerEquipoPos;
    private String opcionSeleccionada;
    private View EquipoLayout,ConsumibleLayout;
    private ImageView ImagenObj;
    private EditText editName,editDescription;
    //Tipo Equipo
    private EditText editDamage, editArmor;
    //Tipo Consumible
    private EditText editValor, editCuantiti, editOperation;
    private Button guardarBTN = findViewById(R.id.GuardarObj);
    private Button cancelarBTN = findViewById(R.id.CancelarObj);
    private Objeto obj;
    private Equipo equp;
    private Consumibles cons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto);

        // Referencia al Spinner
        SpinnerTipo = findViewById(R.id.SpinnerTipo);
        EquipoLayout = findViewById(R.id.equipoLayout);
        ConsumibleLayout = findViewById(R.id.consumibleLayout);
        ImagenObj = findViewById(R.id.ImageObj);

        //Referenciar EditText
        editName = findViewById(R.id.caja_objeto);
        SpinnerEquipoPos = findViewById(R.id.SpinnerPosicion);
        editDamage = findViewById(R.id.caja_danio);
        editArmor = findViewById(R.id.caja_arm);
        editValor = findViewById(R.id.caja_valor);
        editDescription = findViewById(R.id.caja_descripcion);
        editCuantiti = findViewById(R.id.caja_cantidad);
        editOperation = findViewById(R.id.caja_operacion);

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

        guardarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Boton cuando se haga clic en el botón "Guardar"
                String nombre = editName.getText().toString();
                String descrip = editDescription.getText().toString();
                String Tipo;
                if (nombre.equals("")) {
                    Toast.makeText(getApplicationContext(), "El nombre no puede estar vacío",Toast.LENGTH_SHORT).show();
                    return;
                }
                int selectedPosition = SpinnerTipo.getSelectedItemPosition();
                int selectedValue = Integer.parseInt(SpinnerTipo.getItemAtPosition(selectedPosition).toString());
                switch (selectedValue){
                    case 0://Posicion primera OBJETO
                        Tipo = "Otro";
                        break;
                    case 1://Posicion segunda EQUIPO
                        Tipo = "Equipo";
                        break;
                    case 2://Posicion tercera CONSUMIBLE
                        Tipo = "Consumible";
                        break;

                }
            }
        });

        cancelarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Boton cuando se haga clic en el botón "Cancelar"
                Intent intent = new Intent(Main_obj.this, lista_objetos.class);
                startActivity(intent);
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


