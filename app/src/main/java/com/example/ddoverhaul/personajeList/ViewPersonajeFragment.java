package com.example.ddoverhaul.personajeList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personaje, container, false);

        fotopersonaje = view.findViewById(R.id.imagenPersonaje);
        nombre = view.findViewById(R.id.NombreTV);
        raza = view.findViewById(R.id.RazaTV);
        clase = view.findViewById(R.id.ClaseTV);
        nivel = view.findViewById(R.id.nivelTV);
        vida = view.findViewById(R.id.vidaTV);
        mana = view.findViewById(R.id.ManaTV);
        fuerza = view.findViewById(R.id.FuerzaTV);
        destreza = view.findViewById(R.id.DestrzaTV);
        constitucion = view.findViewById(R.id.ConstTV);
        inteligencia = view.findViewById(R.id.IntelTV);
        sabiduria = view.findViewById(R.id.SabTV);
        carisma = view.findViewById(R.id.CarisTV);
        velocidad = view.findViewById(R.id.velTV);

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


        personajeP = helper.getChar(personajeID);

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

        return view;
    }
}
