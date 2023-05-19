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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ddoverhaul.habilidadList.CreateSkill;
import com.example.ddoverhaul.habilidadList.habilidadlist;
import com.example.ddoverhaul.objetoList.lista_objetos;
import com.example.ddoverhaul.personajeList.personajelist;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BaseActivity extends AppCompatActivity {
    ImageButton bajustes;
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
        bajustes = findViewById(R.id.ajustesBoton);
        bajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opciones();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.footerNavegation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.personajes:
                        startActivity(new Intent(BaseActivity.this, personajelist.class));
                        return true;
                    case R.id.equipamieto:
                        showPopupMenu2(bottomNavigationView);
                        return true;
                    case R.id.crear:
                        showPopupMenu(bottomNavigationView );
                        return true;
                    case R.id.multijugador:
                        startActivity(new Intent(BaseActivity.this, Multijugador.class));
                        return true;
                    case R.id.perfil:
                        goprofile();
                        return true;
                    default:
                        return false;
                }
            }
        });



        bajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opciones();
            }
        });

    }

    private void showPopupMenu2(View view) {
        PopupMenu popupMenu2 = new PopupMenu(this, view);
        popupMenu2.inflate(R.menu.menu2);

        popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.lista_hab:
                        gohablist();
                        return true;
                    case R.id.lista_obj:
                        goobjlist();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu2.show();
    }


    private void opciones() {
        setContentView(R.layout.activity_ajustes);

        Button logoutButton = findViewById(R.id.logout_button);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout(){
        startActivity(new Intent(BaseActivity.this, Login.class));
    }






    private void showPopupMenu (View view){
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.floating_menu);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.crearPERS:
                        gocreateperso();
                        return true ;
                    case R.id.crearOBJ:
                        gocreateobjec();
                        return true ;
                    case R.id.crearHAB:
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
        //startActivity(new Intent(BaseActivity.this, .class));
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


