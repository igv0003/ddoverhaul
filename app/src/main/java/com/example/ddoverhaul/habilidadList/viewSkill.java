package com.example.ddoverhaul.habilidadList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.R;

public class viewSkill extends BaseActivity {

    // Variables necesarias
    private Habilidades skill;
    // Falta icono
    private JSONHelper helper;
    TextView nameView;
    TextView costView;
    TextView dmgView;
    TextView statusView;
    TextView percView;
    TextView commentView;
    LinearLayout probLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_skill);

        // Se inicializan las variables necesarias
        nameView = findViewById(R.id.name_skill);
        costView = findViewById(R.id.cost_skill);
        dmgView = findViewById(R.id.dmg_skill);
        statusView = findViewById(R.id.status_skill);
        percView = findViewById(R.id.perc_skill);
        commentView = findViewById(R.id.comment_skill);
        probLayout = findViewById(R.id.probstatus_layout);
        helper = new JSONHelper(getBaseContext());

        String skillString = getIntent().getStringExtra("habilidad");
        int skillId = 0;
        try {
            skillId = Integer.parseInt(skillString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        skill = helper.getSkill(skillId);

        // Se empiezan a mostar los valores
        nameView.setText(skill.getNombre());
        costView.setText(skill.getCoste()+"");
        dmgView.setText(skill.getDanio()+"");
        commentView.setText(skill.getDescripcion());

        // Si la habilidad no provoca problema de estado desaparece su layout
        if (!skill.getProblema_estado().equals("")) {
            statusView.setText(skill.getProblema_estado());
            percView.setText(skill.getPorcentaje() + "");
        } else {
            probLayout.setVisibility(View.GONE);
        }


        findViewById(R.id.delete_skill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSkill(skill.getId());
            }
        });


    }

    private void deleteSkill(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(viewSkill.this);
        builder.setTitle("Eliminar habilidad");
        builder.setMessage("¿Está seguro de que desea eliminar la habilidad?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteSkill(skill.getId());
                        Toast.makeText(getApplicationContext(), "Se eliminó la habilidad",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(viewSkill.this, habilidadlist.class);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Se canceló el borrado",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();
    }



}