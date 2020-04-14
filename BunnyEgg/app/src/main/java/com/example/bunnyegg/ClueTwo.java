package com.example.bunnyegg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class ClueTwo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_clue);

        String message = getString(R.string.clue_two);
        TextView clueView = (TextView) findViewById(R.id.clue);
        clueView.setText(message);
    }

    public void next(View v){
        Intent clueThreeIntent = new Intent(ClueTwo.this, ClueThree.class);

        startActivity(clueThreeIntent);

    }
}
