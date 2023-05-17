package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText Email;
    EditText Name;
    EditText Password;
    String EmailS;
    String NameS;
    String PasswordS;
    private FirebaseFirestore db;
    CheckBox acceptTermsCheckbox ;
    Button crearb ;

    protected void aceptar() {
        acceptTermsCheckbox = findViewById(R.id.accept_terms_checkbox);
        crearb = findViewById(R.id.bregister);

        // Deshabilita el botón al inicio
        crearb.setEnabled(false);

        acceptTermsCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
                crearb.setEnabled(isChecked);
            }
        });
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
            Email = findViewById(R.id.EmailR);
            Password = findViewById(R.id.PasswordR);
            Name = findViewById(R.id.nameR);
            crearb = findViewById(R.id.bregister);
            acceptTermsCheckbox = findViewById(R.id.accept_terms_checkbox);
            aceptar();
            crearb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    aceptar();

                        EmailS = Email.getText().toString();
                        NameS = Name.getText().toString();
                        PasswordS = Password.getText().toString();

                    if (EmailS.equals("")  ||  NameS.equals("") || PasswordS.equals("")) {

                        Toast.makeText(Register.this, "Error al registrarse: " + "No es posible crear la cuenta con alguno de los campos vacios", Toast.LENGTH_SHORT).show();

                    }else{
                        signUp(EmailS, NameS, PasswordS);

                    }
                }
            });
        }



    private void signUp(String email, String name, String password) {


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Agregar el nombre al perfil del usuario
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(updateTask -> {
                                        if (updateTask.isSuccessful()) {
                                            // Guardar usuario en Firestore
                                            saveUserToFirestore(name, email);
                                            // El nombre se ha agregado correctamente
                                            registro_exitoso();
                                        } else {
                                            // Error al agregar el nombre
                                            Toast.makeText(Register.this, "Error al guardar el nombre.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Si el registro falla, muestra un mensaje al usuario
                            String errorMessage = task.getException().getMessage(); // Obtiene el mensaje de error específico
                            Toast.makeText(Register.this, "Error al registrarse: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });

    }

    private void saveUserToFirestore(String name, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);

        db.collection("User_Email")
                .document(email)
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d("TAG", "Usuario guardado en Firestore"))
                .addOnFailureListener(e -> Log.w("TAG", "Error al guardar usuario en Firestore", e));
    }

    public void registro_exitoso() {

        Toast.makeText(Register.this, "Registro exitoso.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Navegar de vuelta a la pantalla de inicio de sesión/registro después de 4 segundos
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }


}

