package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText enterTitle;
    private EditText enterThought;
    private Button saveBtn;

    //Keys

    public static  final  String KEY_TITLE = "title";
    public static  final  String KEY_THOUGHT = "thought";

    // connection to firestore

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveBtn = findViewById(R.id.save_button);
        enterTitle = findViewById(R.id.enter_name_text);
        enterThought = findViewById(R.id.enter_thoughts_text);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = enterTitle.getText().toString().trim();
                String thought = enterThought.getText().toString().trim();

                Map<String, Object> data = new HashMap<>();
                data.put(KEY_TITLE, title);
                data.put(KEY_THOUGHT, thought);

                db.collection("Journal")
                        .document("First Thoughts")
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Success",
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "onFailure:" + e.toString());
                            }
                        });
            }
        });
    }
}
