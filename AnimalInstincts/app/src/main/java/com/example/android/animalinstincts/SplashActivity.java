package com.example.android.animalinstincts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;

    /**
    //Hooks
    View book;
    TextView appName, tag;

    //animation
    Animation topAnimation,bottomAnimation, middleAnimation;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);

        /**
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        //Hooks
        book = findViewById(R.id.design);
        appName = findViewById(R.id.appName);
        tag = findViewById(R.id.tag);

        //Setting animations
        book.setAnimation(topAnimation);
        appName.setAnimation(middleAnimation);
        tag.setAnimation(bottomAnimation);
        */

        // handler to set intent post time delay to move to BookendsSearchActivity.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, CatalogActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
