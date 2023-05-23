package com.example.ddoverhaul.multiplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import com.example.ddoverhaul.BaseActivity;
import com.example.ddoverhaul.Evento;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;
import com.google.android.gms.tasks.OnSuccessListener;
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
                            String player1 = value.getString("player1");
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
        CollectionReference characterCollection = lobbyRef.collection("objects");
        DocumentReference newObj = characterCollection.document("object");
        newObj.set(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                lobbyRef.update(updates);
            }
        });
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