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

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText Email;
    TextInputEditText Name;
    TextInputEditText Password;
    String EmailS;
    String NameS;
    String PasswordS;
    private FirebaseFirestore db;
    CheckBox acceptTermsCheckbox ;
    Button crearb ;

    protected void aceptar() {
        acceptTermsCheckbox = findViewById(R.id.accept_terms_checkbox);
        crearb = findViewById(R.id.registerButtonRegister);
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
            Email = findViewById(R.id.emailCampoR);
            Password = findViewById(R.id.passwordCampoR);
            Name = findViewById(R.id.nombrecampo);
            crearb = findViewById(R.id.registerButtonRegister);
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
        // Realizar consulta para verificar si el nombre de usuario ya existe
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("User_Email");
        Query query = usersCollection.whereEqualTo("name", name);

        query.get().addOnCompleteListener(queryTask -> {
            if (queryTask.isSuccessful()) {
                QuerySnapshot querySnapshot = queryTask.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // El nombre de usuario ya existe
                    Toast.makeText(Register.this, "El nombre de usuario ya está en uso.", Toast.LENGTH_SHORT).show();
                    acceptTermsCheckbox.setChecked(false);
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name)
                                            .build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(updateTask -> {
                                                if (updateTask.isSuccessful()) {
                                                    saveUserToFirestore(name, email);
                                                    registro_exitoso();
                                                } else {
                                                    // Error al agregar el nombre
                                                    Toast.makeText(Register.this, "Error al guardar el nombre.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    acceptTermsCheckbox.setChecked(false);
                                    Toast.makeText(Register.this, "Error al registrarse: " + errorMessage, Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            } else {
                // Error al realizar la consulta
                Toast.makeText(Register.this, "Error al verificar el nombre de usuario.", Toast.LENGTH_SHORT).show();
                acceptTermsCheckbox.setChecked(false);
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
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        }, 1000);
    }


}

