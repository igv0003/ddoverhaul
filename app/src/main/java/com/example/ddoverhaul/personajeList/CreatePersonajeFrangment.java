package com.example.ddoverhaul.personajeList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ddoverhaul.Consumibles;
import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.IconsAdapter;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.habilidadList.HabilidadListFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class CreatePersonajeFrangment extends Fragment implements IconsAdapter.OnIconClickListener{

    // Variables para la creación de personaje
    private Personaje personaje;
    private JSONHelper helper;
    private CreateAdapter objAdapter;
    private CreateAdapter skillAdapter;
    private AlertDialog alert;
    private View view;
    private ImageView iconCharacter;
    private String icon;
    private IconsAdapter adapter;
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
    private ImageView[] accesoriesImg;
    private Objeto finalAccesories[];
    private ArrayList<Objeto> currentAccesories;
    private ImageView[] inventoryImg;
    private Objeto finalConsumibles[];
    private ArrayList<Consumibles> currentCons;
    private ImageView[] skillsImg;
    private Habilidades finalSkills[];
    private ArrayList<Habilidades> currentSkills;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_create_personaje, container, false);

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
        iconCharacter = view.findViewById(R.id.imagenPersonaje);
        icon = "icon_username";

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });

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

        iconCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIcons(view);
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

            int iconID = getResources().getIdentifier(personaje.getImagen(), "drawable", getContext().getPackageName());
            icon = personaje.getImagen();
            iconCharacter.setImageResource(iconID);

        } else {
            personaje = new Personaje();
            personaje.setId(-1);
            iconCharacter.setImageResource(R.drawable.icon_username);
        }

        prepareEquipment(view);
        prepareAccesories(view);
        prepareInventory(view);
        prepareSkills(view);

        return view;
    }

    // Método que prepara y muestra el equipamiento del personaje
    public void prepareEquipment(View v) {
        viewHelmet = v.findViewById(R.id.Equipo1);
        viewArmor = v.findViewById(R.id.Equipo2);
        viewPants = v.findViewById(R.id.Equipo3);
        viewFoots = v.findViewById(R.id.Equipo4);
        viewGloves = v.findViewById(R.id.Equipo6);
        viewPrimaryWeapon = v.findViewById(R.id.Equipo5);
        viewSecondaryWeapon = v.findViewById(R.id.Equipo7);
        Equipo[] equips = helper.getEquips();

        // Prepara la cabeza
        Equipo[] helmets = new Equipo[equips.length];
        int counter = 0;
        for (Equipo e: equips) {
            if (e.getPosicion() == 0) {
                helmets[counter] = e;
                counter++;
            }
        }
        finalHelmets = Arrays.copyOf(helmets, counter);

        if (personaje.getCabeza() != null) {
            Equipo helmet = helper.getEquip(personaje.getCabeza().getId());
            int idIcon = getResources().getIdentifier(helmet.getIcono(), "drawable",getActivity().getPackageName());
            viewHelmet.setImageResource(idIcon);
            removeEquipment(viewHelmet,helmet.getNombre(),"Daño: "+helmet.getDanio()+", Armadura: "+helmet.getArmadura(), helmet.getTipo(), helmet.getId()+"");
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewHelmet.setImageResource(R.drawable.icon_questionmark);
            if (finalHelmets.length > 0) prepareShowOnClick(viewHelmet, finalHelmets);
        }

        // Prepara la armadura
        Equipo[] armors = new Equipo[equips.length];

        counter = 0;
        for (Equipo e: equips) {
            if (e.getPosicion() == 1) {
                armors[counter] = e;
                counter++;
            }
        }
        finalArmors = Arrays.copyOf(armors, counter);
        if (personaje.getPerchera() != null) {
            Equipo armor = helper.getEquip(personaje.getPerchera().getId());
            int idIcon = getResources().getIdentifier(armor.getIcono(), "drawable",getActivity().getPackageName());
            viewArmor.setImageResource(idIcon);
            removeEquipment(viewArmor,armor.getNombre(),"Daño: "+armor.getDanio()+", Armadura: "+armor.getArmadura(), armor.getTipo(), armor.getId()+"");
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewArmor.setImageResource(R.drawable.icon_questionmark);
            if (finalArmors.length > 0) prepareShowOnClick(viewArmor, finalArmors);
        }

        // Prepara los pantalones
        Equipo[] pants = new Equipo[equips.length];

        counter = 0;
        for (Equipo e: equips) {
            if (e.getPosicion() == 2) {
                pants[counter] = e;
                counter++;
            }
        }
        finalPants = Arrays.copyOf(pants, counter);
        if (personaje.getPantalones() != null) {
            Equipo pant = helper.getEquip(personaje.getPantalones().getId());
            int idIcon = getResources().getIdentifier(pant.getIcono(), "drawable",getActivity().getPackageName());
            viewPants.setImageResource(idIcon);
            removeEquipment(viewPants,pant.getNombre(),"Daño: "+pant.getDanio()+", Armadura: "+pant.getArmadura(), pant.getTipo(), pant.getId()+"");
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewPants.setImageResource(R.drawable.icon_questionmark);
            if (finalPants.length > 0) prepareShowOnClick(viewPants, finalPants);
        }

        // Prepara los pies
        Equipo[] foots = new Equipo[equips.length];

        counter = 0;
        for (Equipo e: equips) {
            if (e.getPosicion() == 3) {
                foots[counter] = e;
                counter++;
            }
        }
        finalFoots = Arrays.copyOf(foots, counter);

        if (personaje.getPies() != null) {
            Equipo foot = helper.getEquip(personaje.getPies().getId());
            int idIcon = getResources().getIdentifier(foot.getIcono(), "drawable",getActivity().getPackageName());
            viewFoots.setImageResource(idIcon);
            removeEquipment(viewFoots,foot.getNombre(),"Daño: "+foot.getDanio()+", Armadura: "+foot.getArmadura(), foot.getTipo(), foot.getId()+"");
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewFoots.setImageResource(R.drawable.icon_questionmark);
            if (finalFoots.length > 0) prepareShowOnClick(viewFoots, finalFoots);
        }

        // Prepara los guantes
        Equipo[] gloves = new Equipo[equips.length];

        counter = 0;
        for (Equipo e: equips) {
            if (e.getPosicion() == 4) {
                gloves[counter] = e;
                counter++;
            }
        }
        finalGloves = Arrays.copyOf(gloves, counter);

        if (personaje.getGuantes() != null) {
            Equipo glove = helper.getEquip(personaje.getGuantes().getId());
            int idIcon = getResources().getIdentifier(glove.getIcono(), "drawable",getActivity().getPackageName());
            viewGloves.setImageResource(idIcon);
            removeEquipment(viewGloves,glove.getNombre(),"Daño: "+glove.getDanio()+", Armadura: "+glove.getArmadura(), glove.getTipo(), glove.getId()+"");
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewGloves.setImageResource(R.drawable.icon_questionmark);
            if (finalGloves.length > 0) prepareShowOnClick(viewGloves, finalGloves);
        }

        // Prepara la arma principal
        Equipo[] weapons1 = new Equipo[equips.length];

        counter = 0;
        for (Equipo e: equips) {
            if (e.getPosicion() == 5) {
                weapons1[counter] = e;
                counter++;
            }
        }
        finalWeps1 = Arrays.copyOf(weapons1, counter);

        if (personaje.getArma() != null) {
            Equipo weapon = helper.getEquip(personaje.getArma().getId());
            int idIcon = getResources().getIdentifier(weapon.getIcono(), "drawable",getActivity().getPackageName());
            viewPrimaryWeapon.setImageResource(idIcon);
            removeEquipment(viewPrimaryWeapon,weapon.getNombre(),"Daño: "+weapon.getDanio()+", Armadura: "+weapon.getArmadura(), weapon.getTipo(), weapon.getId()+"");
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewPrimaryWeapon.setImageResource(R.drawable.icon_questionmark);
            if (finalWeps1.length > 0) prepareShowOnClick(viewPrimaryWeapon, finalWeps1);
        }

        // Prepara la arma secundaria
        Equipo[] weapons2 = new Equipo[equips.length];

        counter = 0;
        for (Equipo e: equips) {
            if (e.getPosicion() == 6) {
                weapons2[counter] = e;
                counter++;
            }
        }
        finalWeps2 = Arrays.copyOf(weapons2, counter);

        if (personaje.getArma_sec() != null) {
            Equipo weapon = helper.getEquip(personaje.getArma_sec().getId());
            int idIcon = getResources().getIdentifier(weapon.getIcono(), "drawable",getActivity().getPackageName());
            viewSecondaryWeapon.setImageResource(idIcon);
            removeEquipment(viewSecondaryWeapon,weapon.getNombre(),"Daño: "+weapon.getDanio()+", Armadura: "+weapon.getArmadura(), weapon.getTipo(), weapon.getId()+"");
        } else {
            // Si no tiene , prepara un array de equipo solamente de eso
            viewSecondaryWeapon.setImageResource(R.drawable.icon_questionmark);
            if (finalWeps2.length > 0) prepareShowOnClick(viewSecondaryWeapon, finalWeps2);
        }

    }

    // Método que prepara y muestra los accesorios del personaje
    public void prepareAccesories(View v) {
        accesoriesImg = new ImageView[2];
        accesoriesImg[0] = v.findViewById(R.id.Accesorios1);
        accesoriesImg[1] = v.findViewById(R.id.Accesorios2);

        finalAccesories = helper.getObjects();

        if (currentAccesories == null) currentAccesories = personaje.getAccesorios();

        int left = currentAccesories.size();
        for (int i = 0; i < accesoriesImg.length; i++) {
            if (left > 0) {
                Objeto acc = currentAccesories.get(i);
                int idIcon = getResources().getIdentifier(acc.getIcono(),"drawable", getContext().getPackageName());
                accesoriesImg[i].setImageResource(idIcon);
                removeEquipment(accesoriesImg[i],acc.getNombre(), acc.getDescripcion(), acc.getTipo(), acc.getId()+"");
                left--;
            } else {
                accesoriesImg[i].setImageResource(R.drawable.icon_questionmark);
                if (finalAccesories.length > 0) prepareShowOnClick(accesoriesImg[i], finalAccesories);
            }
        }
    }

    // Método que prepara y muestra el inventario del personaje
    public void prepareInventory(View v) {
        inventoryImg = new ImageView[5];
        inventoryImg[0] = v.findViewById(R.id.inventario1);
        inventoryImg[1] = v.findViewById(R.id.inventario2);
        inventoryImg[2] = v.findViewById(R.id.inventario3);
        inventoryImg[3] = v.findViewById(R.id.inventario4);
        inventoryImg[4] = v.findViewById(R.id.inventario5);

        finalConsumibles = helper.getAllCons();

        if (currentCons == null) {currentCons = personaje.getInventario();}

        int left = currentCons.size();
        for (int i = 0; i < inventoryImg.length; i++) {
            if (left > 0) {
                Consumibles cons = currentCons.get(i);
                int idIcon = getResources().getIdentifier(cons.getIcono(),"drawable", getContext().getPackageName());
                inventoryImg[i].setImageResource(idIcon);
                removeEquipment(inventoryImg[i],cons.getNombre(), cons.getDescripcion(), cons.getTipo(), cons.getId()+"");
                left--;
            } else {
                inventoryImg[i].setImageResource(R.drawable.icon_questionmark);
                if (finalConsumibles.length > 0) prepareShowOnClick(inventoryImg[i], finalConsumibles);
            }
        }
    }

    // Método que prepara y muestra las habilidades del personaje
    public void prepareSkills(View v) {
        skillsImg = new ImageView[5];
        skillsImg[0] = v.findViewById(R.id.habilidad1);
        skillsImg[1] = v.findViewById(R.id.habilidad2);
        skillsImg[2] = v.findViewById(R.id.habilidad3);
        skillsImg[3] = v.findViewById(R.id.habilidad4);
        skillsImg[4] = v.findViewById(R.id.habilidad5);

        finalSkills = helper.getAllSkills();

        if (currentSkills == null) currentSkills = personaje.getHabilidades();

        int left = currentSkills.size();
        for (int i = 0; i < skillsImg.length; i++) {
            if (left > 0) {
                Habilidades skill = currentSkills.get(i);
                int idIcon = getResources().getIdentifier(skill.getIcono(),"drawable", getContext().getPackageName());
                skillsImg[i].setImageResource(idIcon);
                removeSkill(skillsImg[i],skill.getNombre(), "Coste: "+skill.getDanio()+", Daño: "+skill.getCoste(), skill.getId()+"");
                left--;
            } else {
                skillsImg[i].setImageResource(R.drawable.icon_questionmark);
                if (finalSkills.length > 0) prepareSkillOnClick(skillsImg[i], finalSkills);
            }
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
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
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
                                    case "Otro":
                                        Object acc = helper.getObject(Integer.parseInt(id));

                                        if (currentAccesories.contains(acc)) currentAccesories.remove(acc);
                                        prepareAccesories(view);

                                        break;
                                    case "Consumible":
                                        Consumibles cons = helper.getCons(Integer.parseInt(id));

                                        if (currentCons.contains(cons)) currentCons.remove(cons);
                                        prepareInventory(view);
                                    default:
                                        break;
                                }
                            }
                        }).show();
            }
        });
    }


    public void showObject(Objeto[] objs, View v) {
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
                                removeEquipment(viewHelmet,e.getNombre(),"Daño: "+e.getDanio()+", Armadura: "+e.getArmadura(), e.getTipo(), e.getId()+"");
                                break;
                            case 1:
                                personaje.setPerchera(e);
                                viewArmor.setImageResource(iconID);
                                removeEquipment(viewArmor,e.getNombre(),"Daño: "+e.getDanio()+", Armadura: "+e.getArmadura(), e.getTipo(), e.getId()+"");
                                break;
                            case 2:
                                personaje.setPantalones(e);
                                viewPants.setImageResource(iconID);
                                removeEquipment(viewPants,e.getNombre(),"Daño: "+e.getDanio()+", Armadura: "+e.getArmadura(), e.getTipo(), e.getId()+"");
                                break;
                            case 3:
                                personaje.setPies(e);
                                viewFoots.setImageResource(iconID);
                                removeEquipment(viewFoots,e.getNombre(),"Daño: "+e.getDanio()+", Armadura: "+e.getArmadura(), e.getTipo(), e.getId()+"");
                                break;
                            case 4:
                                personaje.setGuantes(e);
                                viewGloves.setImageResource(iconID);
                                removeEquipment(viewGloves,e.getNombre(),"Daño: "+e.getDanio()+", Armadura: "+e.getArmadura(), e.getTipo(), e.getId()+"");
                                break;
                            case 5:
                                personaje.setArma(e);
                                viewPrimaryWeapon.setImageResource(iconID);
                                removeEquipment(viewPrimaryWeapon,e.getNombre(),"Daño: "+e.getDanio()+", Armadura: "+e.getArmadura(), e.getTipo(), e.getId()+"");
                                break;
                            case 6:
                                personaje.setArma_sec(e);
                                viewSecondaryWeapon.setImageResource(iconID);
                                removeEquipment(viewSecondaryWeapon,e.getNombre(),"Daño: "+e.getDanio()+", Armadura: "+e.getArmadura(), e.getTipo(), e.getId()+"");
                                break;
                            default:
                                break;
                        }
                        break;
                    case "Otro":
                        Objeto acc = helper.getObject(Integer.parseInt(id));

                        if (!currentAccesories.contains(acc)) currentAccesories.add(acc);
                        prepareAccesories(view);

                        break;
                    case "Consumible":
                        Consumibles cons = helper.getCons(Integer.parseInt(id));

                        if (!currentCons.contains(cons)) currentCons.add(cons);
                        prepareInventory(view);
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

    // Método que prepara el onclick listener de los imagenview
    public void prepareSkillOnClick(ImageView img, Habilidades[] skill) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSkill(skill, v);
            }
        });
    }

    public void showSkill(Habilidades[] skills, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Habilidad a equipar")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        skillAdapter = new CreateAdapter(skills,getContext(), false);

        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.addItemDecoration(new SpacingItemDecoration(15));
        recyclerView.setAdapter(skillAdapter);
        skillAdapter.setOnSkillClickListener(new CreateAdapter.OnSkillClickListener() {
            @Override
            public void onClick(int position, String id) {
                Habilidades skill = helper.getSkill(Integer.parseInt(id));

                if (!currentSkills.contains(skill)) currentSkills.add(skill);
                prepareSkills(view);
                alert.dismiss();
            }
        });

        // Configura el diálogo para mostrar el RecyclerView
        builder.setView(recyclerView);

        // Muestra el diálogo
        alert = builder.show();

    }

    public void removeSkill(ImageView img, String title, String info, String id) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(title);
                builder.setMessage(info)
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Quitar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Habilidades skill = helper.getSkill(Integer.parseInt(id));

                                for (int i = 0; i < currentSkills.size(); i++) {
                                    if (skill.getId() == currentSkills.get(i).getId()) {
                                        currentSkills.remove(i);
                                        prepareSkills(view);
                                        i = currentSkills.size();
                                    }
                                }

                            }
                        }).show();
            }
        });
    }

    public void showIcons(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Selecciona un icono");

        // Guarda los iconos a mostrar
        final int[] iconos = {R.drawable.icon_alien, R.drawable.icon_elf, R.drawable.icon_furry, R.drawable.icon_goblin,R.drawable.icon_knight, R.drawable.icon_mummy, R.drawable.icon_profile_elf, R.drawable.icon_profile_vikyngo};

        // Adaptador personalizado para el RecyclerView
        adapter = new IconsAdapter(iconos,this);

        // Configura el RecyclerView
        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 4)); // 3 es el número de columnas que deseas
        recyclerView.setAdapter(adapter);

        // Configura el diálogo para mostrar el RecyclerView
        builder.setView(recyclerView);

        // Muestra el diálogo
        alert = builder.show();
    }

    @Override
    public void onIconClick(int iconID) {
        iconCharacter.setImageResource(iconID);
        icon = getResources().getResourceEntryName(iconID);
        if (alert != null) {
            alert.dismiss();
        }

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
        personaje.setImagen(icon);
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
        personaje.addNewAccesories(currentAccesories);
        personaje.addNewHabilidades(currentSkills);

        ArrayList<Consumibles> finalCons = new ArrayList<>();
        for (Consumibles cons: currentCons) {
            finalCons.add(cons);
        }

        personaje.addNewInventario(finalCons);

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