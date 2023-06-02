package com.example.ddoverhaul.multiplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ddoverhaul.BaseActivity_Multi;
import com.example.ddoverhaul.Consumibles;
import com.example.ddoverhaul.Equipo;
import com.example.ddoverhaul.Evento;
import com.example.ddoverhaul.Objeto;
import com.example.ddoverhaul.Personaje;
import com.example.ddoverhaul.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import android.graphics.drawable.AnimatedImageDrawable;

import java.util.HashMap;
import java.util.Map;

public class Master extends BaseActivity_Multi {
    private ImageView imageView;
    private Button volver;
    private AnimatedImageDrawable animatedImageDrawable;
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
        imageView = findViewById(R.id.engranajeImg);
        Drawable drawable = getResources().getDrawable(R.drawable.icon_gif_engranaje);
        if (drawable instanceof AnimatedImageDrawable) {
            animatedImageDrawable = (AnimatedImageDrawable) drawable;
            imageView.setImageDrawable(animatedImageDrawable);
            animatedImageDrawable.start();
        }

        volver = findViewById(R.id.Volver);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // Se crea debido al MultiSelector, tiene Extras
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            obtainExtras();
        }
        /*
        // Se obtiene el documento que hace referencia al lobby
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        this.lobbyCol = db.collection("lobbys");
        this.lobbyRef = lobbyCol.document(lobbyName);
        setListener();*/
    }

    // Método que obtiene extras
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
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
        clientChars = new Personaje[clientTokens.length];
        // Se obtiene la lista de personajes sin null

        length = 0;
        for (int i = 0; i < 4; i++) {
            String player = "player"+(i+1);
            Personaje character = getIntent().getSerializableExtra(player,Personaje.class);
            if (character != null) {
                clientChars[length] = character;
                length++;
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
                        case "give":
                            stealObject(value, msg);
                            break;
                        case "left":
                            // Un jugador ha abandonado la sala
                            clientLeft(value);
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
                            giveObject(player1,obj);
                            sendMessage(clientTokens[player2],"take",obj);
                            takeObject(player2,obj);
                            // Método guardar objeto en lista objetos
                            Toast.makeText(getApplicationContext(), "Se ha realizado el robo de objeto",Toast.LENGTH_SHORT).show();
                        } else if (msg.equals("give")) {
                            sendMessage(clientTokens[player1],"take",obj);
                            takeObject(player1,obj);
                            sendMessage(clientTokens[player2],"give",obj);
                            giveObject(player2,obj);
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

    // Método para dar un objeto
    private void giveObject(int player, Objeto obj) {
        // Comprueba el tipo del objeto
        // Si es otro o consumible se guarda en inventario
        if (obj.getTipo().equals("Otro") || obj.getTipo().equals("Consumible")) {
            if (clientChars[player].getInventario().size() < 5) {
                clientChars[player].addToInventory((Consumibles) obj);
            }
        } else {
            // Si es un equipo, lo convierte a su clase y la añade donde debe con su posicion
            Equipo equip = (Equipo) obj;
            switch (equip.getPosicion()) {
                case 1:
                    clientChars[player].setCabeza(equip);
                    break;
                case 2:
                    clientChars[player].setPerchera(equip);
                    break;
                case 3:
                    clientChars[player].setGuantes(equip);
                    break;
                case 4:
                    clientChars[player].setPantalones(equip);
                    break;
                case 5:
                    clientChars[player].setPies(equip);
                    break;
                case 6:
                    clientChars[player].setArma(equip);
                    break;
                case 7:
                    clientChars[player].setArma_sec(equip);
                    break;
                default:
                    break;
            }
        }


    }

    // Método para quitar un objeto
    private void takeObject(int player, Objeto obj) {
        // Comprueba el tipo del objeto
        // Si es otro o consumible lo obtiene de inventario
        if (obj.getTipo().equals("Otro") || obj.getTipo().equals("Consumible")) {
            if (clientChars[player].getInventario().size() > 0) {
                clientChars[player].removeFromInventory((Consumibles)obj);
            }
        } else {
            // Si es un equipo, lo convierte a su clase y pone null la posición
            Equipo equip = (Equipo) obj;
            switch (equip.getPosicion()) {
                case 1:
                    clientChars[player].setCabeza(null);
                    break;
                case 2:
                    clientChars[player].setPerchera(null);
                    break;
                case 3:
                    clientChars[player].setGuantes(null);
                    break;
                case 4:
                    clientChars[player].setPantalones(null);
                    break;
                case 5:
                    clientChars[player].setPies(null);
                    break;
                case 6:
                    clientChars[player].setArma(null);
                    break;
                case 7:
                    clientChars[player].setArma_sec(null);
                    break;
                default:
                    break;
            }
        }
    }

    // Método cuando un cliente abandona la sala
    private void clientLeft(DocumentSnapshot doc) {
        String clientToken = doc.getString("clientToken");
        // Se busca la posición del cliente
        int clientPos = -1;
        for (int i = 0; i< clientTokens.length; i++) {
            if (clientTokens[i].equals(clientToken)) {
                clientPos = i;
                i = clientTokens.length;
            }
        }
        // Se reduce el tamaño de los array eliminando los huecos nulos
        if (clientPos != -1) {
            clientTokens[clientPos] = null;
            clientChars[clientPos] = null;

            String[] tempClients = new String[clientTokens.length-1];
            Personaje[] tempChars = new Personaje[clientChars.length-1];
            int tempPos = 0;
            for (int i = 0; i < clientTokens.length; i++) {
                if (clientTokens != null) {
                    tempClients[tempPos] = clientTokens[i];
                    tempChars[tempPos] = clientChars[i];
                    tempPos++;
                }
            }
            clientTokens = tempClients;
            clientChars = tempChars;
            sendMessage("all","clientLeft");
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