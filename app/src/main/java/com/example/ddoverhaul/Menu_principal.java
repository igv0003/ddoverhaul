package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Menu_principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        FloatingActionButton floatingActionButton = findViewById(R.id.bcentral);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    public void goprofile() {

        Intent intent4 = new Intent(Menu_principal.this, Perfil.class);
        startActivity(intent4);

    }

    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.blista_personajes:
                Intent intent = new Intent(Menu_principal.this, lista_personajes.class);
                startActivity(intent);
                break;
            case R.id.blista_objetos:
                Intent intent2 = new Intent(Menu_principal.this, lista_objetos.class);
                startActivity(intent2);
                break;
            case R.id.bmultijugador:
                Intent intent3 = new Intent(Menu_principal.this, Multijugador.class);
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
        PopupMenu popupMenu = new PopupMenu(Menu_principal.this, view);
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
    }


}
