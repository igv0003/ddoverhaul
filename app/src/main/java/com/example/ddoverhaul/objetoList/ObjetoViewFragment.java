package com.example.ddoverhaul.objetoList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ddoverhaul.Consumibles;
import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.R;

public class ObjetoViewFragment extends Fragment {
    private JSONHelper helper;
    private TextView editName, editDescription, editDamage, editArmor, editValor, editCuantiti, editOperation;
    private Spinner spinnerTipo, spinnerEquipoPos, spinnerValor;
    private View equipoLayout, consumibleLayout;
    private Objeto obj;
    private String opcionSeleccionada;
    private Equipo equip;
    private Consumibles cons;
    private Button editButton;
    private ImageView iconView;
    private LinearLayout mainObj;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_objeto_view, container, false);

        editButton = rootView.findViewById(R.id.GuardarObj);
        editName = rootView.findViewById(R.id.caja_objeto);
        spinnerEquipoPos = rootView.findViewById(R.id.SpinnerPosicion);
        editDamage = rootView.findViewById(R.id.caja_danio);
        editArmor = rootView.findViewById(R.id.caja_arm);
        spinnerValor = rootView.findViewById(R.id.Spinnervalor);
        editDescription = rootView.findViewById(R.id.caja_descripcion);
        editCuantiti = rootView.findViewById(R.id.caja_cantidad);
        editOperation = rootView.findViewById(R.id.caja_operacion);
        spinnerEquipoPos = rootView.findViewById(R.id.SpinnerPosicion);
        spinnerTipo = rootView.findViewById(R.id.SpinnerTipo);
        equipoLayout = rootView.findViewById(R.id.equipoLayout);
        consumibleLayout = rootView.findViewById(R.id.consumibleLayout);
        mainObj = rootView.findViewById(R.id.activityObj);
        iconView = rootView.findViewById(R.id.ImageObj);

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Otro", "Equipo", "Consumible"});
        spinnerTipo.setAdapter(adapterTipo);
        ArrayAdapter<String> adapterEquipoPos = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Cabeza", "Pecho", "Manos", "Piernas", "Pies", "Arma Principal", "Arma Secundaria"});
        spinnerEquipoPos.setAdapter(adapterEquipoPos);
        ArrayAdapter<String> adapterValor = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Vida", "Mana", "Fuerza", "Destreza", "Constitucion", "Inteligencia", "Sabiduria", "Carisma", "Velocidad"});
        spinnerValor.setAdapter(adapterValor);
        int indexC = mainObj.indexOfChild(consumibleLayout);


        String idString = getArguments().getString("objeto");
        String type = getArguments().getString("type");

        Bundle args = getArguments();
        int id = -1;
        try {
            id = Integer.parseInt(idString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        helper = new JSONHelper(requireContext());
        switch (type) {
            default://OTROS
                if (id != -1) {
                    obj = helper.getObject(id);
                    // Al existir, se le añaden los valores que tiene para su edición
                    editName.setText(obj.getNombre());
                    editDescription.setText(obj.getDescripcion());
                    int idIcon = getResources().getIdentifier(obj.getIcono(),"drawable", getActivity().getPackageName());
                    iconView.setImageResource(idIcon);
                    equipoLayout.setVisibility(View.INVISIBLE);
                    consumibleLayout.setVisibility(View.INVISIBLE);
                    spinnerTipo.setSelection(0);
                } else {
                    Toast.makeText(requireContext(), "NO SE HA ENCONTRADO", Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                }
                break;
            case "Equipo"://EQUIPO
                if (id != -1) {
                    equip = helper.getEquip(id);
                    // Al existir, se le añaden los valores que tiene para su edición
                    editName.setText(equip.getNombre());
                    editDescription.setText(equip.getDescripcion());
                    editDamage.setText(String.valueOf(equip.getDanio()));
                    editArmor.setText(String.valueOf(equip.getArmadura()));
                    spinnerEquipoPos.setSelection(equip.getPosicion());
                    int idIcon = getResources().getIdentifier(equip.getIcono(),"drawable", getActivity().getPackageName());
                    iconView.setImageResource(idIcon);
                    equipoLayout.setVisibility(View.VISIBLE);
                    spinnerTipo.setSelection(1);
                } else {
                    Toast.makeText(requireContext(), "NO SE HA ENCONTRADO", Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                }
                break;
            case "Consumible"://CONSUMIBLE
                if (id != -1) {
                    cons = helper.getCons(id);
                    // Al existir, se le añaden los valores que tiene para su edición
                    editName.setText(cons.getNombre());
                    editDescription.setText(cons.getDescripcion());
                    spinnerValor.setSelection(cons.getValor());
                    editCuantiti.setText(String.valueOf(cons.getCantidad()));
                    int idIcon = getResources().getIdentifier(cons.getIcono(),"drawable", getActivity().getPackageName());
                    iconView.setImageResource(idIcon);
                    editOperation.setText(cons.getOperacion());
                    consumibleLayout.setVisibility(View.VISIBLE);
                    spinnerTipo.setSelection(2);
                } else {
                    Toast.makeText(requireContext(), "NO SE HA ENCONTRADO", Toast.LENGTH_SHORT).show();
                    requireActivity().finish();
                }
                break;
        }

        final int idF = id;
        spinnerTipo.setEnabled(false);
        spinnerValor.setEnabled(false);
        spinnerEquipoPos.setEnabled(false);
        String finalType = type;
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opcionSeleccionada = parent.getItemAtPosition(position).toString();
                consumibleLayout.setVisibility(View.INVISIBLE);
                equipoLayout.setVisibility(View.INVISIBLE);
                mainObj.removeView(equipoLayout);
                mainObj.removeView(consumibleLayout);
                if (finalType.equals("Equipo")) {
                    mainObj.addView(equipoLayout, indexC);
                    equipoLayout.setVisibility(View.VISIBLE);
                } else if (finalType.equals("Consumible")) {
                    mainObj.addView(consumibleLayout, indexC);
                    consumibleLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rootView.findViewById(R.id.CancelarObj).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteObj(Integer.parseInt(idString), type);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("Equipo")) {
                    editObj(idF, type);
                } else if (type.equals("Consumible")) {
                    editObj(idF, type);
                } else {
                    editObj(idF, type);
                }
            }
        });

        return rootView;
    }

    public void editObj(int id, String type) {
        CreateobjetoFragment fragment = new CreateobjetoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("objeto", id);
        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_content, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void deleteObj(int id, String type) {
            // Implementa el código para eliminar el objeto con el ID y el tipo especificados.
    }
}

