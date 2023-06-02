package com.example.ddoverhaul.personajeList;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.habilidadList.ViewSkillFragment;

public class PersonajeListFragment extends Fragment {
    // Variables necesarias para mostrar la lista
    private RecyclerView recyclerView;
    private PersonajeAdapter adapter;
    private JSONHelper helper;
    private Personaje[] characters;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personajelist, container, false);

        helper = new JSONHelper(getContext());

        // Se prepara el recyclerView para mostrar la lista
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpacingItemDecoration(35));

        // Se obtiene el array a mostrar en el recyclerView
        characters = helper.getChars();
        // Se crea el PersonajeAdapter con el array obtenido
        adapter = new PersonajeAdapter(characters, getContext());
        // Se vincula el recyclerView con el adaptador
        recyclerView.setAdapter(adapter);

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });

        // Se añade el evento OnClick, para poder ver un item en concreto. Se le pasa la id para el siguiente Activity
        adapter.setOnClickListener(new PersonajeAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String id) {
                ViewPersonajeFragment fragment = new ViewPersonajeFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("personaje", Integer.parseInt(id));
                fragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }
}
