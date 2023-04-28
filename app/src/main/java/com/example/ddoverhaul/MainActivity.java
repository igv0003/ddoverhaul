package com.example.ddoverhaul;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONHelper jHelper = new JSONHelper(getBaseContext());

        //jHelper.addCharacter();
    }
}