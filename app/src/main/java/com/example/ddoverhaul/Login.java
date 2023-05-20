package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private TextInputLayout emailCampo;

    private TextInputLayout passwordCampo;

    private FirebaseAuth mAuth;
    String EmailS;
    String PasswordS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        mAuth = FirebaseAuth.getInstance();

        emailCampo = findViewById(R.id.emailCampo);
        passwordCampo = findViewById(R.id.passwordCampo);
        Button blog = findViewById(R.id.loginButton);
        Button bregister = findViewById(R.id.registerButton);

        bregister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Login.this, Register.class );
                startActivity(register);
            }
        });
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailS = emailCampo.getEditText().getText().toString().trim();
                PasswordS = passwordCampo.getEditText().getText().toString().trim();
                signIn(EmailS,PasswordS);
            }
        });
    }

    public void enter() {
        Intent intent = new Intent(Login.this, Menu_principal.class);
        startActivity(intent);
    }

    private void signIn(String email, String password) {
        if (email.contains("@")) {
            // Inicio de sesión utilizando correo electrónico
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
        } else {
            // Inicio de sesión utilizando nombre
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference userRef = db.collection("User_Email");

            userRef.whereEqualTo("name", email)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {
                                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                String userEmail = documentSnapshot.getString("email");

                                // Iniciar sesión utilizando el correo electrónico obtenido
                                mAuth.signInWithEmailAndPassword(userEmail, password)
                                        .addOnCompleteListener(authTask -> {
                                            if (authTask.isSuccessful()) {

                                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                                Log.d("LoginActivity", "Inicio de sesión exitoso: " + currentUser.getEmail());
                                                enter();
                                            } else {
                                                // Ocurrió un error durante el inicio de sesión
                                                try {
                                                    Exception exception = authTask.getException();
                                                    String errorMessage = exception.getMessage();
                                                    Log.e("LoginActivity", "Error al iniciar sesión: " + errorMessage);
                                                    Toast.makeText(Login.this, "Error al iniciar sesión: " + errorMessage, Toast.LENGTH_SHORT).show();
                                                }
                                                catch (Exception e){
                                                    Toast.makeText(Login.this, "Error al iniciar sesión: " + e, Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                            } else {
                                // No se encontró el nombre en la base de datos
                                Toast.makeText(Login.this, "No se encontró el nombre en la base de datos", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Ocurrió un error durante la consulta
                            Exception exception = task.getException();
                            String errorMessage = exception.getMessage();
                            Log.e("LoginActivity", "Error en la consulta: " + errorMessage);
                            Toast.makeText(Login.this, "Error en la consulta a la base de datos", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}