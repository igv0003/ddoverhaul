package com.example.ddoverhaul.habilidadList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.objetoList.ListaObjetosFragment;
import com.example.ddoverhaul.personajeList.PersonajeListFragment;

public class ViewSkillFragment extends Fragment {

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
    ImageView imageIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_skill, container, false);

        // Se inicializan las variables necesarias
        nameView = view.findViewById(R.id.name_skill);
        costView = view.findViewById(R.id.cost_skill);
        dmgView = view.findViewById(R.id.dmg_skill);
        statusView = view.findViewById(R.id.status_skill);
        percView = view.findViewById(R.id.perc_skill);
        commentView = view.findViewById(R.id.comment_skill);
        probLayout = view.findViewById(R.id.probstatus_layout);
        imageIcon = view.findViewById(R.id.icon_skill);
        helper = new JSONHelper(getContext());

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                HabilidadListFragment fragment = new HabilidadListFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_content, fragment)
                        .commit();
            }
        });

        String skillString = getArguments().getString("habilidad");
        int skillId = -1;
        Bundle args = getArguments();
        if (args != null) {
            skillId = args.getInt("habilidad", -1);

        }
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
        int idIcon = getResources().getIdentifier(skill.getIcono(),"drawable", getActivity().getPackageName());
        imageIcon.setImageResource(idIcon);

        // Si la habilidad no provoca problema de estado desaparece su layout
        if (!skill.getProblema_estado().equals("")) {
            statusView.setText(skill.getProblema_estado());
            percView.setText(skill.getPorcentaje() + "");
        } else {
            probLayout.setVisibility(View.GONE);
        }


        view.findViewById(R.id.edit_skill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSkill(skill.getId());
            }
        });

        view.findViewById(R.id.delete_skill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSkill(skill.getId());
            }
        });

        return view;
    }

    public void editSkill(int id) {
        CreateSkillFragment fragment = new CreateSkillFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("habilidad", id);
        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_content, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void deleteSkill(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar habilidad");
        builder.setMessage("¿Está seguro de que desea eliminar la habilidad?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteSkill(id);
                        Toast.makeText(getContext(), "Se eliminó la habilidad",Toast.LENGTH_SHORT).show();
                        HabilidadListFragment fragment = new HabilidadListFragment();
                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.activity_content, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Se canceló el borrado",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).show();
    }
}
