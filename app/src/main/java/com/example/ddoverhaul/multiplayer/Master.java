package com.example.ddoverhaul.multiplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.Consumibles;
import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.Evento;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;
import com.example.ddoverhaul.habilidadList.habilidadlist;
import com.example.ddoverhaul.habilidadList.viewSkill;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Master extends BaseActivity {

    private CollectionReference lobbyCol;
    private String lobbyName;
    private DocumentReference lobbyRef;
    private String mainToken;
    private String[] clientTokens;
    private Personaje[] clientChars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        // Se crea debido al MultiSelector, tiene Extras
        obtainExtras();
        // Se obtiene el documento que hace referencia al lobby
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.lobbyCol = db.collection("lobbys");
        this.lobbyRef = lobbyCol.document(lobbyName);



    }

    // Método que obtiene extras
    private void obtainExtras() {
        lobbyName = getIntent().getStringExtra("lobbyName");
        mainToken = getIntent().getStringExtra("mainToken");

        // Se limpian los null para tener la lista definitiva de clientes
        String[] clients = getIntent().getStringArrayExtra("clientTokens");
        int length = 0;
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null) length++;
        }
        clientTokens = new String[length];
        int pos = 0;
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null) {
                clientTokens[pos] = clients[i];
                pos++;
            }
        }
        // Se obtiene y se limpia la lista de personajes null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Personaje[] chars = getIntent().getParcelableArrayExtra("clientChars",Personaje.class);

            length = 0;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] != null) length++;
            }
            clientChars = new Personaje[length];
            pos = 0;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] != null) {
                    clientChars[pos] = chars[i];
                    pos++;
                }
            }
        }
    }

    // Método que prepara el setListener
    private void setListener() {
        lobbyRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                // El documento existe y fue modificado
                if (value != null && value.exists()) {
                    String msg = value.getString("message");

                    switch (msg) {
                        case "steal":
                            // Acciones cuando Jug1 roba X objeto a Jug2
                            stealObject(value, msg);
                            break;
                        case "give":
                            // Acciones cuando Jug1 da X objeto a Jug2
                            break;
                        case "left":
                            // Acciones cuando un jugador abandona la sala
                            break;
                        default:
                            break;
                    }
                    cleanLobby();
                }
            }
        });
    }

    // Recoge los jugadores implicados y la acción a realizar
    private void stealObject(DocumentSnapshot doc, String msg) {
        int player1=-1;
        for (int i = 0; i < clientTokens.length; i++) {
            if (clientTokens[i].equals(doc.getString("player1"))) {
                player1 = i;
                i = clientTokens.length;
            }
        }
        int player2=-1;
        for (int i = 0; i < clientTokens.length; i++) {
            if (clientTokens[i].equals(doc.getString("player2"))) {
                player2 = i;
                i = clientTokens.length;
            }
        }
        final Objeto[] obj = {new Objeto()};
        // Obtiene el personaje del cliente
        DocumentReference objRef = lobbyRef.collection("objects").document("object");

        int finalPlayer1 = player1;
        int finalPlayer2 = player2;
        objRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Si el objeto existe, comprueba el tipo, lo recupera con su Clase original y lo guarda como Objeto
                        String type = document.toObject(Objeto.class).getTipo();
                        if (type.equals("Equipo")) {
                            Equipo equip = document.toObject(Equipo.class);
                            obj[0] = equip;
                        } else if (type.equals("Consumible")) {
                            Consumibles cons = document.toObject(Consumibles.class);
                            obj[0] = cons;
                        } else {
                            obj[0] = document.toObject(Objeto.class);
                        }

                        objRef.delete();
                        requestSteal(finalPlayer1, finalPlayer2, obj[0],msg);
                    } else {
                        Log.d("Firestore", "El documento de objeto no existe");
                    }
                } else {
                    Log.d("Firestore", "Error al obtener el documento: " + task.getException());
                }
            }
        });

    }

    // Método que gestiona la petición de robo de un objeto
    private void requestSteal(int player1, int player2, Objeto obj, String msg) {
        String message = "";
        if (msg.equals("steal")) {
            message = clientChars[player1].getNombre()+" quiere robar el objeto "+obj.getNombre()+" a "+clientChars[player2].getNombre();
        } else if (msg.equals("give")) {
            message = clientChars[player1].getNombre()+" quiere dar el objeto "+obj.getNombre()+" a "+clientChars[player2].getNombre();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(Master.this);
        builder.setTitle("Petición de robo");
        builder.setMessage(message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ACCIONES DE ROBO DE OBJETO O ENTREGA DE OBJETO
                        if (msg.equals("steal")) {


                            sendMessage(clientTokens[player1],"give",obj);
                            sendMessage(clientTokens[player2],"take",obj);
                            Toast.makeText(getApplicationContext(), "Se ha realizado el robo de objeto",Toast.LENGTH_SHORT).show();
                        } else if (msg.equals("give")) {


                            sendMessage(clientTokens[player1],"take",obj);
                            sendMessage(clientTokens[player2],"give",obj);
                            Toast.makeText(getApplicationContext(), "Se ha realizado la entrega de objeto",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Denegar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Se denegó el robo",Toast.LENGTH_SHORT).show();
                        sendMessage(clientTokens[player1],"rejected");
                        dialog.dismiss();
                    }
                }).show();
    }

    private void giveObject(int player, Objeto obj) {

        if (obj.getTipo().equals("Otro") || obj.getTipo().equals("Consumible")) {
        }


    }


    // MÉTODOS PARA ENVIAR MENSAJES A LOS CLIENTES
    private void sendMessage(String clientToken, String msg) {
        Map<String,Object> updates = new HashMap<>();
        updates.put("message",msg);
        updates.put("clientToken",clientToken);
        lobbyRef.update(updates);
    }

    // ¡¡¡¡¡¡¡¡¡¡¡¡¡¡Revisar!!!!!!!!!!!!!!!!!!
    private void sendMessage(String clientToken, String msg, Objeto obj) {
        Map<String,Object> updates = new HashMap<>();
        updates.put("message",msg);
        updates.put("clientToken",clientToken);

        // Crea una nueva colección con un nuevo documento
        CollectionReference characterCollection = lobbyRef.collection(clientToken);
        DocumentReference newObj = characterCollection.document("object");

        // Comprueba que tipo de objeto es, lo convierte y lo guarda
        if (obj instanceof Equipo) {
            Equipo equip = (Equipo) obj;
            newObj.set(equip).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    lobbyRef.update(updates);
                }
            });
        } else if (obj instanceof Consumibles) {
            Consumibles cons = (Consumibles) obj;
            newObj.set(cons).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    lobbyRef.update(updates);
                }
            });
        } else {
            newObj.set(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    lobbyRef.update(updates);
                }
            });
        }





    }

    private void sendMessage(String clientToken, Evento event) {
        Map<String,Object> updates = new HashMap<>();
        updates.put("message","event");
        updates.put("clientToken",clientToken);

        // Crea una nueva colección con un nuevo documento
        CollectionReference characterCollection = lobbyRef.collection("events");
        DocumentReference newObj = characterCollection.document("event");
        newObj.set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                lobbyRef.update(updates);
            }
        });
    }

    // Acciones para limpiar el mensaje del lobby
    private void cleanLobby() {
        Map<String,Object> updates = new HashMap<>();
        updates.put("message","");
        updates.put("clientToken","");
        lobbyRef.update(updates);
    }

}