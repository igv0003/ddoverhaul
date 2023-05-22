package com.example.ddoverhaul.multiplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {




    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        // Enviar el token a Shared Preferences
        saveTokenToSharedPreferences(token);

    }

    private void saveTokenToSharedPreferences(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firebaseToken", token);
        editor.apply();
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            // Se comprueba que la notificacion es silenciosa
            if (remoteMessage.getNotification() == null) {
                Map<String, String> data = remoteMessage.getData();

                // Comprueba que tipo de notificación es
                String type = data.get("type");
                switch (type) {
                    case "join":
                        // Es una peticion de unirse al lobby
                        String clientToken = data.get("clientToken");
                        // Tambien recogerá el personaje del cliente

                        sendJoinPetition(clientToken,"",type);
                }

            }
        }
    }

    // Método que recoge el token y el personaje del cliente que quiere unirse al lobby
    private void sendJoinPetition(String client, String personaje, String type) {
        Intent intent = new Intent("com.example.NOTIFICATION_DATA");
        intent.putExtra("clientToken",client);
        intent.putExtra("personaje",personaje);
        intent.putExtra("type",type);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }



}
