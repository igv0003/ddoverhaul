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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

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
        cambiarcorreo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoCambiarCorreo();

            }
        });
        cambiarusuario.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarDialogoCambiarusuario();
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.nombreS = user.getDisplayName();
        return this.nombreS;
    }
    private void mostrarDialogoCambiarCorreo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Cambiar correo");

        // Inflar el diseño del diálogo
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.cambiarcorreo, null);
        builder.setView(view);

        // Obtener referencias a las vistas del diálogo
        EditText CorreoActual = view.findViewById(R.id.CorreoActual);
        EditText NuevoCorreo = view.findViewById(R.id.NuevoCorreo);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String correoActual = CorreoActual.getText().toString();
            String nuevoCorreo = NuevoCorreo.getText().toString();

            cambiarCorreo(correoActual, nuevoCorreo);
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
        private void cambiarCorreo(String correoActual, String nuevoCorreo) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("User_Email").document(correoActual);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            docRef.update("email", nuevoCorreo)
                    .addOnSuccessListener(aVoid -> {
                        docRef.get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        Map<String, Object> datos = documentSnapshot.getData();
                                        DocumentReference docRefNuevo = db.collection("User_Email").document(nuevoCorreo);
                                        docRefNuevo.set(datos)
                                                .addOnSuccessListener(aVoid1 -> {
                                                    docRef.delete()
                                                            .addOnSuccessListener(aVoid2 -> {
                                                                user.updateEmail(nuevoCorreo);
                                                                Log.d("cambiarcorreo", "Correo actualizado correctamente");

                                                            })
                                                            .addOnFailureListener(e -> {
                                                                Log.d("cambiarcorreo", "Error al eliminar el documento anterior: " + e.getMessage());
                                                            });
                                                })
                                                .addOnFailureListener(e -> {
                                                    Log.d("YourFragment", "Error al crear el nuevo documento: " + e.getMessage());
                                                });
                                    } else {
                                        Log.d("YourFragment", "El documento no existe");
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Log.d("YourFragment", "Error al obtener el documento: " + e.getMessage());
                                });
                    })
                    .addOnFailureListener(e -> {
                        Log.d("YourFragment", "Error al actualizar el correo: " + e.getMessage());
                    });
        }


    public String ponercorreo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.correoS = user.getEmail();
        return this.correoS;
    }

    private void mostrarDialogoCambiarusuario() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Cambiar correo");

        View view = LayoutInflater.from(requireContext()).inflate(R.layout.cambiarnombreusuario, null);
        builder.setView(view);

        EditText usuarioac = view.findViewById(R.id.UsuarioActual);
        EditText usuarioNU = view.findViewById(R.id.NuevoUsuario);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String usuarioAC = usuarioac.getText().toString();
            String usuarioNUEVO = usuarioNU.getText().toString();
            cambiarUsuario(usuarioAC,usuarioNUEVO);
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void cambiarUsuario(String usuarioActual, String nuevoUsuario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userRef = db.collection("User_Email");

        Query query = userRef.whereEqualTo("name", usuarioActual);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                    String documentId = documentSnapshot.getId();

                    DocumentReference documentReference = userRef.document(documentId);

                    documentReference.update("name", nuevoUsuario)
                            .addOnSuccessListener(aVoid -> {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nuevoUsuario).build();
                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(task1-> {
                                            if (task1.isSuccessful()) {
                                                Log.d("YourFragment", "Nombre de usuario actualizado correctamente");
                                            } else {
                                                Log.d("YourFragment", "Error al actualizar el nombre de usuario: " + task.getException().getMessage());
                                            }
                                        });
                                Log.d("YourFragment", "Usuario actualizado correctamente");
                            })
                            .addOnFailureListener(e -> {
                                Log.d("YourFragment", "Error al actualizar el usuario: " + e.getMessage());
                            });
                } else {
                    Log.d("YourFragment", "No se encontró el usuario en la base de datos");
                }
            } else {
                Log.d("YourFragment", "Error en la consulta: " + task.getException().getMessage());
            }
        });
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

