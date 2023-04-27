package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // El inicio de sesión se realizó exitosamente
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d("LoginActivity", "Inicio de sesión exitoso: " + user.getEmail());
                        enter();
                    } else {
                        // Ocurrió un error durante el inicio de sesión
                        Exception exception = task.getException();
                        String errorMessage = exception.getMessage();
                        Log.e("LoginActivity", "Error al iniciar sesión: " + errorMessage);
                        Toast.makeText(Login.this, "Error al iniciar sesión: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}