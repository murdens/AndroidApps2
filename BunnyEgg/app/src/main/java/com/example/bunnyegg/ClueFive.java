package com.example.bunnyegg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClueFive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clue);

        String message = getString(R.string.clue_five);
        TextView clueView = (TextView) findViewById(R.id.clue);
        clueView.setText(message);
    }
    public void next(View v){
        Intent clueEndIntent = new Intent(ClueFive.this, BunnyEnd.class);

        startActivity(clueEndIntent);

    }

}
