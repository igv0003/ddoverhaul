package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ddoverhaul.habilidadList.CreateSkill;
import com.example.ddoverhaul.habilidadList.habilidadlist;
import com.example.ddoverhaul.objetoList.Main_obj;
import com.example.ddoverhaul.objetoList.lista_objetos;
import com.example.ddoverhaul.personajeList.personajelist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BaseActivity extends AppCompatActivity {

    private ImageButton blistapersonajes;
    private ImageButton blistaobjetos;
    private ImageButton bperfil;
    private ImageButton bmulti;
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
                showPopupMenu2(v);
            }
        });

        bmulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, Multijugador.class));
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

    private void showPopupMenu2 (View view){
        PopupMenu popupMenu2 = new PopupMenu(this, view);
        popupMenu2.inflate(R.menu.menu2);

        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.lista_hab:
                        gohablist();
                        return true ;
                    case R.id.lista_obj:
                        goobjlist();
                        return true ;
                    default:
                        return false;
                }
            }
        });
        popupMenu2.show();
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
                        return true ;
                    case R.id.menu_option2:
                        gocreateobjec();
                        return true ;
                    case R.id.menu_option3:
                        gocreateskill();
                        return true ;
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
        startActivity(new Intent(BaseActivity.this, Main_obj.class));
    }
    public void goobjlist(){
        startActivity(new Intent(BaseActivity.this, lista_objetos.class));
        System.out.println("segundoboton");
    }
    public void gohablist(){
        startActivity(new Intent(BaseActivity.this, habilidadlist.class));
        System.out.println("primer boton");
    }
}


