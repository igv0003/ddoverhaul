package com.example.ddoverhaul.habilidadList;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddoverhaul.Habilidades;
import com.example.ddoverhaul.JSONHelper;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.personajeList.SpacingItemDecoration;

public class HabilidadListFragment extends Fragment {
    // Variables necesarias para mostrar la lista
    private RecyclerView recyclerView;
    private HabilidadAdapter adapter;
    private JSONHelper helper;
    private Habilidades[] skills;

    @SuppressLint("MissingInflateId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_habilidadlist, container, false);

        helper = new JSONHelper(getActivity());
        // Se prepara el recyclerView para mostrar la lista
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SpacingItemDecoration(35));

        // Se obtiene el array a mostrar en el recyclerView
        skills = helper.getAllSkills();
        // Se crea el HabilidadAdapter con el array obtenido
        adapter = new HabilidadAdapter(skills, getContext());
        // Se vincula el recyclerView con el adaptador
        recyclerView.setAdapter(adapter);

        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });

        // Se añade el evento OnClick, para poder ver un item en concreto. Se le pasa la id para el siguiente Activity
        adapter.setOnClickListener(new HabilidadAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String id) {
                ViewSkillFragment fragment = new ViewSkillFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("habilidad", Integer.parseInt(id));
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
