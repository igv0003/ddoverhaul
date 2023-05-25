package com.example.ddoverhaul;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.ddoverhaul.navigation.Normal.BaseActivity;

public class PerfilFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perfil, container, false);

        Button buttonOpenPagina1 = view.findViewById(R.id.bv);
        buttonOpenPagina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gomenu();
            }
        });

        return view;
    }
}

