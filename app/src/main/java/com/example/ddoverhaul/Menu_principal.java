package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.ddoverhaul.habilidadList.habilidadlist;
import com.example.ddoverhaul.objetoList.lista_objetos;
import com.example.ddoverhaul.personajeList.personajelist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Menu_principal extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Footer footer = new Footer(this);
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

    public void golista_pers(){
        Intent intent = new Intent(this, personajelist.class);
        startActivity(intent);
    }

    public void golista_obj(){
        Intent intent2 = new Intent(this, lista_objetos.class);
        startActivity(intent2);
    }

    public void gomulti(){
        Intent intent3 = new Intent(this, habilidadlist.class);
        startActivity(intent3);
    }

    public void onFooterButtonClick(View view) {
        switch (view.getId()) {
            case R.id.blista_personajes:
                golista_pers();
                break;
            case R.id.blista_objetos:
                golista_obj();
                break;
            case R.id.bmultijugador:
                gomulti();
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
        popupMenu.inflate(R.menu.option_footer);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_option1:
                        return true;
                    case R.id.menu_option2:
                        return true;
                    case R.id.menu_option3:
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }
}
