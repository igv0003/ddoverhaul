package com.example.ddoverhaul.objetoList;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ddoverhaul.Consumibles;
import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.IconsAdapter;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.habilidadList.HabilidadListFragment;
import com.example.ddoverhaul.habilidadList.ViewSkillFragment;
import com.example.ddoverhaul.multiplayer.MultiSelectorFragment;
import com.example.ddoverhaul.personajeList.PersonajeListFragment;

public class CreateobjetoFragment extends Fragment implements IconsAdapter.OnIconClickListener {
    private Spinner SpinnerTipo, SpinnerEquipoPos, SpinnerValor;
    private String opcionSeleccionada;
    private View EquipoLayout, ConsumibleLayout;
    private LinearLayout mainObj;
    private EditText editName, editDescription;
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
    // Variables para mostrar los iconos
    private ImageView ImagenObj;
    private String editIcon;
    private AlertDialog alert;
    private IconsAdapter adapter;
    private int finalIcons[];
    private int iconOthers[] = {R.drawable.book, R.drawable.chest, R.drawable.key};
    private int iconCons[] = {R.drawable.pocion1,R.drawable.pocion2, R.drawable.pocion3, R.drawable.feather};
    private int iconHead[] = {R.drawable.helmet, R.drawable.hat};
    private int iconArmor[] = {R.drawable.armor1,R.drawable.armor2};
    private int iconGloves[]= {R.drawable.gloves1,R.drawable.gloves2};
    private int iconPants[] = {R.drawable.pants1, R.drawable.pants2};
    private int iconFoots[] = {R.drawable.foot2, R.drawable.foot1};
    private int iconWeapons[] = {R.drawable.weapon1, R.drawable.weapon2, R.drawable.weapon3};




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_objeto, container, false);

        // Referencia al Spinner
        SpinnerTipo = rootView.findViewById(R.id.SpinnerTipo);
        EquipoLayout = rootView.findViewById(R.id.equipoLayout);
        ConsumibleLayout = rootView.findViewById(R.id.consumibleLayout);
        mainObj = rootView.findViewById(R.id.activityObj);
        ImagenObj = rootView.findViewById(R.id.ImageObj);

        guardarBTN = rootView.findViewById(R.id.GuardarObj);
        cancelarBTN = rootView.findViewById(R.id.CancelarObj);
        //Referenciar EditText
        editName = rootView.findViewById(R.id.caja_objeto);
        SpinnerEquipoPos = rootView.findViewById(R.id.SpinnerPosicion);
        editDamage = rootView.findViewById(R.id.caja_danio);
        editArmor = rootView.findViewById(R.id.caja_arm);
        SpinnerValor = rootView.findViewById(R.id.Spinnervalor);
        editDescription = rootView.findViewById(R.id.caja_descripcion);
        editCuantiti = rootView.findViewById(R.id.caja_cantidad);
        editOperation = rootView.findViewById(R.id.caja_operacion);

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Otro", "Equipo", "Consumible"});
        SpinnerTipo.setAdapter(adapterTipo);
        ArrayAdapter<String> adapterEquipoPos = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Cabeza", "Pecho", "Manos", "Piernas", "Pies", "Arma Principal", "Arma Secundaria"});
        SpinnerEquipoPos.setAdapter(adapterEquipoPos);
        SpinnerEquipoPos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtiene el personaje seleccionado del adaptador
                switch (adapterEquipoPos.getItem(position)) {
                    case "Cabeza":
                        finalIcons = iconHead;
                        ImagenObj.setImageResource(R.drawable.questionmark);
                        break;
                    case "Pecho":
                        finalIcons = iconArmor;
                        ImagenObj.setImageResource(R.drawable.questionmark);
                        break;
                    case "Manos":
                        finalIcons = iconGloves;
                        ImagenObj.setImageResource(R.drawable.questionmark);
                        break;
                    case "Piernas":
                        finalIcons = iconPants;
                        ImagenObj.setImageResource(R.drawable.questionmark);
                        break;
                    case "Pies":
                        finalIcons = iconFoots;
                        ImagenObj.setImageResource(R.drawable.questionmark);
                        break;
                    case "Arma Principal":
                    case "Arma Secundaria":
                        finalIcons = iconWeapons;
                        ImagenObj.setImageResource(R.drawable.questionmark);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acción a realizar cuando no se selecciona ningún elemento
            }
        });
        ArrayAdapter<String> adapterValor = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{"Vida", "Mana", "Fuerza", "Destreza", "Constitucion", "Inteligencia", "Sabiduria", "Carisma", "Velocidad"});
        SpinnerValor.setAdapter(adapterValor);

        helper = new JSONHelper(requireContext());

        ImagenObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalIcons != null) {
                    showIcons(rootView);
                }
            }
        });


        String idString = requireActivity().getIntent().getStringExtra("objeto");
        String type = requireActivity().getIntent().getStringExtra("type");
        int id = -1;
        try {
            id = Integer.parseInt(idString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(type == null){
            int tipin = SpinnerTipo.getSelectedItemPosition();
            switch (tipin){
                default:
                    type = "Otro";
                    break;
                case 1:
                    type = "Equipo";
                    finalIcons = null;
                    break;
                case 2:
                    type = "Consumible";
                    break;
            }
            id = -1;
        }
            switch (type) {
                case "Equipo": // EQUIPO
                    if (id != -1) {
                        equip = helper.getEquip(id);
                        // Al existir, se le añaden los valores que tiene para su edición
                        editName.setText(equip.getNombre());
                        editDescription.setText(equip.getDescripcion());
                        editDamage.setText(String.valueOf(equip.getDanio()));
                        editArmor.setText(String.valueOf(equip.getArmadura()));
                        int idIcon = getResources().getIdentifier(equip.getIcono(),"drawable", getActivity().getPackageName());
                        ImagenObj.setImageResource(idIcon);
                        editIcon = equip.getIcono();
                        EquipoLayout.setVisibility(View.VISIBLE);
                        SpinnerTipo.setSelection(2);
                        SpinnerTipo.setEnabled(false);
                    } else {
                        equip = new Equipo();
                        editIcon = "questionmark";
                        ImagenObj.setImageResource(R.drawable.questionmark);
                        equip.setId(-1);
                    }
                    break;
                case "Consumible": // CONSUMIBLE
                    if (id != -1) {
                        cons = helper.getCons(id);
                        // Al existir, se le añaden los valores que tiene para su edición
                        editName.setText(cons.getNombre());
                        editDescription.setText(cons.getDescripcion());
                        SpinnerValor.setSelection(cons.getValor());
                        editCuantiti.setText(String.valueOf(cons.getCantidad()));
                        editOperation.setText(String.valueOf(cons.getOperacion()));
                        int idIcon = getResources().getIdentifier(cons.getIcono(),"drawable", getActivity().getPackageName());
                        ImagenObj.setImageResource(idIcon);
                        editIcon = cons.getIcono();
                        ConsumibleLayout.setVisibility(View.VISIBLE);
                        SpinnerTipo.setSelection(1);
                        SpinnerTipo.setEnabled(false);
                    } else {
                        equip = new Equipo();
                        editIcon = "questionmark";
                        ImagenObj.setImageResource(R.drawable.questionmark);
                        equip.setId(-1);
                    }
                    break;
                case "Otro": // OTROS
                    if (id != -1) {
                        obj = helper.getObject(id);
                        // Al existir, se le añaden los valores que tiene para su edición
                        String nm = obj.getNombre();
                        editName.setText(obj.getNombre());
                        editDescription.setText(obj.getDescripcion());
                        EquipoLayout.setVisibility(View.INVISIBLE);
                        ConsumibleLayout.setVisibility(View.INVISIBLE);
                        int idIcon = getResources().getIdentifier(obj.getIcono(),"drawable", getActivity().getPackageName());
                        ImagenObj.setImageResource(idIcon);
                        editIcon = obj.getIcono();
                        SpinnerTipo.setSelection(0);
                        SpinnerTipo.setEnabled(false);
                    } else {
                        obj = new Objeto();
                        editIcon = "questionmark";
                        ImagenObj.setImageResource(R.drawable.questionmark);
                        obj.setId(-1);
                    }
                    break;
                default:
                    break;
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
                finalIcons = iconOthers;
                ImagenObj.setImageResource(R.drawable.questionmark);
                if (opcionSeleccionada.equals("Equipo")) {
                    mainObj.addView(EquipoLayout, indexC);
                    EquipoLayout.setVisibility(View.VISIBLE);
                    finalIcons = null;
                    ImagenObj.setImageResource(R.drawable.questionmark);
                } else if (opcionSeleccionada.equals("Consumible")) {
                    mainObj.addView(ConsumibleLayout, indexC);
                    ConsumibleLayout.setVisibility(View.VISIBLE);
                    finalIcons = iconCons;
                    ImagenObj.setImageResource(R.drawable.questionmark);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Si no se selecciona nada
            }
        });

        guardarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Boton cuando se haga clic en el botón "Guardar"
                String nombre = editName.getText().toString();
                String descrip = editDescription.getText().toString();
                String Tipo = "Otro";
                // Equipo
                int danio = 0;
                int arm = 0;
                int posicion = 0;
                // Consumible
                int valor = 0;
                int cantidad = 0;
                char operacion = '+';
                    switch (opcionSeleccionada) {

                        case "Otro":
                            Tipo = "Otro";
                            if(obj == null){
                                obj = new Objeto();
                            }
                            obj.setTipo(Tipo);
                            obj.setNombre(nombre);
                            obj.setDescripcion(descrip);
                            obj.setTipo(Tipo);
                            obj.setIcono(editIcon);
                            if (obj.getId() == -1) {
                                helper.addObject(obj);
                                Toast.makeText(requireContext(), "Objeto guardado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                helper.updateObject(obj);
                                Toast.makeText(requireContext(), "Objeto actualizado correctamente", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "Equipo":
                            Tipo = "Equipo";
                            if(equip == null){
                                equip = new Equipo();
                            }
                            danio = Integer.parseInt(editDamage.getText().toString());
                            arm = Integer.parseInt(editArmor.getText().toString());
                            posicion = SpinnerEquipoPos.getSelectedItemPosition();
                            equip.setNombre(nombre);
                            equip.setDescripcion(descrip);
                            equip.setDanio(danio);
                            equip.setArmadura(arm);
                            equip.setPosicion(posicion);
                            equip.setTipo(Tipo);
                            equip.setIcono(editIcon);
                            if (equip.getId() == -1) {
                                helper.addEquip(equip);
                                Toast.makeText(requireContext(), "Equipo guardado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                helper.updateEquip(equip);
                                Toast.makeText(requireContext(), "Equipo actualizado correctamente", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case "Consumible":
                            Tipo = "Consumible";
                            if(cons == null){
                                cons = new Consumibles();
                            }

                            valor = SpinnerValor.getSelectedItemPosition();
                            cantidad = Integer.parseInt(editCuantiti.getText().toString());
                            operacion = editOperation.getText().toString().charAt(0);
                            cons.setNombre(nombre);
                            cons.setDescripcion(descrip);
                            cons.setValor(valor);//0=Vida;1=Mana...8=Velocidad
                            cons.setCantidad(cantidad);
                            cons.setOperacion(operacion);
                            cons.setTipo(Tipo);
                            cons.setIcono(editIcon);
                            if (cons.getId() == -1) {
                                helper.addCons(cons);
                                Toast.makeText(requireContext(), "Consumible guardado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                helper.updateCons(cons);
                                Toast.makeText(requireContext(), "Consumible actualizado correctamente", Toast.LENGTH_SHORT).show();
                            }
                                break;
                    }
                cambiarfragment();
            }
        });

        cancelarBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarfragment();
            }
        });

        return rootView;
    }


    public void showIcons(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Selecciona un icono");

        // Adaptador personalizado para el RecyclerView
        adapter = new IconsAdapter(finalIcons,this);

        // Configuración del RecyclerView
        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),3));
        recyclerView.setAdapter(adapter);

        // Configura el diálogo para mostrar el RecyclerView
        builder.setView(recyclerView);

        // Muestra el diálogo
        alert = builder.show();

    }


    @Override
    public void onIconClick(int iconID) {
        ImagenObj.setImageResource(iconID);
        editIcon = getResources().getResourceEntryName(iconID);
        if (alert != null) {
            alert.dismiss();
        }
    }

    public void cambiarfragment(){
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_content, new ListaObjetosFragment());
        transaction.commit();
    }


}
