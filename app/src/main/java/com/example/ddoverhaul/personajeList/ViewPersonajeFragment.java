package com.example.ddoverhaul.personajeList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ddoverhaul.Consumibles;
import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.habilidadList.CreateSkillFragment;
import com.example.ddoverhaul.habilidadList.HabilidadListFragment;
import com.example.ddoverhaul.multiplayer.MASTER.PersonajeListFragmentMaster;

import java.util.ArrayList;

public class ViewPersonajeFragment extends Fragment {
    // Variables necesarias
    private Personaje personajeP;
    private JSONHelper helper;
    private ImageView fotopersonaje;
    private TextView nombre;
    private TextView nivel;
    private TextView mana;
    private TextView vida;
    private TextView raza;
    private TextView clase;
    private TextView fuerza;
    private TextView destreza;
    private TextView constitucion;
    private TextView inteligencia;
    private TextView sabiduria;
    private TextView carisma;
    private TextView velocidad;
    private ImageView viewHelmet, viewArmor, viewPants,viewFoots,viewPrimaryWeapon,viewGloves,viewSecondaryWeapon;
    private ArrayList<ImageView> accesoriesImg;
    private ArrayList<ImageView> inventoryImg;
    private ArrayList<ImageView> skillsImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personaje, container, false);

        fotopersonaje = view.findViewById(R.id.imagenPersonaje);
        nombre = view.findViewById(R.id.NombreText);
        raza = view.findViewById(R.id.RazaTV);
        clase = view.findViewById(R.id.ClaseText);
        nivel = view.findViewById(R.id.NivelTV);
        vida = view.findViewById(R.id.VidaTV);
        mana = view.findViewById(R.id.ManaTV);
        fuerza = view.findViewById(R.id.FuerzaTV);
        destreza = view.findViewById(R.id.DestrzaTV);
        constitucion = view.findViewById(R.id.ConstTV);
        inteligencia = view.findViewById(R.id.IntelTV);
        sabiduria = view.findViewById(R.id.SabiduriaTV);
        carisma = view.findViewById(R.id.CarismaTV);
        velocidad = view.findViewById(R.id.VelocidadTV);

        helper = new JSONHelper(getContext());

        String personajeString = getArguments().getString("personaje");
        int personajeID = -1;
        Bundle args = getArguments();
        if (args != null) {
            personajeID = args.getInt("personaje", -1);
        }
        try {
            personajeID = Integer.parseInt(personajeString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                PersonajeListFragment fragment = new PersonajeListFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_content, fragment)
                        .commit();
            }
        });

        personajeP = helper.getChar(personajeID);

        // Información general
        nombre.setText(personajeP.getNombre());
        nivel.setText(personajeP.getNivel() + "");
        raza.setText(personajeP.getRaza());
        clase.setText(personajeP.getClase());
        vida.setText(personajeP.getVida() + "");
        mana.setText(personajeP.getMana() + "");
        fuerza.setText(personajeP.getFuerza() + "");
        destreza.setText(personajeP.getDestreza() + "");
        constitucion.setText(personajeP.getConstitucion() + "");
        inteligencia.setText(personajeP.getInteligencia() + "");
        sabiduria.setText(personajeP.getSabiduria() + "");
        carisma.setText(personajeP.getCarisma() + "");
        velocidad.setText(personajeP.getVelocidad() + "");

        // Icono del personaje
        int idIcon = getResources().getIdentifier(personajeP.getImagen(),"drawable", getActivity().getPackageName());
        fotopersonaje.setImageResource(idIcon);

        prepareEquipment(view);
        prepareAccesory(view);
        prepareInventory(view);
        prepareSkills(view);

        view.findViewById(R.id.edit_personaje).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPersonaje(personajeP.getId());
            }
        });

        view.findViewById(R.id.delete_pesonaje).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePersonaje(personajeP.getId());
            }
        });
        return view;
    }

    // Método que prepara y muestra el equipamiento del personaje
    public void prepareEquipment(View v) {
        viewHelmet = v.findViewById(R.id.view_Helmet);
        viewArmor = v.findViewById(R.id.view_Armor);
        viewPants = v.findViewById(R.id.view_Pants);
        viewFoots = v.findViewById(R.id.view_Foots);
        viewGloves = v.findViewById(R.id.view_Gloves);
        viewPrimaryWeapon = v.findViewById(R.id.view_PrimaryWeapon);
        viewSecondaryWeapon = v.findViewById(R.id.view_SecondaryWeapon);

        // Prepara la cabeza
        if (personajeP.getCabeza() != null) {
            Equipo helmet = helper.getEquip(personajeP.getCabeza().getId());
            if (helmet != null) {
                if (helmet.equals(personajeP.getCabeza())) {
                    int idIcon = getResources().getIdentifier(helmet.getIcono(), "drawable",getActivity().getPackageName());
                    viewHelmet.setImageResource(idIcon);
                    prepareOnClick(viewHelmet,helmet.getNombre(),"Daño: "+helmet.getDanio()+", Armadura: "+helmet.getArmadura());
                } else {
                    personajeP.setCabeza(helmet);
                    int idIcon = getResources().getIdentifier(helmet.getIcono(), "drawable",getActivity().getPackageName());
                    viewHelmet.setImageResource(idIcon);
                    prepareOnClick(viewHelmet,helmet.getNombre(),"Daño: "+helmet.getDanio()+", Armadura: "+helmet.getArmadura());
                }
            } else {
                personajeP.setCabeza(null);
                viewHelmet.setImageResource(R.drawable.icon_questionmark);
            }
        } else {
            viewHelmet.setImageResource(R.drawable.icon_questionmark);
        }

        // Prepara la armadura
        if (personajeP.getPerchera() != null) {
            Equipo perchera = helper.getEquip(personajeP.getPerchera().getId());
            if (perchera != null) {
                if (perchera.equals(personajeP.getPerchera())) {
                    int idIcon = getResources().getIdentifier(perchera.getIcono(), "drawable",getActivity().getPackageName());
                    viewArmor.setImageResource(idIcon);
                    prepareOnClick(viewArmor,perchera.getNombre(),"Daño: "+perchera.getDanio()+", Armadura: "+perchera.getArmadura());
                } else {
                    personajeP.setPerchera(perchera);
                    int idIcon = getResources().getIdentifier(perchera.getIcono(), "drawable",getActivity().getPackageName());
                    viewArmor.setImageResource(idIcon);
                    prepareOnClick(viewArmor,perchera.getNombre(),"Daño: "+perchera.getDanio()+", Armadura: "+perchera.getArmadura());
                }
            } else {
                personajeP.setPerchera(null);
                viewArmor.setImageResource(R.drawable.icon_questionmark);
            }
        } else {
            viewArmor.setImageResource(R.drawable.icon_questionmark);
        }

        // Prepara los pantalones
        if (personajeP.getPantalones() != null) {
            Equipo pants = helper.getEquip(personajeP.getPantalones().getId());
            if (pants != null) {
                if (pants.equals(personajeP.getPantalones())) {
                    int idIcon = getResources().getIdentifier(pants.getIcono(), "drawable",getActivity().getPackageName());
                    viewPants.setImageResource(idIcon);
                    prepareOnClick(viewPants,pants.getNombre(),"Daño: "+pants.getDanio()+", Armadura: "+pants.getArmadura());
                } else {
                    personajeP.setPantalones(pants);
                    int idIcon = getResources().getIdentifier(pants.getIcono(), "drawable",getActivity().getPackageName());
                    viewPants.setImageResource(idIcon);
                    prepareOnClick(viewPants,pants.getNombre(),"Daño: "+pants.getDanio()+", Armadura: "+pants.getArmadura());
                }
            } else {
                personajeP.setPantalones(null);
                viewPants.setImageResource(R.drawable.icon_questionmark);
            }
        } else {
            viewPants.setImageResource(R.drawable.icon_questionmark);
        }

        // Prepara los pies
        if (personajeP.getPies() != null) {
            Equipo foots = helper.getEquip(personajeP.getPies().getId());
            if (foots != null) {
                if (foots.equals(personajeP.getPies())) {
                    int idIcon = getResources().getIdentifier(foots.getIcono(), "drawable",getActivity().getPackageName());
                    viewFoots.setImageResource(idIcon);
                    prepareOnClick(viewFoots,foots.getNombre(),"Daño: "+foots.getDanio()+", Armadura: "+foots.getArmadura());
                } else {
                    personajeP.setPies(foots);
                    int idIcon = getResources().getIdentifier(foots.getIcono(), "drawable",getActivity().getPackageName());
                    viewFoots.setImageResource(idIcon);
                    prepareOnClick(viewFoots,foots.getNombre(),"Daño: "+foots.getDanio()+", Armadura: "+foots.getArmadura());
                }
            } else {
                personajeP.setPies(null);
                viewFoots.setImageResource(R.drawable.icon_questionmark);
            }
        } else {
            viewFoots.setImageResource(R.drawable.icon_questionmark);
        }

        // Prepara los guantes
        if (personajeP.getGuantes() != null) {
            Equipo gloves = helper.getEquip(personajeP.getGuantes().getId());
            if (gloves != null) {
                if (gloves.equals(personajeP.getGuantes())) {
                    int idIcon = getResources().getIdentifier(gloves.getIcono(), "drawable",getActivity().getPackageName());
                    viewGloves.setImageResource(idIcon);
                    prepareOnClick(viewGloves,gloves.getNombre(),"Daño: "+gloves.getDanio()+", Armadura: "+gloves.getArmadura());
                } else {
                    personajeP.setGuantes(gloves);
                    int idIcon = getResources().getIdentifier(gloves.getIcono(), "drawable",getActivity().getPackageName());
                    viewGloves.setImageResource(idIcon);
                    prepareOnClick(viewGloves,gloves.getNombre(),"Daño: "+gloves.getDanio()+", Armadura: "+gloves.getArmadura());
                }
            } else {
                personajeP.setGuantes(null);
                viewGloves.setImageResource(R.drawable.icon_questionmark);
            }
        } else {
            viewGloves.setImageResource(R.drawable.icon_questionmark);
        }

        // Prepara la arma principal
        if (personajeP.getArma() != null) {
            Equipo wep1 = helper.getEquip(personajeP.getArma().getId());
            if (wep1 != null) {
                if (wep1.equals(personajeP.getArma())) {
                    int idIcon = getResources().getIdentifier(wep1.getIcono(), "drawable",getActivity().getPackageName());
                    viewPrimaryWeapon.setImageResource(idIcon);
                    prepareOnClick(viewPrimaryWeapon,wep1.getNombre(),"Daño: "+wep1.getDanio()+", Armadura: "+wep1.getArmadura());
                } else {
                    personajeP.setArma(wep1);
                    int idIcon = getResources().getIdentifier(wep1.getIcono(), "drawable",getActivity().getPackageName());
                    viewPrimaryWeapon.setImageResource(idIcon);
                    prepareOnClick(viewPrimaryWeapon,wep1.getNombre(),"Daño: "+wep1.getDanio()+", Armadura: "+wep1.getArmadura());
                }
            } else {
                personajeP.setArma(null);
                viewPrimaryWeapon.setImageResource(R.drawable.icon_questionmark);
            }
        } else {
            viewPrimaryWeapon.setImageResource(R.drawable.icon_questionmark);
        }

        // Prepara la arma secundaria
        if (personajeP.getArma_sec() != null) {
            Equipo wep1 = helper.getEquip(personajeP.getArma_sec().getId());
            if (wep1 != null) {
                if (wep1.equals(personajeP.getArma_sec())) {
                    int idIcon = getResources().getIdentifier(wep1.getIcono(), "drawable",getActivity().getPackageName());
                    viewSecondaryWeapon.setImageResource(idIcon);
                    prepareOnClick(viewSecondaryWeapon,wep1.getNombre(),"Daño: "+wep1.getDanio()+", Armadura: "+wep1.getArmadura());
                } else {
                    personajeP.setArma_sec(wep1);
                    int idIcon = getResources().getIdentifier(wep1.getIcono(), "drawable",getActivity().getPackageName());
                    viewSecondaryWeapon.setImageResource(idIcon);
                    prepareOnClick(viewSecondaryWeapon,wep1.getNombre(),"Daño: "+wep1.getDanio()+", Armadura: "+wep1.getArmadura());
                }
            } else {
                personajeP.setArma_sec(null);
                viewSecondaryWeapon.setImageResource(R.drawable.icon_questionmark);
            }
        } else {
            viewSecondaryWeapon.setImageResource(R.drawable.icon_questionmark);
        }

        helper.updateCharacter(personajeP);
    }

    // Método que recorre los accesorios del personaje y los muestra
    private void prepareAccesory(View v) {
        ImageView acc1 = v.findViewById(R.id.view_Accesory1);
        ImageView acc2 = v.findViewById(R.id.view_Accesory2);
        accesoriesImg = new ArrayList<>();
        accesoriesImg.add(acc1);
        accesoriesImg.add(acc2);

        ArrayList<Objeto> accesories = personajeP.getAccesorios();
        ArrayList<Objeto> newAccesories =  new ArrayList<>();

        int left = accesories.size();
        for (int i = 0; i < accesoriesImg.size(); i++) {
            if (left > 0) {
                Objeto acc = accesories.get(i);
                Objeto accjson = helper.getObject(acc.getId());
                if (accjson != null) {
                    // Comprueba que el accesorio que tiene personaje es el mismo que existe en el json
                    if (acc.equals(accjson)) {
                        newAccesories.add(accjson);
                        int idIcon = getResources().getIdentifier(acc.getIcono(), "drawable", getActivity().getPackageName());
                        accesoriesImg.get(i).setImageResource(idIcon);
                        prepareOnClick(accesoriesImg.get(i), acc.getNombre(), acc.getDescripcion());
                    } else {
                        newAccesories.add(accjson);
                        int idIcon = getResources().getIdentifier(accjson.getIcono(), "drawable", getActivity().getPackageName());
                        accesoriesImg.get(i).setImageResource(idIcon);
                        prepareOnClick(accesoriesImg.get(i), accjson.getNombre(), accjson.getDescripcion());
                    }
                } else {
                    accesoriesImg.get(i).setImageResource(R.drawable.icon_questionmark);
                }
                left--;
            } else {
                accesoriesImg.get(i).setImageResource(R.drawable.icon_questionmark);
            }
        }

        personajeP.addNewAccesories(newAccesories);
        helper.updateCharacter(personajeP);

    }

    // Método que recorre el inventario del personaje y lo muestra
    private void prepareInventory(View v) {
        ImageView inv1 = v.findViewById(R.id.view_Inventory1);
        ImageView inv2 = v.findViewById(R.id.view_Inventory2);
        ImageView inv3 = v.findViewById(R.id.view_Inventory3);
        ImageView inv4 = v.findViewById(R.id.view_Inventory4);
        ImageView inv5 = v.findViewById(R.id.view_Inventory5);
        inventoryImg = new ArrayList<>();
        inventoryImg.add(inv1);
        inventoryImg.add(inv2);
        inventoryImg.add(inv3);
        inventoryImg.add(inv4);
        inventoryImg.add(inv5);

        ArrayList<Consumibles> inventory = personajeP.getInventario();
        ArrayList<Consumibles> newInventory =  new ArrayList<>();

        int left = inventory.size();
        for (int i = 0; i < inventoryImg.size(); i++) {
            if (left > 0) {
                Consumibles obj = inventory.get(i);
                Consumibles objjson = helper.getCons(obj.getId());
                if (objjson != null) {
                    // Comprueba que el objeto que tiene personaje es el mismo que existe en el json
                    if (obj.equals(objjson)) {
                        newInventory.add(objjson);
                        int idIcon = getResources().getIdentifier(obj.getIcono(), "drawable", getActivity().getPackageName());
                        inventoryImg.get(i).setImageResource(idIcon);
                        prepareOnClick(inventoryImg.get(i), obj.getNombre(), obj.getDescripcion());
                    } else {
                        newInventory.add(objjson);
                        int idIcon = getResources().getIdentifier(objjson.getIcono(), "drawable", getActivity().getPackageName());
                        inventoryImg.get(i).setImageResource(idIcon);
                        prepareOnClick(inventoryImg.get(i), objjson.getNombre(), objjson.getDescripcion());
                    }
                } else {
                    inventoryImg.get(i).setImageResource(R.drawable.icon_questionmark);
                }
                left--;
            } else {
                inventoryImg.get(i).setImageResource(R.drawable.icon_questionmark);
            }
        }

        personajeP.addNewInventario(newInventory);
        helper.updateCharacter(personajeP);
    }

    // Método que recorre las habilidades del personaje y lo muestra
    private void prepareSkills(View v) {
        ImageView skill1 = v.findViewById(R.id.view_Skill1);
        ImageView skill2 = v.findViewById(R.id.view_Skill2);
        ImageView skill3 = v.findViewById(R.id.view_Skill3);
        ImageView skill4 = v.findViewById(R.id.view_Skill4);
        ImageView skill5 = v.findViewById(R.id.view_Skill5);
        skillsImg = new ArrayList<>();
        skillsImg.add(skill1);
        skillsImg.add(skill2);
        skillsImg.add(skill3);
        skillsImg.add(skill4);
        skillsImg.add(skill5);

        ArrayList<Habilidades> skills = personajeP.getHabilidades();
        ArrayList<Habilidades> newSkills =  new ArrayList<>();

        int left = skills.size();
        for (int i = 0; i < skillsImg.size(); i++) {
            if (left > 0) {
                Habilidades skill = skills.get(i);
                Habilidades skilljson = helper.getSkill(skill.getId());
                if (skilljson != null) {
                    // Comprueba que la habilidad que tiene personaje es el mismo que existe en el json
                    if (skill.equals(skilljson)) {
                        newSkills.add(skilljson);
                        int idIcon = getResources().getIdentifier(skill.getIcono(), "drawable", getActivity().getPackageName());
                        skillsImg.get(i).setImageResource(idIcon);
                        prepareOnClick(skillsImg.get(i), skill.getNombre(), "Coste: "+skill.getCoste()+", Daño: "+skill.getDanio());
                    } else {
                        newSkills.add(skilljson);
                        int idIcon = getResources().getIdentifier(skilljson.getIcono(), "drawable", getActivity().getPackageName());
                        skillsImg.get(i).setImageResource(idIcon);
                        prepareOnClick(skillsImg.get(i), skilljson.getNombre(), "Coste: "+skilljson.getCoste()+", Daño: "+skilljson.getDanio());
                    }
                } else {
                    skillsImg.get(i).setImageResource(R.drawable.icon_questionmark);
                }
                left--;
            } else {
                skillsImg.get(i).setImageResource(R.drawable.icon_questionmark);
            }
        }

        personajeP.addNewHabilidades(newSkills);
        helper.updateCharacter(personajeP);
    }

    // Método que prepara el onclick listener de los imagenview
    public void prepareOnClick(ImageView img, String title, String info) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo(title, info);
            }
        });
    }

    // Método que muestra información del equipamiento
    private void showInfo(String title, String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(info)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    // Editar Personaje
    public void editPersonaje(int id) {
        CreatePersonajeFrangment fragment = new CreatePersonajeFrangment();
        Bundle bundle = new Bundle();
        bundle.putInt("personaje", id);
        fragment.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_content, fragment)
                .addToBackStack(null)
                .commit();
    }

    // Eliminar Personaje
    private void deletePersonaje(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar personaje");
        builder.setMessage("¿Está seguro de que desea eliminar este personaje?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteSkill(id);
                        Toast.makeText(getContext(), "Se eliminó el personaje",Toast.LENGTH_SHORT).show();
                        PersonajeListFragment fragment = new PersonajeListFragment();
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
