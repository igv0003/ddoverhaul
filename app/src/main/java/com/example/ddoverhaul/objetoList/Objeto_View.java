package com.example.ddoverhaul.objetoList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.Consumibles;
import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.habilidadList.CreateSkill;
import com.example.ddoverhaul.habilidadList.viewSkill;

public class Objeto_View extends BaseActivity {
    private JSONHelper helper;
    private TextView editName,editDescription,editDamage, editArmor, editValor, editCuantiti, editOperation;
    private Spinner SpinnerTipo, SpinnerEquipoPos, SpinnerValor;
    private View EquipoLayout,ConsumibleLayout;
    private Objeto obj;
    private String opcionSeleccionada;
    private Equipo equip;
    private Consumibles cons;

    private LinearLayout mainObj;
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
        SpinnerTipo = findViewById(R.id.SpinnerTipo);
        EquipoLayout = findViewById(R.id.equipoLayout);
        ConsumibleLayout = findViewById(R.id.consumibleLayout);
        mainObj = findViewById(R.id.activityObj);

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Otro", "Equipo", "Consumible"});
        SpinnerTipo.setAdapter(adapterTipo);
        ArrayAdapter<String> adapterEquipoPos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Cabeza", "Pecho", "Manos", "Piernas", "Pies", "Arma Principal", "Arma Secundaria"});
        SpinnerEquipoPos.setAdapter(adapterEquipoPos);
        ArrayAdapter<String> adapterValor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[]{"Vida", "Mana", "Fuerza", "Destreza", "Constitucion", "Inteligencia","Sabiduria","Carisma","Velocidad"});
        SpinnerValor.setAdapter(adapterValor);
        int indexC = mainObj.indexOfChild(ConsumibleLayout);
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
        switch (type){
            default://OTROS
                if (id != -1) {
                    obj = helper.getObject(id);
                    // Al existir, se le añaden los valores que tiene para su edición
                    editName.setText(obj.getNombre());
                    editDescription.setText(obj.getDescripcion());
                    EquipoLayout.setVisibility(View.INVISIBLE);
                    ConsumibleLayout.setVisibility(View.INVISIBLE);
                    SpinnerTipo.setSelection(0);
                } else {
                    Toast.makeText(getApplicationContext(), "NO SE HA ENCONTRADO",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case "Equipo"://EQUIPO
                if (id != -1) {
                    equip = helper.getEquip(id);
                    // Al existir, se le añaden los valores que tiene para su edición
                    editName.setText(equip.getNombre());
                    editDescription.setText(equip.getDescripcion());
                    editDamage.setText(equip.getDanio()+"");
                    editArmor.setText(equip.getArmadura()+"");
                    SpinnerEquipoPos.setSelection(equip.getPosicion());
                    EquipoLayout.setVisibility(View.VISIBLE);
                    SpinnerTipo.setSelection(2);
                } else {
                    Toast.makeText(getApplicationContext(), "NO SE HA ENCONTRADO",Toast.LENGTH_SHORT).show();
                    finish();
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
                    Toast.makeText(getApplicationContext(), "NO SE HA ENCONTRADO",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
        SpinnerTipo.setEnabled(false);
        SpinnerValor.setEnabled(false);
        SpinnerEquipoPos.setEnabled(false);
        SpinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opcionSeleccionada = parent.getItemAtPosition(position).toString();
                ConsumibleLayout.setVisibility(View.INVISIBLE);
                EquipoLayout.setVisibility(View.INVISIBLE);
                mainObj.removeView(EquipoLayout);
                mainObj.removeView(ConsumibleLayout);
                if (type.equals("Equipo")) {
                    mainObj.addView(EquipoLayout, indexC);
                    EquipoLayout.setVisibility(View.VISIBLE);
                } else if (type.equals("Consumible")) {
                    mainObj.addView(ConsumibleLayout, indexC);
                    ConsumibleLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.CancelarObj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteObj(Integer.parseInt(idString), type);
            }
        });

        findViewById(R.id.GuardarObj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Equipo")){
                    equip.getId();
                }else if(type.equals("Consumible")){
                    cons.getId();
                }else{
                    obj.getId();
                }
                editObj(obj.getId(), type);
            }
        });
    }
    public void editObj(int id, String type) {
        Intent intent = new Intent(Objeto_View.this, Main_obj.class);
        intent.putExtra("objeto", id);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    public void deleteObj(int id, String type) {

    }
}
