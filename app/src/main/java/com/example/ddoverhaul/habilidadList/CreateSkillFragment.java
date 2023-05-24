package com.example.ddoverhaul.habilidadList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.R;

public class CreateSkillFragment extends Fragment {

    // Variables para la creación de habilidades
    private Habilidades skill;
    private JSONHelper helper;
    // Variables EditText
    private EditText editName;
    private EditText editCost;
    private EditText editDmg;
    private EditText editStatus;
    private EditText editPerc;
    private EditText editDescription;
    private Button Cancel;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_skill, container, false);

        // Inicializando variables
        editName = view.findViewById(R.id.edit_name_skill);
        editCost = view.findViewById(R.id.edit_cost_skill);
        editDmg = view.findViewById(R.id.edit_dmg_skill);
        editStatus = view.findViewById(R.id.edit_status_skill);
        editPerc = view.findViewById(R.id.edit_perc_skill);
        editDescription = view.findViewById(R.id.edit_comment_skill);
        helper = new JSONHelper(getContext());
        view.findViewById(R.id.botoncancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cancel();
            }
        });
        view.findViewById(R.id.botonguardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save(view);
            }
        });

        // Se comprueba si no se está editando una habilidad

        // Si el extra existe, es que es una habilidad a editar y se modifican las variables
        int skillId = -1;
        Bundle args = getArguments();
            if (args != null) {
                skillId = args.getInt("habilidad", -1);

            }

        if (skillId != -1) {
            skill = helper.getSkill(skillId);
            // Al existir, se le añaden los valores que tiene para su edición
            editName.setText(skill.getNombre());
            editCost.setText(skill.getCoste() + "");
            editDmg.setText(skill.getDanio() + "");
            editStatus.setText(skill.getProblema_estado());
            editPerc.setText(skill.getPorcentaje() + "");
            editDescription.setText(skill.getDescripcion());
        } else {
            skill = new Habilidades();
            skill.setId(-1);
        }

        return view;
    }

    // Método que recoge los valores introducidos y guarda la habilidad en el JSON
    public void Save(View v) {
        // Se guardan todos los valores dentro de la skill para guardarla
        String name = editName.getText().toString();
        String cost = editCost.getText().toString();
        String dmg = editDmg.getText().toString();
        if (name.equals("")) {
            Toast.makeText(getContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cost.equals("")) {
            cost = "0";
        }
        if (dmg.equals("")) {
            dmg = "0";
        }
        skill.setNombre(name);
        skill.setCoste(Integer.parseInt(cost));
        skill.setDanio(Integer.parseInt(dmg));
        skill.setDescripcion(editDescription.getText().toString());

        // Si la habilidad no provoca un estado se deja vacío
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
            Toast.makeText(getContext(), "Se actualizó la habilidad", Toast.LENGTH_SHORT).show();
        } else {
            helper.addSkill(skill);
            Toast.makeText(getContext(), "Se creó la habilidad", Toast.LENGTH_SHORT).show();
        }

        // Una vez se creó la habilidad, se vuelve a la lista de habilidades
        HabilidadListFragment fragment = new HabilidadListFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_content, fragment)
                .commit();
    }

    public void Cancel() {
        HabilidadListFragment fragment = new HabilidadListFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_content, fragment)
                .commit();
    }

}
