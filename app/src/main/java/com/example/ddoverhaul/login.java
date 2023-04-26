package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button blog = findViewById(R.id.blog);
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter();
            }
        });
    }
    public void enter() {
        Intent intent = new Intent(login.this, pagina1.class);
        startActivity(intent);
    }
}
