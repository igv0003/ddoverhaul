package com.example.ddoverhaul;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ddoverhaul.habilidadList.CreateSkill;
import com.example.ddoverhaul.habilidadList.habilidadlist;
import com.example.ddoverhaul.objetoList.lista_objetos;
import com.example.ddoverhaul.personajeList.personajelist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BaseActivity extends AppCompatActivity {

    private Button blistapersonajes;
    private Button blistaobjetos;
    private Button bperfil;
    private Button bmulti;
    private FloatingActionButton bcentral;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout fullLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_container, null);
        FrameLayout activityContainer = fullLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullLayout);

    blistapersonajes = findViewById(R.id.blista_personajes);
        blistaobjetos = findViewById(R.id.blista_objetos);
        bmulti = findViewById(R.id.bmultijugador);
        bperfil = findViewById(R.id.bperfil);
        bcentral = findViewById(R.id.bcentral);

        blistapersonajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, personajelist.class));
            }
        });

        blistaobjetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, lista_objetos.class));
            }
        });

        bmulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, habilidadlist.class));
            }
        });

        bperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goprofile();
            }
        });
        bcentral.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
    }
    private void showPopupMenu (View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.floating_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_option1:
                        gocreateperso();
                    case R.id.menu_option2:
                        gocreateobjec();

                    case R.id.menu_option3:
                        gocreateskill();
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public void goprofile() {
        Intent intent4 = new Intent(this, Perfil.class);
        startActivity(intent4);
    }
    public void gocreateskill(){
        startActivity(new Intent(BaseActivity.this, CreateSkill.class));
    }
    public void gocreateperso(){
        //startActivity(new Intent(BaseActivity.this, .class));
    }
    public void gocreateobjec(){
        //startActivity(new Intent(BaseActivity.this, .class));
    }
}


