package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText Email;
    EditText NameE;
    EditText PasswordE;
    String EmailS;
    String NameS;
    String PasswordS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.email);
        PasswordE = findViewById(R.id.Password);
        Button blog = findViewById(R.id.blog);


        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailS = Email.getText().toString();
                PasswordS = PasswordE.getText().toString();
                signIn(EmailS,PasswordS);
            }
        });
    }

    public void enter() {
        Intent intent = new Intent(Login.this, pagina1.class);
        startActivity(intent);
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Inicio de sesión exitoso
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Continúa con la siguiente actividad o actualiza la interfaz de usuario
                        enter();
                    } else {
                        // Si el inicio de sesión falla, muestra un mensaje al usuario
                        Toast.makeText(Login.this, "Error al iniciar sesión.", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}