package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentTransaction;

import com.example.ddoverhaul.habilidadList.CreateSkillFragment;
import com.example.ddoverhaul.habilidadList.HabilidadListFragment;
import com.example.ddoverhaul.objetoList.ListaObjetosFragment;
import com.example.ddoverhaul.personajeList.PersonajeListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity_Multi extends AppCompatActivity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void setContentView(int layoutResID) {
            LinearLayout fullLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_container_multi, null);
            FrameLayout activityContainer = fullLayout.findViewById(R.id.activity_content_multi);
            getLayoutInflater().inflate(layoutResID, activityContainer, true);
            super.setContentView(fullLayout);

            BottomNavigationView bottomNavigationView = findViewById(R.id.footer_multiL);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.personajes:
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.activity_content, new PersonajeListFragment());
                            transaction.commit();
                            return true;
                        case R.id.equipamieto:
                            //goobjlist();
                            return true;
                        case R.id.crear:

                            return true;
                        case R.id.multijugador:
                            startActivity(new Intent(com.example.ddoverhaul.BaseActivity_Multi.this, MultijugadorFragment.class));
                            return true;
                        case R.id.perfil:

                            return true;
                        default:
                            return false;
                    }
                }
            });

        }







        /*public void goperfil() {
            Intent intent4 = new Intent(this, Perfil.class);
            startActivity(intent4);
        }
        public void gocreateskill(){
            startActivity(new Intent(com.example.ddoverhaul.BaseActivity_Multi.this, CreateSkill.class));
        }

        public void gocreateperso(){
            startActivity(new Intent(BaseActivity_Multi.this, personajelist.class));
        }
        public void gocreateobjec(){
            //startActivity(new Intent(BaseActivity.this, .class));
        }
        public void goobjlist(){
            startActivity(new Intent(com.example.ddoverhaul.BaseActivity_Multi.this, lista_objetos.class));
        }
        public void gohablist(){
            startActivity(new Intent(com.example.ddoverhaul.BaseActivity_Multi.this, habilidadlist.class));
        }*/
    }




