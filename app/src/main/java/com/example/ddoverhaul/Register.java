package com.example.ddoverhaul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
            Email = findViewById(R.id.EmailR);
            Password = findViewById(R.id.PasswordR);
            Name = findViewById(R.id.nameR);

            Button blog = findViewById(R.id.bregister);

            blog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmailS = Email.getText().toString();
                    NameS = Name.getText().toString();
                    PasswordS = Name.getText().toString();

                    signUp(EmailS,NameS,PasswordS);

                }
            });
        }
        public void enter() {
            Intent intent = new Intent(com.example.ddoverhaul.Register.this, pagina1.class);
            startActivity(intent);
        }

    private void signUp(String email, String name, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registro exitoso
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
                                        enter();
                                    } else {
                                        // Error al agregar el nombre
                                        Toast.makeText(Register.this, "Error al guardar el nombre.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        // Si el registro falla, muestra un mensaje al usuario
                        Toast.makeText(Register.this, "Error al registrarse.", Toast.LENGTH_SHORT).show();
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


}

