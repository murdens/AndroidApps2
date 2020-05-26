package com.example.placeholders;

import android.content.Intent;
import android.os.Bundle;
import androidx.transition.TransitionManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Placeholder;

import com.example.placeholders.R;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout layout;
    private Placeholder placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.layout);
        placeholder = findViewById(R.id.placeholder);

    }

    public void swapView(View v){
        TransitionManager.beginDelayedTransition(layout);
        placeholder.setContentId(v.getId());
    }

    public void nextExample(View v){
        Intent intent = new Intent(MainActivity.this, PlaceholderActivity.class);
        startActivity(intent);
        finish();
    }
}
