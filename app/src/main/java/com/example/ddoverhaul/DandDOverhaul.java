package com.example.ddoverhaul;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class DandDOverhaul extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
