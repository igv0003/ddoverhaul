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
import com.google.firebase.firestore.FirebaseFirestore;

public class PerfilFragment extends Fragment {
    private String nombreS;
    private String correoS;
    private AlertDialog alert;
    private String contraNS;
    private String contraNSC;
    private EditText contraN;
    private EditText contraNconfirm;

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
                // Acción a realizar al hacer clic en el botón Cancelar (opcional)
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }


    public String  ponernombre(){
        SharedPreferences shared = requireContext().getSharedPreferences("LoginUsuario", Context.MODE_PRIVATE);
        String correo = shared.getString("correo", "");
        String contraseña = shared.getString("contraseña", "");

        PerfilFragment perfilFragment = new PerfilFragment();
        perfilFragment.setSharedPreferences(shared);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userRef = db.collection("User_Email");

       this.nombreS = userRef.whereEqualTo("name", )
                .get();
        return this.nombreS;
    }
    public String ponercorreo(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        this.correoS = sharedPreferences.getString("correo", "");
        return this.correoS;
    }
    public void cambiarcontrasena(String nuevacon){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.updatePassword(nuevacon)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // La contraseña se cambió exitosamente
                            Log.d(TAG, "Contraseña actualizada exitosamente");
                            // Aquí puedes realizar acciones adicionales después de cambiar la contraseña
                        } else {
                            // Hubo un error al cambiar la contraseña
                            Log.w(TAG, "Error al actualizar la contraseña", task.getException());
                        }
                    });
        } else {
            // El usuario no está autenticado
            Log.w(TAG, "Usuario no autenticado");
        }
    }

}

