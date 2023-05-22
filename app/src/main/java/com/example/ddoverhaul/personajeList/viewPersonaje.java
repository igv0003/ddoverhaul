package com.example.ddoverhaul.personajeList;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;

public class viewPersonaje extends BaseActivity {
    // Variables necesarias
    private Personaje personajeP;
    private JSONHelper helper;
    ImageView fotopersonaje;
    TextView nombre;
    TextView nivel;
    TextView mana;
    TextView vida;
    TextView raza;
    TextView clase;
    TextView fuerza;
    TextView destreza;
    TextView constitucion;
    TextView inteligencia;
    TextView sabiduria;
    TextView carisma;
    TextView velocidad;
    ImageView Equipo1;
    ImageView Equipo2;
    ImageView Equipo3;
    ImageView Equipo4;
    ImageView Equipo5;
    ImageView Equipo6;
    ImageView Equipo7;
    ImageView Equipo8;
    ImageView Accesorio1;
    ImageView Accesorio2;
    ImageView Inventario1;
    ImageView Inventario2;
    ImageView Inventario3;
    ImageView Inventario4;
    ImageView Inventario5;
    ImageView Habilidad1;
    ImageView Habilidad2;
    ImageView Habilidad3;
    ImageView Habilidad4;
    ImageView Habilidad5;

    TextView commentView;
    LinearLayout probLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaje);
        fotopersonaje = findViewById(R.id.imagenPersonaje);
        nombre = findViewById(R.id.NombreTV);
        raza = findViewById(R.id.RazaTV);
        clase = findViewById(R.id.ClaseTV);
        nivel = findViewById(R.id.nivelTV);
        vida = findViewById(R.id.vidaTV);
        mana = findViewById(R.id.ManaTV);
        fuerza = findViewById(R.id.FuerzaTV);
        destreza = findViewById(R.id.DestrzaTV);
        constitucion = findViewById(R.id.ConstTV);
        inteligencia = findViewById(R.id.IntelTV);
        sabiduria = findViewById(R.id.SabTV);
        carisma = findViewById(R.id.CarisTV);
        velocidad = findViewById(R.id.velTV);
        Equipo1 = findViewById(R.id.Equipo1);
        Equipo2 = findViewById(R.id.Equipo2);
        Equipo3 = findViewById(R.id.Equipo3);
        Equipo4 = findViewById(R.id.Equipo4);
        Equipo5 = findViewById(R.id.Equipo5);
        Equipo6 = findViewById(R.id.Equipo6);
        Equipo7 = findViewById(R.id.Equipo7);
        Equipo8 = findViewById(R.id.Equipo8);
        Accesorio1 = findViewById(R.id.Accesorios1);
        Accesorio2 =findViewById(R.id.Accesorios2);
        Inventario1 = findViewById(R.id.inventario1);
        Inventario2 = findViewById(R.id.inventario2);
        Inventario3 = findViewById(R.id.inventario3);
        Inventario4 = findViewById(R.id.inventario4);
        Inventario5 = findViewById(R.id.inventario5);
        Habilidad1 = findViewById(R.id.habilidad1);
        Habilidad2 = findViewById(R.id.habilidad2);
        Habilidad3 = findViewById(R.id.habilidad3);
        Habilidad4 = findViewById(R.id.habilidad4);
        Habilidad5 = findViewById(R.id.habilidad5);
        helper = new JSONHelper(getBaseContext());
        String personajeString = getIntent().getStringExtra("personaje");
        int personajeID = -1;
        try {
            personajeID = Integer.parseInt(personajeString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        personajeP = helper.getChar(personajeID);
        System.out.println(personajeP.getNombre());

        //nameView.setText(skill.getNombre());
        nombre.setText(personajeP.getNombre());

        nivel.setText(personajeP.getNivel()+"");
        raza.setText(personajeP.getRaza());
        clase.setText(personajeP.getClase());
        vida.setText(personajeP.getVida()+"");
        mana.setText(personajeP.getMana()+"");
        fuerza.setText(personajeP.getFuerza()+"");
        destreza.setText(personajeP.getDestreza()+"");
        constitucion.setText(personajeP.getConstitucion()+"");
        inteligencia.setText(personajeP.getInteligencia()+"");
        sabiduria.setText(personajeP.getSabiduria()+"");
        carisma.setText(personajeP.getCarisma()+"");
        velocidad.setText(personajeP.getVelocidad()+"");
        //Equipo1



    }


}
