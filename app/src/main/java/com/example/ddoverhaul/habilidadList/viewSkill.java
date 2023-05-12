package com.example.ddoverhaul.habilidadList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.R;

public class viewSkill extends AppCompatActivity {

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

        // Si la habilidad no provoca problema de estado
        if (!skill.getProblema_estado().equals("")) {
            statusView.setText(skill.getProblema_estado());
            percView.setText(skill.getPorcentaje() + "");
        } else {
            probLayout.setVisibility(View.GONE);
        }


    }
}