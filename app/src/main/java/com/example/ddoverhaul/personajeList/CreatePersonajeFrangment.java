package com.example.ddoverhaul.personajeList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.IconsAdapter;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.habilidadList.HabilidadListFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CreatePersonajeFrangment extends Fragment {

    // Variables para la creación de personaje
    private Personaje personaje;
    private JSONHelper helper;
    private CreateAdapter objAdapter;
    private AlertDialog alert;
    // Variables EditText
    private EditText editName;
    private EditText editNivel;
    private EditText editVida;
    private EditText editMana;
    private EditText editRaza;
    private EditText editClase;
    // editStats
    private EditText editFuerza;
    private EditText editDestreza;
    private EditText editConstitucion;
    private EditText editInteligencia;
    private EditText editSabiduria;
    private EditText editCarisma;
    private EditText editVelocidad;

    private ImageView viewHelmet, viewArmor, viewPants,viewFoots,viewPrimaryWeapon,viewGloves,viewSecondaryWeapon;
    Equipo[] finalHelmets, finalArmors, finalPants, finalFoots, finalGloves, finalWeps1, finalWeps2;
    private ArrayList<ImageView> accesoriesImg;
    private ArrayList<ImageView> inventoryImg;
    private ArrayList<ImageView> skillsImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_personaje, container, false);

        // Inicializando variables
        editName = view.findViewById(R.id.NombreEdit);
        editNivel = view.findViewById(R.id.NivelEdit);
        editVida = view.findViewById(R.id.VidaEdit);
        editMana = view.findViewById(R.id.ManaEdit);
        editRaza = view.findViewById(R.id.RazaEdit);
        editClase = view.findViewById(R.id.ClaseEdit);
        editFuerza = view.findViewById(R.id.FuerzaEdit);
        editDestreza = view.findViewById(R.id.DestrzaEdit);
        editConstitucion = view.findViewById(R.id.ConstEdit);
        editInteligencia = view.findViewById(R.id.IntelEdit);
        editSabiduria = view.findViewById(R.id.SabiduriaEdit);
        editCarisma = view.findViewById(R.id.CarisEdit);
        editVelocidad = view.findViewById(R.id.VelocidadEdit);

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

        // Se comprueba si no se está editando un personaje

        // Si el extra existe, es que es una habilidad a editar y se modifican las variables
        int personajeId = -1;
        Bundle args = getArguments();
        if (args != null) {
            personajeId = args.getInt("personaje", -1);

        }

        if (personajeId != -1) {
            personaje = helper.getChar(personajeId);
            // Al existir, se le añaden los valores que tiene para su edición
            editName.setText(personaje.getNombre());
            editNivel.setText(personaje.getNivel() + "");
            editVida.setText(personaje.getVida() + "");
            editMana.setText(personaje.getMana() + "");
            editRaza.setText(personaje.getRaza() + "");
            editClase.setText(personaje.getClase() + "");
            editFuerza.setText(personaje.getFuerza() + "");
            editDestreza.setText(personaje.getDestreza() + "");
            editConstitucion.setText(personaje.getConstitucion() + "");
            editInteligencia.setText(personaje.getInteligencia() + "");
            editSabiduria.setText(personaje.getSabiduria() + "");
            editCarisma.setText(personaje.getCarisma() + "");
            editVelocidad.setText(personaje.getVelocidad() + "");


        } else {
            personaje = new Personaje();
            personaje.setId(-1);
        }

        prepareEquipment(view);

        return view;
    }

    // TODO añadir onclick para eliminar objeto
    // Método que prepara y muestra el equipamiento del personaje
    public void prepareEquipment(View v) {
        viewHelmet = v.findViewById(R.id.Equipo1);
        viewArmor = v.findViewById(R.id.Equipo2);
        viewPants = v.findViewById(R.id.Equipo3);
        viewFoots = v.findViewById(R.id.Equipo4);
        viewGloves = v.findViewById(R.id.Equipo6);
        viewPrimaryWeapon = v.findViewById(R.id.Equipo5);
        viewSecondaryWeapon = v.findViewById(R.id.Equipo7);

        // Prepara la cabeza
        if (personaje.getCabeza() != null) {
            int idIcon = getResources().getIdentifier(personaje.getCabeza().getIcono(), "drawable",getActivity().getPackageName());
            viewHelmet.setImageResource(idIcon);
            removeEquipment(viewHelmet,personaje.getCabeza().getNombre(),"Daño: "+personaje.getCabeza().getDanio()+", Armadura: "+personaje.getCabeza().getArmadura(), personaje.getCabeza().getId()+"", personaje.getCabeza().getTipo());
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewHelmet.setImageResource(R.drawable.icon_questionmark);
            Equipo[] equips = helper.getEquips();
            Equipo[] helmets = new Equipo[equips.length];

            int counter = 0;
            for (Equipo e: equips) {
                if (e.getPosicion() == 0) {
                    helmets[counter] = e;
                    counter++;
                }
            }
            finalHelmets = Arrays.copyOf(helmets, counter);
            if (finalHelmets.length > 0) prepareShowOnClick(viewHelmet, finalHelmets);
        }

        // Prepara la armadura
        if (personaje.getPerchera() != null) {
            int idIcon = getResources().getIdentifier(personaje.getPerchera().getIcono(), "drawable",getActivity().getPackageName());
            viewArmor.setImageResource(idIcon);
            //removeEquipment(viewHelmet,helmet.getNombre(),"Daño: "+helmet.getDanio()+", Armadura: "+helmet.getArmadura());
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewArmor.setImageResource(R.drawable.icon_questionmark);
            Equipo[] equips = helper.getEquips();
            Equipo[] armors = new Equipo[equips.length];

            int counter = 0;
            for (Equipo e: equips) {
                if (e.getPosicion() == 1) {
                    armors[counter] = e;
                    counter++;
                }
            }
            finalArmors = Arrays.copyOf(armors, counter);
            if (finalArmors.length > 0) prepareShowOnClick(viewArmor, finalArmors);
        }

        // Prepara los pantalones
        if (personaje.getPantalones() != null) {
            int idIcon = getResources().getIdentifier(personaje.getPantalones().getIcono(), "drawable",getActivity().getPackageName());
            viewPants.setImageResource(idIcon);
            //prepareOnClick(viewHelmet,helmet.getNombre(),"Daño: "+helmet.getDanio()+", Armadura: "+helmet.getArmadura());
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewPants.setImageResource(R.drawable.icon_questionmark);
            Equipo[] equips = helper.getEquips();
            Equipo[] pants = new Equipo[equips.length];

            int counter = 0;
            for (Equipo e: equips) {
                if (e.getPosicion() == 2) {
                    pants[counter] = e;
                    counter++;
                }
            }
            finalPants = Arrays.copyOf(pants, counter);
            if (finalPants.length > 0) prepareShowOnClick(viewPants, finalPants);
        }

        // Prepara los pies
        if (personaje.getPies() != null) {
            int idIcon = getResources().getIdentifier(personaje.getPies().getIcono(), "drawable",getActivity().getPackageName());
            viewFoots.setImageResource(idIcon);
            //prepareOnClick(viewHelmet,helmet.getNombre(),"Daño: "+helmet.getDanio()+", Armadura: "+helmet.getArmadura());
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewFoots.setImageResource(R.drawable.icon_questionmark);
            Equipo[] equips = helper.getEquips();
            Equipo[] foots = new Equipo[equips.length];

            int counter = 0;
            for (Equipo e: equips) {
                if (e.getPosicion() == 3) {
                    foots[counter] = e;
                    counter++;
                }
            }
            finalFoots = Arrays.copyOf(foots, counter);
            if (finalFoots.length > 0) prepareShowOnClick(viewFoots, finalFoots);
        }

        // Prepara los guantes
        if (personaje.getGuantes() != null) {
            int idIcon = getResources().getIdentifier(personaje.getGuantes().getIcono(), "drawable",getActivity().getPackageName());
            viewGloves.setImageResource(idIcon);
            //prepareOnClick(viewHelmet,helmet.getNombre(),"Daño: "+helmet.getDanio()+", Armadura: "+helmet.getArmadura());
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewGloves.setImageResource(R.drawable.icon_questionmark);
            Equipo[] equips = helper.getEquips();
            Equipo[] gloves = new Equipo[equips.length];

            int counter = 0;
            for (Equipo e: equips) {
                if (e.getPosicion() == 4) {
                    gloves[counter] = e;
                    counter++;
                }
            }
            finalGloves = Arrays.copyOf(gloves, counter);
            if (finalGloves.length > 0) prepareShowOnClick(viewGloves, finalGloves);
        }

        // Prepara la arma principal
        if (personaje.getArma() != null) {
            int idIcon = getResources().getIdentifier(personaje.getArma().getIcono(), "drawable",getActivity().getPackageName());
            viewPrimaryWeapon.setImageResource(idIcon);
            //prepareOnClick(viewHelmet,helmet.getNombre(),"Daño: "+helmet.getDanio()+", Armadura: "+helmet.getArmadura());
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewPrimaryWeapon.setImageResource(R.drawable.icon_questionmark);
            Equipo[] equips = helper.getEquips();
            Equipo[] weapons1 = new Equipo[equips.length];

            int counter = 0;
            for (Equipo e: equips) {
                if (e.getPosicion() == 5) {
                    weapons1[counter] = e;
                    counter++;
                }
            }
            finalWeps1 = Arrays.copyOf(weapons1, counter);
            if (finalWeps1.length > 0) prepareShowOnClick(viewPrimaryWeapon, finalWeps1);
        }

        // Prepara la arma secundaria
        if (personaje.getArma_sec() != null) {
            int idIcon = getResources().getIdentifier(personaje.getArma_sec().getIcono(), "drawable",getActivity().getPackageName());
            viewSecondaryWeapon.setImageResource(idIcon);
            //prepareOnClick(viewHelmet,helmet.getNombre(),"Daño: "+helmet.getDanio()+", Armadura: "+helmet.getArmadura());
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewSecondaryWeapon.setImageResource(R.drawable.icon_questionmark);
            Equipo[] equips = helper.getEquips();
            Equipo[] weapons2 = new Equipo[equips.length];

            int counter = 0;
            for (Equipo e: equips) {
                if (e.getPosicion() == 6) {
                    weapons2[counter] = e;
                    counter++;
                }
            }
            finalWeps2 = Arrays.copyOf(weapons2, counter);
            if (finalWeps2.length > 0) prepareShowOnClick(viewSecondaryWeapon, finalWeps2);
        }

    }

    // Método que prepara el onclick listener de los imagenview
    public void prepareShowOnClick(ImageView img, Objeto[] objs) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showObject(objs, v);
            }
        });
    }

    // Método que elimina el equipamiento
    public void removeEquipment(ImageView img, String title, String info, String type, String id) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(title);
                builder.setMessage(info)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Quitar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (type) {
                                    case "Equipo":
                                        Equipo e = helper.getEquip(Integer.parseInt(id));
                                        switch (e.getPosicion()) {
                                            case 0:
                                                personaje.setCabeza(null);
                                                viewHelmet.setImageResource(R.drawable.icon_questionmark);
                                                prepareShowOnClick(viewHelmet, finalHelmets);
                                                break;
                                            case 1:
                                                personaje.setPerchera(null);
                                                viewArmor.setImageResource(R.drawable.icon_questionmark);
                                                prepareShowOnClick(viewArmor, finalArmors);
                                                break;
                                            case 2:
                                                personaje.setPantalones(null);
                                                viewPants.setImageResource(R.drawable.icon_questionmark);
                                                prepareShowOnClick(viewPants, finalPants);
                                                break;
                                            case 3:
                                                personaje.setPies(null);
                                                viewFoots.setImageResource(R.drawable.icon_questionmark);
                                                prepareShowOnClick(viewFoots, finalFoots);
                                                break;
                                            case 4:
                                                personaje.setGuantes(null);
                                                viewGloves.setImageResource(R.drawable.icon_questionmark);
                                                prepareShowOnClick(viewGloves, finalGloves);
                                                break;
                                            case 5:
                                                personaje.setArma(null);
                                                viewPrimaryWeapon.setImageResource(R.drawable.icon_questionmark);
                                                prepareShowOnClick(viewPrimaryWeapon, finalWeps1);
                                                break;
                                            case 6:
                                                personaje.setArma_sec(null);
                                                viewSecondaryWeapon.setImageResource(R.drawable.icon_questionmark);
                                                prepareShowOnClick(viewSecondaryWeapon, finalWeps2);
                                                break;
                                            default:
                                                break;
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                alert.dismiss();
                            }
                        }).show();
            }
        });
    }


    // TODO añadir onclick para eliminar objeto
    public void showObject(Objeto[] objs, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Objeto a equipar")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        objAdapter = new CreateAdapter(objs,getContext(), true);

        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.addItemDecoration(new SpacingItemDecoration(15));
        recyclerView.setAdapter(objAdapter);
        objAdapter.setOnObjectClickListener(new CreateAdapter.OnObjectClickListener() {
            @Override
            public void onClick(int position, String id, String type) {
                switch (type) {
                    case "Equipo":
                        Equipo e = helper.getEquip(Integer.parseInt(id));
                        int iconID = getResources().getIdentifier(e.getIcono(), "drawable", getContext().getPackageName());
                        switch (e.getPosicion()) {
                            case 0:
                                personaje.setCabeza(e);
                                viewHelmet.setImageResource(iconID);
                                break;
                            case 1:
                                personaje.setPerchera(e);
                                viewArmor.setImageResource(iconID);
                                break;
                            case 2:
                                personaje.setPantalones(e);
                                viewPants.setImageResource(iconID);
                                break;
                            case 3:
                                personaje.setPies(e);
                                viewFoots.setImageResource(iconID);
                                break;
                            case 4:
                                personaje.setGuantes(e);
                                viewGloves.setImageResource(iconID);
                                break;
                            case 5:
                                personaje.setArma(e);
                                viewPrimaryWeapon.setImageResource(iconID);
                                break;
                            case 6:
                                personaje.setArma_sec(e);
                                viewSecondaryWeapon.setImageResource(iconID);
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                alert.dismiss();
            }
        });

        // Configura el diálogo para mostrar el RecyclerView
        builder.setView(recyclerView);

        // Muestra el diálogo
        alert = builder.show();

    }







    // Método que recoge los valores introducidos y guarda la habilidad en el JSON
    public void Save(View v) {
        // Se guardan todos los valores dentro de la skill para guardarla
        String name = editName.getText().toString();
        String nivel = editNivel.getText().toString();
        String vida = editVida.getText().toString();
        String mana = editMana.getText().toString();
        String raza = editRaza.getText().toString();
        String clase = editClase.getText().toString();
        String fuerza = editFuerza.getText().toString();
        String destreza = editDestreza.getText().toString();
        String constitucion = editConstitucion.getText().toString();
        String inteligencia = editInteligencia.getText().toString();
        String sabiduria = editSabiduria.getText().toString();
        String carisma = editCarisma.getText().toString();
        String velocidad = editVelocidad.getText().toString();
        if (name.equals("")) {
            Toast.makeText(getContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nivel.equals("")) {
            nivel = "0";
        }
        if (vida.equals("")) {
            vida = "0";
        }
        if (mana.equals("")) {
            mana = "0";
        }
        if (raza.equals("")) {
            raza = "0";
        }
        if (clase.equals("")) {
            clase = "0";
        }
        if (fuerza.equals("")) {
            fuerza = "0";
        }
        if (destreza.equals("")) {
            destreza = "0";
        }
        if (constitucion.equals("")) {
            constitucion = "0";
        }
        if (inteligencia.equals("")) {
            inteligencia = "0";
        }
        if (sabiduria.equals("")) {
            sabiduria = "0";
        }
        if (carisma.equals("")) {
            carisma = "0";
        }
        if (velocidad.equals("")) {
            velocidad = "0";
        }
        personaje.setNombre(name);
        personaje.setVida(Integer.parseInt(vida));
        personaje.setVida_Mx(Integer.parseInt(vida));
        personaje.setNivel(Integer.parseInt(nivel));
        personaje.setMana(Integer.parseInt(mana));
        personaje.setMana_Mx(Integer.parseInt(mana));
        personaje.setRaza(raza);
        personaje.setClase(clase);
        personaje.setFuerza(Integer.parseInt(fuerza));
        personaje.setDestreza(Integer.parseInt(destreza));
        personaje.setConstitucion(Integer.parseInt(constitucion));
        personaje.setInteligencia(Integer.parseInt(inteligencia));
        personaje.setSabiduria(Integer.parseInt(sabiduria));
        personaje.setCarisma(Integer.parseInt(carisma));
        personaje.setVelocidad(Integer.parseInt(velocidad));

        if (personaje.getId() != -1) {
            helper.updateCharacter(personaje);
            Toast.makeText(getContext(), "Se actualizó el personaje", Toast.LENGTH_SHORT).show();
        } else {
            helper.addCharacter(personaje);
            Toast.makeText(getContext(), "Se creó la personaje", Toast.LENGTH_SHORT).show();
        }

        // Una vez se creó el personaje, se vuelve a la lista de personaje
        PersonajeListFragment fragment = new PersonajeListFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_content, fragment)
                .commit();
    }

    public void Cancel() {
        PersonajeListFragment fragment = new PersonajeListFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_content, fragment)
                .commit();
    }
}