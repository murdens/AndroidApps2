package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText enterTitle;
    private EditText enterThought;
    private Button saveBtn;
    private Button showBtn;
    private TextView showTitle, showThought;

    //Keys

    public static  final  String KEY_TITLE = "title";
    public static  final  String KEY_THOUGHT = "thought";

    // connection to firestore

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef = db.document("Journal/First Thoughts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveBtn = findViewById(R.id.save_button);
        enterTitle = findViewById(R.id.enter_name_text);
        enterThought = findViewById(R.id.enter_thoughts_text);

        showBtn = findViewById(R.id.show_button);
        showTitle = findViewById(R.id.show_title);
        showThought = findViewById(R.id.show_thought);

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String title = documentSnapshot.getString(KEY_TITLE);
                            showTitle.setText(title);
                            String thought = documentSnapshot.getString(KEY_THOUGHT);
                            showThought.setText(thought);
                        } else {
                            Toast.makeText(MainActivity.this, "Nothing to show", Toast.LENGTH_LONG)
                                    .show();
                        }
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

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = enterTitle.getText().toString().trim();
                String thought = enterThought.getText().toString().trim();

                Map<String, Object> data = new HashMap<>();
                data.put(KEY_TITLE, title);
                data.put(KEY_THOUGHT, thought);

               docRef.set(data)
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

    @Override
    protected void onStart() {
        super.onStart();
        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Toast.makeText(MainActivity.this,"Something went wrong",
                            Toast.LENGTH_LONG)
                            .show();
                }
                if(documentSnapshot != null && documentSnapshot.exists()){
                    String title = documentSnapshot.getString(KEY_TITLE);
                    showTitle.setText(title);
                    String thought = documentSnapshot.getString(KEY_THOUGHT);
                    showThought.setText(thought);
                }
            }
        });
    }

}
