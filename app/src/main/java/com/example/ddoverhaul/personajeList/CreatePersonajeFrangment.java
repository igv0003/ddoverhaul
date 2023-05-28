package com.example.ddoverhaul.personajeList;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.habilidadList.HabilidadListFragment;

public class CreatePersonajeFrangment extends Fragment {

    // Variables para la creación de personaje
    private Personaje personaje;
    private JSONHelper helper;
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

        return view;
    }

    // Método que recoge los valores introducidos y guarda la habilidad en el JSON
    public void Save(View v) {
        // Se guardan todos los valores dentro de la skill para guardarla
        String name = editName.getText().toString();
        String nivel = editNivel.getText().toString();
        String vida = editVida.getText().toString();
        String mana = editName.getText().toString();
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