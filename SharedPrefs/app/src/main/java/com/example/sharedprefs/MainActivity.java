package com.example.sharedprefs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String MESSAGE_ID = "messages_prefs";
    private EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageText = findViewById(R.id.message_text);
        TextView messageTextView = findViewById(R.id.show_message_textview);
        Button saveButton = findViewById(R.id.button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString().trim();

                SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("messages", message);

                editor.apply(); // saving to disk
            }
        });

        //get data back from Shared Prefs
        SharedPreferences getSharedData = getSharedPreferences(MESSAGE_ID, MODE_PRIVATE);
        String value = getSharedData.getString("message", "Nothing");

        messageTextView.setText(value);
    }
}
