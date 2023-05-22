package com.example.ddoverhaul.multiplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.ddoverhaul.Personaje;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MultiConfig {

    private CollectionReference lobbyDB;
    private Context context;

    // Variable contraseña del lobby
    private int password;
    private int sizeLobby;
    private int allCharactersReceived;
    private String mainToken;
    private String hostToken;
    private String clientToken;

    public MultiConfig (int pw, Context context) {
        // Se obtiene la referencia de la base de datos
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.lobbyDB = db.collection("lobbys");
        this.context = context;
        this.password = pw;
        this.sizeLobby = 0;

        SharedPreferences sharedPreferences = this.context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        this.mainToken = sharedPreferences.getString("firebaseToken", "");


    }

    public String getMainToken() {
        return this.mainToken;
    }


    // ACCIONES SI ES HOST

    // Método que crea un Lobby si es el master
    public void createLobby() {
        Map<String, Object> lobby = new HashMap<>();
        lobby.put("token", this.mainToken);
        lobby.put("size", sizeLobby+"");
        lobby.put("clientToken","");
        lobby.put("message","");

        DocumentReference newDoc = lobbyDB.document(this.password+"");

        newDoc.set(lobby)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        setHostListener(newDoc);
                    }
                })
                .addOnFailureListener(e -> Log.w("TAG", "Hubo un error al crear el lobby", e));

    }

    // Método que añade un listener al documento
    public void setHostListener(DocumentReference ref) {
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                if (value != null && value.exists()) {
                    // El documento existe y fue modificado
                    // Obtiene el mensaje y realiza las acciones necesarias
                    String msg = value.getString("message");
                    switch (msg) {
                        // Un cliente tiene una petición de unirse
                        case "join":
                            // Obtiene el token del cliente
                            clientToken = value.getString("clientToken");
                            // Obtiene el personaje del cliente
                            DocumentReference charRef = lobbyDB.document(password+"").collection("characters").document("clientChar");

                            charRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            // Si el personaje existe, pasa los datos a MultiSelector y borra los campos
                                            String sizeStr = value.getString("size");
                                            Personaje chara = document.toObject(Personaje.class);
                                            sendClientToken(clientToken,chara,Integer.parseInt(sizeStr));

                                            cleanLobby(ref);
                                            charRef.delete();
                                        } else {
                                            Log.d("Firestore", "El documento de personaje no existe");
                                        }
                                    } else {
                                        Log.d("Firestore", "Error al obtener el documento: " + task.getException());
                                    }
                                }
                            });

                            break;
                        case "left":
                            // Un cliente ha abandonado la sala
                            // Obtiene el token del cliente
                            String clientLeft = value.getString("clientToken");
                            // Llama al metodo para enviar el token del cliente que abandona la sala
                            sendClientLeft(clientLeft);
                            cleanLobby(ref);
                            break;
                        default:
                            break;
                    }

                }

            }
        });
    }

    private void setClientListener(DocumentReference ref) {
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }

                if (value != null && value.exists()) {
                    // El documento existe y fue modificado
                    String msg = value.getString("message");

                    // Si la partida comienza se notifica al cliente
                    if (msg.equals("startGame")) {
                        // Obtiene los personajes de la partida y los envia a MultiSelector cuando tenga todos
                        receiveCharacters();
                    } else if (msg.equals("delete")) {
                        sendLobbyDelete();
                    }

                }

            }
        });
    }

    // ACCIONES SI ES CLIENTE

    // Método de búsqueda de lobby
    public void foundLobby(Personaje character) {
        DocumentReference lobbyRef = lobbyDB.document(this.password+"");

        lobbyRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String sizeStr = document.getString("size");
                        // Si la sala aun tiene espacio, recibe el token del master
                        if (Integer.parseInt(sizeStr) < 4) {
                            hostToken = document.getString("token");
                            sendHostToken(hostToken);
                            setClientListener(lobbyRef);
                            int size = Integer.parseInt(sizeStr) +1;
                            updateLobby(lobbyRef, character, size);
                        }
                    } else {
                        Log.d("Firestore", "El documento no existe");
                    }
                } else {
                    Log.d("Firestore", "Error al obtener el documento: " + task.getException());
                }
            }
        });

    }

    // Método que actualiza los campos que contiene el lobby
    public void updateLobby(DocumentReference lobbyRef, Personaje character, int size) {

        Map<String, Object> updates = new HashMap<>();
        updates.put("clientToken",mainToken);
        updates.put("size",size+"");
        updates.put("message","join");

        // Crea una nueva colección con un nuevo documento
        CollectionReference characterCollection = lobbyRef.collection("characters");
        DocumentReference newChar = characterCollection.document("clientChar");

        newChar.set(character)
                .addOnSuccessListener(e -> Log.w("toma","ha funcionado"))
                .addOnFailureListener(e -> Log.w("e","ha funcionado"));

        lobbyRef.update(updates);

    }

    private void receiveCharacters() {
        Personaje[] chars = new Personaje[4];
        allCharactersReceived = 0;

        for (int i = 0; i < chars.length; i++) {
            // Obtiene el personaje del master
            DocumentReference charRef = lobbyDB.document(password+"").collection("gameCharacters").document("character"+i);

            int finalI = i;
            charRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Si el personaje existe, recoge al personaje y lo guarda en dicha posición
                            chars[finalI] = document.toObject(Personaje.class);
                            allCharactersReceived++;
                            sendCharacters(chars);
                        } else {
                            chars[finalI] = null;
                            allCharactersReceived++;
                            sendCharacters(chars);
                        }
                    } else {
                        Log.d("Firestore", "Error al obtener el documento: " + task.getException());
                    }
                }
            });

        }
    }

    // Método que limpia el documento para el siguiente cliente
    public void cleanLobby(DocumentReference lobbyRef) {
        Map<String,Object> updates = new HashMap<>();
        updates.put("message","");
        updates.put("clientToken","");
        lobbyRef.update(updates);
    }

    public void lobbyDown(int size) {
        DocumentReference lobbyRef = lobbyDB.document(this.password+"");
        lobbyRef.update("size",size+"");
    }

    // Método que modifica el documento indicando su borrado y borra el documento
    public void deleteLobby() {
        DocumentReference lobbyRef = lobbyDB.document(this.password+"");
        lobbyRef.update("message","delete");

        lobbyRef.delete();

    }

    // Método para abandonar la sala
    public void leftLobby() {
        DocumentReference lobbyRef = lobbyDB.document(this.password+"");

        Map<String, Object> updates = new HashMap<>();
        updates.put("clientToken",mainToken);
        updates.put("message","left");

        lobbyRef.update(updates);
    }

    // Método que gestiona y prepara la sala para los clientes
    public void startGame(Personaje[] characters) {
        //
        DocumentReference lobbyRef = lobbyDB.document(this.password+"");

        Map<String, Object> updates = new HashMap<>();
        updates.put("message","startGame");

        // Crea una nueva colección con un nuevo documento
        CollectionReference characterCollection = lobbyRef.collection("gameCharacters");

        // Se recorre la lista de personajes añadiendolos a la nueva colección
        int pos = 0;
        for (int i = 0; i < characters.length; i++) {
            // Si en la vuelta actual hay personaje
            if (characters[i] != null) {
                // Creo un nuevo documento con el nombre de la primera posicion del personaje
                DocumentReference newChar = characterCollection.document("character"+pos);
                // Al nuevo documento le añado el personaje actual serializado
                newChar.set(characters[i]);
                // Aumenta la posicion para el siguiente personaje
                pos++;
            }
        }
        // Se actualiza el mensaje de la sala para que los clientes se preparen
        lobbyRef.update(updates);
    }

    // Método que envía el token del cliente a MultiSelector para otras operaciones
    private void sendClientToken(String clientToken, Personaje character, int size) {
        Intent intent = new Intent("com.example.NOTIFICATION_DATA");
        intent.putExtra("message","join");
        intent.putExtra("clientToken",clientToken);
        intent.putExtra("character",character);
        intent.putExtra("size",size);
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }

    // Método que indica a MultiSelector que un cliente se ha ido
    private void sendClientLeft(String clientToken) {
        Intent intent = new Intent("com.example.NOTIFICATION_DATA");
        intent.putExtra("message","left");
        intent.putExtra("clientToken",clientToken);
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }

    // Método que indica a MultiSelector los personajes que se unen
    private void sendCharacters(Personaje[] chars) {
        // Cuando haya recibido todos los personajes
        if (allCharactersReceived == 4) {
            Intent intent = new Intent("com.example.NOTIFICATION_DATA");
            intent.putExtra("message","startGame");
            intent.putExtra("characters",chars);
            LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
        }
    }

    // Método que envia el token del host a MultiSelector para otras operaciones
    private void sendHostToken(String token){
        Intent intent = new Intent("com.example.NOTIFICATION_DATA");
        intent.putExtra("hostToken",token);
        intent.putExtra("message","hostToken");
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }

    // Método que envia la información de que el host ha eliminado la sala
    private void sendLobbyDelete() {
        Intent intent = new Intent("com.example.NOTIFICATION_DATA");
        intent.putExtra("message","lobbyDelete");
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }

}
