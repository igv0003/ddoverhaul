package com.example.ddoverhaul;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ddoverhaul.navigation.Normal.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.color.utilities.Contrast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PerfilFragment extends Fragment {
    private String nombreS;
    private String correoS;
    private AlertDialog alert;
    private String contraNS;
    private String contraNSC;
    private EditText contraN;
    private EditText contraNconfirm;
    private Context context;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_perfil, container, false);
        TextView nombre = view.findViewById(R.id.NombreP);
        TextView correo = view.findViewById(R.id.CorreoP);
        TextView cambiarcontr = view.findViewById(R.id.CambiarContrasena);
        TextView cambiarcorreo = view.findViewById(R.id.CambiarCorreo);
        TextView cambiarusuario = view.findViewById(R.id.CambiarUsuario);
        TextView EliminarCuenta = view.findViewById(R.id.Eliminarcuenta);
        TextView cerrarsesion = view.findViewById(R.id.CerraSesion);
        db = FirebaseFirestore.getInstance();
        nombre.setText(ponernombre());
        correo.setText(ponercorreo());
        cambiarcontr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog();

            }
        });


        return view;
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Cambiar Contraseña");

        // Inflar el diseño del diálogo personalizado
        View view = LayoutInflater.from(getContext()).inflate(R.layout.cambio_cntrs, null);
        builder.setView(view);
            builder.setPositiveButton("Cambiar", (dialog, which) -> {
                contraN = view.findViewById(R.id.contrasenueva);
                contraNconfirm = view.findViewById(R.id.confirmcontra);
                contraNS = contraN.getText().toString();
                contraNSC = contraNconfirm.getText().toString();
                String newPassword = contraNS;
                if (contraNSC.equals(contraNS)) {
                    if (!TextUtils.isEmpty(newPassword)) {
                        cambiarcontrasena(newPassword);
                    } else {
                        Toast.makeText(getContext(), "Ingrese una nueva contraseña", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Cancelar", (dialog, which) -> {
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }


    public String  ponernombre(){
        String correo = ponercorreo();
        DocumentReference docRef = db.document("User_Email/" + correo);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists()) {
                    nombreS = documentSnapshot.getString("name");
                    // Actualizar la interfaz de usuario con el nombre obtenido
                } else {
                    Log.d("YourFragment", "No se encontró el correo en la base de datos");
                }
            } else {
                Log.d("YourFragment", "Error en la consulta: " + task.getException().getMessage());
            }
        });
        return this.nombreS;
    }
    public String ponercorreo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.correoS = user.getEmail();
        return this.correoS;
    }
    public void cambiarcontrasena(String nuevacon){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
            user.updatePassword(nuevacon)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Contraseña actualizada exitosamente");

                        } else {
                            Log.w(TAG, "Error al actualizar la contraseña", task.getException());
                        }
                    });
        } else {
            Log.w(TAG, "Usuario no autenticado");
        }
    }

}

