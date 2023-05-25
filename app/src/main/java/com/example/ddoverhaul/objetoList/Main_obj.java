package com.example.ddoverhaul.objetoList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.Consumibles;
import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.R;


public class Main_obj extends BaseActivity {
    private Spinner SpinnerTipo, SpinnerEquipoPos, SpinnerValor;
    private String opcionSeleccionada;
    private View EquipoLayout,ConsumibleLayout;
    private LinearLayout mainObj;
    private ImageView ImagenObj;
    private EditText editName,editDescription;
    private JSONHelper helper;
    //Tipo Equipo
    private EditText editDamage, editArmor;
    //Tipo Consumible
    private EditText editCuantiti, editOperation;
    private Button guardarBTN;
    private Button cancelarBTN;
    private Objeto obj;
    private Equipo equip;
    private Consumibles cons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objeto);

        // Referencia al Spinner
        SpinnerTipo = findViewById(R.id.SpinnerTipo);
        EquipoLayout = findViewById(R.id.equipoLayout);
        ConsumibleLayout = findViewById(R.id.consumibleLayout);
        mainObj = findViewById(R.id.activityObj);
        ImagenObj = findViewById(R.id.ImageObj);

        guardarBTN = findViewById(R.id.GuardarObj);
        cancelarBTN = findViewById(R.id.CancelarObj);
        //Referenciar EditText
        editName = findViewById(R.id.caja_objeto);
        SpinnerEquipoPos = findViewById(R.id.SpinnerPosicion);
        editDamage = findViewById(R.id.caja_danio);
        editArmor = findViewById(R.id.caja_arm);
        SpinnerValor = findViewById(R.id.Spinnervalor);
        editDescription = findViewById(R.id.caja_descripcion);
        editCuantiti = findViewById(R.id.caja_cantidad);
        editOperation = findViewById(R.id.caja_operacion);

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Otro", "Equipo", "Consumible"});
        SpinnerTipo.setAdapter(adapterTipo);
        ArrayAdapter<String> adapterEquipoPos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Cabeza", "Pecho", "Manos", "Piernas", "Pies", "Arma Principal", "Arma Secundaria"});
        SpinnerEquipoPos.setAdapter(adapterEquipoPos);
        ArrayAdapter<String> adapterValor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Vida", "Mana", "Fuerza", "Destreza", "Constitucion", "Inteligencia","Sabiduria","Carisma","Velocidad"});
        SpinnerValor.setAdapter(adapterValor);

        String idString = getIntent().getStringExtra("objeto");
        String type = getIntent().getStringExtra("type");
        int id = -1;
        try{
            id = Integer.parseInt(idString);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        helper = new JSONHelper(getBaseContext());
        if(type != null){
            switch (type){
                case "Equipo"://EQUIPO
                    if (id != -1) {
                        equip = helper.getEquip(id);
                        // Al existir, se le añaden los valores que tiene para su edición
                        editName.setText(equip.getNombre());
                        editDescription.setText(equip.getDescripcion());
                        editDamage.setText(equip.getDanio()+"");
                        editArmor.setText(equip.getArmadura()+"");
                        EquipoLayout.setVisibility(View.VISIBLE);
                        SpinnerTipo.setSelection(2);
                    } else {
                        equip = new Equipo();
                        equip.setId(-1);
                    }
                    break;
                case "Consumible"://CONSUMIBLE
                    if (id != -1) {
                        cons = helper.getCons(id);
                        // Al existir, se le añaden los valores que tiene para su edición
                        editName.setText(cons.getNombre());
                        editDescription.setText(cons.getDescripcion());
                        SpinnerValor.setSelection(cons.getValor());
                        editCuantiti.setText(cons.getCantidad()+"");
                        editOperation.setText(cons.getOperacion()+"");
                        ConsumibleLayout.setVisibility(View.VISIBLE);
                        SpinnerTipo.setSelection(1);
                    } else {
                        equip = new Equipo();
                        equip.setId(-1);
                    }
                    break;
                case "Otro"://OTROS
                    if (id != -1) {
                        obj = helper.getObject(id);
                        // Al existir, se le añaden los valores que tiene para su edición
                        String nm = obj.getNombre();
                        editName.setText(obj.getNombre());
                        editDescription.setText(obj.getDescripcion());
                        EquipoLayout.setVisibility(View.INVISIBLE);
                        ConsumibleLayout.setVisibility(View.INVISIBLE);
                        SpinnerTipo.setSelection(0);
                    } else {
                        obj = new Objeto();
                        obj.setId(-1);
                    }
                default:
                    break;
            }
            SpinnerTipo.setEnabled(false);
        }

        int indexC = mainObj.indexOfChild(ConsumibleLayout);



        // Aquí configuras el escuchador de elementos seleccionados para el Spinner
        SpinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opcionSeleccionada = parent.getItemAtPosition(position).toString();
                ConsumibleLayout.setVisibility(View.INVISIBLE);
                EquipoLayout.setVisibility(View.INVISIBLE);
                mainObj.removeView(EquipoLayout);
                mainObj.removeView(ConsumibleLayout);
                if(opcionSeleccionada.equals("Equipo")){
                    mainObj.addView(EquipoLayout, indexC);
                    EquipoLayout.setVisibility(View.VISIBLE);
                }else if(opcionSeleccionada.equals("Consumible")){
                    mainObj.addView(ConsumibleLayout, indexC);
                    ConsumibleLayout.setVisibility(View.VISIBLE);
                }
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
                String Tipo = "Otro";
                //Equipo
                int danio = 0;
                int arm = 0;
                int posicion = 0;
                //Consumible
                int valor = 0;
                int cantidad = 0;
                char operacion = '+';
                if (nombre.equals("")) {
                    Toast.makeText(getApplicationContext(), "El nombre no puede estar vacío",Toast.LENGTH_SHORT).show();
                    return;
                }
                int selectedPosition = SpinnerTipo.getSelectedItemPosition();
                switch (selectedPosition){
                    case 1://Posicion segunda EQUIPO
                        Tipo = "Equipo";
                        danio = Integer.parseInt(editDamage.getText().toString());
                        arm = Integer.parseInt(editArmor.getText().toString());
                        posicion = SpinnerEquipoPos.getSelectedItemPosition();
                        equip.setNombre(nombre);
                        equip.setDescripcion(descrip);
                        equip.setDanio(danio);
                        equip.setArmadura(arm);
                        equip.setPosicion(posicion);
                        equip.setTipo(Tipo);
                        if (equip.getId() != -1){
                            helper.updateEquip(equip);
                            Toast.makeText(getApplicationContext(), "Se edito el equipo",Toast.LENGTH_SHORT).show();
                        }else{
                            helper.addEquip(equip);
                            Toast.makeText(getApplicationContext(), "Se creó el equipo",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 2://Posicion tercera CONSUMIBLE
                        Tipo = "Consumible";
                        valor = SpinnerValor.getSelectedItemPosition();
                        cantidad = Integer.parseInt(editCuantiti.getText().toString());
                        if (editOperation.getText().toString().length() == 1 && (editOperation.getText().toString().charAt(0) == ('+') || editOperation.getText().toString().charAt(0) == '-' || editOperation.getText().toString().charAt(0) == '*' || editOperation.getText().toString().charAt(0) == '/')){
                            operacion = editOperation.getText().toString().charAt(0);
                        }else if(editOperation.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(), "No puedes dejar vacio operacion",Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            Toast.makeText(getApplicationContext(), "Operacion debe ser un caracter operador",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        cons.setNombre(nombre);
                        cons.setDescripcion(descrip);
                        cons.setValor(valor);//0=Vida;1=Mana...8=Velocidad
                        cons.setCantidad(cantidad);
                        cons.setOperacion(operacion);
                        cons.setTipo(Tipo);
                        if (cons.getId() != -1){
                            helper.updateCons(cons);
                            Toast.makeText(getApplicationContext(), "Se editó el consumble",Toast.LENGTH_SHORT).show();
                        }else{
                            helper.addCons(cons);
                            Toast.makeText(getApplicationContext(), "Se creó el consumible",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default://Posicion segunda OTRO
                        Tipo = "Otro";
                        obj.setNombre(nombre);
                        obj.setDescripcion(descrip);
                        obj.setTipo(Tipo);
                        if (obj.getId() != -1){
                            helper.updateObject(obj);
                            Toast.makeText(getApplicationContext(), "Se editó el objeto",Toast.LENGTH_SHORT).show();
                        }else{
                            helper.addObject(obj);
                            Toast.makeText(getApplicationContext(), "Se creó el objeto",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                Intent intent = new Intent(Main_obj.this, lista_objetos.class);
                startActivity(intent);
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
}


