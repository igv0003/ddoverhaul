package com.example.ddoverhaul.multiplayer;

import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ddoverhaul.navigation.Normal.BaseActivity;
import com.example.ddoverhaul.R;

public class Client extends BaseActivity {
    private ImageView imageView;
    private Button volver;
    private AnimatedImageDrawable animatedImageDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

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
    }
}