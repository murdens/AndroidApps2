package com.example.MotionAnimation;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView androidImage = (ImageView) findViewById(R.id.android_image);
        final SpringAnimation springAnimationX = new
                SpringAnimation(androidImage, DynamicAnimation.TRANSLATION_Y,0);

        SpringForce springForceX = new SpringForce(0);
        springForceX.setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        springForceX.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

        springAnimationX.setSpring(springForceX);
        springAnimationX.setStartVelocity(5000);

        androidImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                springAnimationX.setStartVelocity(5000);
                springAnimationX.start();
            }
        });
    }
}
