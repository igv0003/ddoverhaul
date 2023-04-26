package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_register);


        Button register = findViewById(R.id.registerbut);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        Button blog = findViewById(R.id.loginbut);
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void login() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
    public void register(){
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}
