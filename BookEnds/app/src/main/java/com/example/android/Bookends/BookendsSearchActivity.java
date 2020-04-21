package com.example.android.Bookends;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

public class BookendsSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookends_search);

        // intent to move to BookendsActivity on search click event, if connected and search successful.
        final Intent bookendsActivity = new Intent(this, BookendsActivity.class);
        final EditText searchQuery = findViewById(R.id.bookInput);
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchQueryString = searchQuery.getText().toString();
                bookendsActivity.putExtra("searchQuery", searchQueryString);

                startActivity(bookendsActivity);

            }
        });
    }

}