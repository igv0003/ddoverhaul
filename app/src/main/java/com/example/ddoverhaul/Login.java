package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText Email;
    EditText PasswordE;
    String EmailS;
    String PasswordS;
    CheckBox mc ;
    Button rgb;
    Button blog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.emailCampo);
        PasswordE = findViewById(R.id.passwordCampo);
        blog = findViewById(R.id.loginButton);
        //Button oc = findViewById(R.id.);
        mc = findViewById(R.id.mostrarP);
        rgb = findViewById(R.id.registerButton);
        mostrarcont();
        /*oc.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mandarCorreo();
            }
        });*/

        mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarcont();            }
        });

        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailS = Email.getText().toString();
                PasswordS = PasswordE.getText().toString();
                signIn(EmailS,PasswordS);
            }
        });
        rgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegister();
            }
        });
    }
    public void mostrarcont(){

        mc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Para mostrar la contraseña
                    PasswordE.setTransformationMethod(null);
                } else {
                    // Para ocultar la contraseña
                    PasswordE.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void enter() {
        Intent intent = new Intent(Login.this, Menu_principal.class);
        startActivity(intent);
    }
    /*public void mandarCorreo(){
    Button forgotPassword = findViewById(R.id.bolvidoC);
    forgotPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailAddress = Email.getText().toString();
            if (emailAddress.equals("")) {
                Toast.makeText(Login.this, "Por favor, introduce tu correo electrónico", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Correo de restablecimiento enviado.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "Error al enviar correo de restablecimiento.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    });

    }*/

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

    private void goRegister(){
        startActivity(new Intent(Login.this, Register.class));
    }

}