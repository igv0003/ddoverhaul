package com.example.ddoverhaul.habilidadList;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.R;

public class CreateSkill extends AppCompatActivity {

    // Variables para la creacion de Habilidad
    private Habilidades skill;
    private JSONHelper helper;
    // Variables EditText
    private EditText editName;
    private EditText editCost;
    private EditText editDmg;
    private EditText editStatus;
    private EditText editPerc;
    private EditText editDescription;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_skill);

        // Inicializando variables
        editName = findViewById(R.id.edit_name_skill);
        editCost = findViewById(R.id.edit_cost_skill);
        editDmg = findViewById(R.id.edit_dmg_skill);
        editStatus = findViewById(R.id.edit_status_skill);
        editPerc = findViewById(R.id.edit_perc_skill);
        editDescription = findViewById(R.id.edit_comment_skill);
        helper = new JSONHelper(getBaseContext());

        // Se comprueba si no se está editando una habilidad
        int skillId = getIntent().getIntExtra("habilidad",-1);
        // Si el extra existe, es que es una habilidad a editar y se modifican las variables
        if (skillId != -1) {
            skill = helper.getSkill(skillId);
            // Al existir, se le añaden los valores que tiene para su edición
            editName.setText(skill.getNombre());
            editCost.setText(skill.getCoste()+"");
            editDmg.setText(skill.getDanio()+"");
            editStatus.setText(skill.getProblema_estado());
            editPerc.setText(skill.getPorcentaje()+"");
            editDescription.setText(skill.getDescripcion());
        } else {
            skill = new Habilidades();
            skill.setId(-1);
        }

    }

    // Método que recoge los valores introducidos y guarda la habilidad en el json
    public void Save(View v) {
        // Se guardan todos los valores dentro de la skill para guardarla
        String name = editName.getText().toString();
        String cost = editCost.getText().toString();
        String dmg = editDmg.getText().toString();
        if (name.equals("")) {
            Toast.makeText(getApplicationContext(), "El nombre no puede estar vacío",Toast.LENGTH_SHORT).show();
            return;
        }
        if (cost.equals("")) {
            cost = "0";
        }
        if (dmg.equals("")){
            dmg = "0";
        }
        skill.setNombre(name);
        skill.setCoste(Integer.parseInt(cost));
        skill.setDanio(Integer.parseInt(dmg));
        skill.setDescripcion(editDescription.getText().toString());

        // Si la habilidad no provoca un estado se deja vacio
        if (!editStatus.getText().toString().equals("")) {
            skill.setProblema_estado(editStatus.getText().toString());
            String perc = editPerc.getText().toString();
            if (perc.equals("")) {
                perc = "0";
            }
            skill.setPorcentaje(Integer.parseInt(perc));
        } else {
            skill.setProblema_estado("");
            skill.setPorcentaje(0);
        }

        if (skill.getId() != -1) {
            helper.updateSkill(skill);
            Toast.makeText(getApplicationContext(), "Se actualizó la habilidad",Toast.LENGTH_SHORT).show();
        } else {
            helper.addSkill(skill);
            Toast.makeText(getApplicationContext(), "Se creó la habilidad",Toast.LENGTH_SHORT).show();
        }

        // Una vez se creó la habilidad, se vuelve a la lista de habilidades
        Intent intent = new Intent(CreateSkill.this, habilidadlist.class);
        startActivity(intent);

    }


    public void Cancel(View v){
        Intent intent = new Intent(CreateSkill.this, habilidadlist.class);
        startActivity(intent);
    }

}