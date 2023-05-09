package com.example.ddoverhaul;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

/*
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Footer extends LinearLayout {


    public Footer(Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footer_layout);

        Button button = findViewById(R.id.blista_personajes);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFooterButtonClick(view);
            }
        });

        FloatingActionButton floatingActionButton = findViewById(R.id.bcentral);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    public void goprofile() {
        Intent intent4 = new Intent(this, Perfil.class);
        startActivity(intent4);
    }

    public void onFooterButtonClick(View view) {
        switch (view.getId()) {
            case R.id.blista_personajes:
                Intent intent = new Intent(this, lista_personajes.class);
                startActivity(intent);
                break;
            case R.id.blista_objetos:
                Intent intent2 = new Intent(this, lista_objetos.class);
                startActivity(intent2);
                break;
            case R.id.bmultijugador:
                Intent intent3 = new Intent(this, Multijugador.class);
                startActivity(intent3);
                break;
            case R.id.bperfil:
                goprofile();
                break;
            case R.id.bcentral:
                showPopupMenu(view);
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.floating_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_option1:
                        // Realiza la acción correspondiente a la opción 1
                        return true;
                    case R.id.menu_option2:
                        // Realiza la acción correspondiente a la opción 2
                        return true;
                    case R.id.menu_option3:
                        // Realiza la acción correspondiente a la opción 3
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }*/

public class Footer extends LinearLayout {

    private Button blistapersonajes;
    private Button blistaobjetos;
    private Button bperfil;
    private Button bmulti;

    public Footer(Context context) {
        super(context);
        init(context);
    }

    public Footer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.footer_layout, this, true);
        blistapersonajes = findViewById(R.id.blista_personajes);
        blistaobjetos = findViewById(R.id.blista_objetos);
        bmulti = findViewById(R.id.bmultijugador);
        bperfil = findViewById(R.id.bperfil);


    }


}

