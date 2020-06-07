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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText enterTitle, enterThought;
    private Button saveBtn, showBtn, updateBtn; //deleteBtn;
    private TextView showTitle;//showThought/;

    //Keys

    public static  final  String KEY_TITLE = "title";
    public static  final  String KEY_THOUGHT = "thought";

    // connection to firestore

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef = db.document("Journal/First Thoughts");
    private CollectionReference collectionRef = db.collection("Journal");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveBtn = findViewById(R.id.save_button);
        enterTitle = findViewById(R.id.enter_name_text);
        enterThought = findViewById(R.id.enter_thoughts_text);

        showBtn = findViewById(R.id.show_button);
        showTitle = findViewById(R.id.show_title);
        //showThought = findViewById(R.id.show_thought);

//        updateBtn = findViewById(R.id.update_button);
//        updateBtn.setOnClickListener(this);
////
//        deleteBtn = findViewById(R.id.delete_button);
//        deleteBtn.setOnClickListener(this);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry();
            }
        });
//                String title = enterTitle.getText().toString().trim();
//                String thought = enterThought.getText().toString().trim();
//
//                Journal journal = new Journal();
//                journal.setThought(thought);
//                journal.setTitle(title);
//
////                Map<String, Object> data = new HashMap<>();
////                data.put(KEY_TITLE, title);
////                data.put(KEY_THOUGHT, thought);
//
//                docRef.set(journal)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(MainActivity.this, "Success",
//                                        Toast.LENGTH_LONG)
//                                        .show();
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("TAG", "onFailure:" + e.toString());
//                            }
//                        });
//            }
//        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getThoughts();
            }
        });
//                docRef.get()
//                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                if (documentSnapshot.exists()) {
//                                    Journal journal = documentSnapshot.toObject(Journal.class);
//                                    assert journal != null;
//                                    showTitle.setText(journal.getTitle());
//                                    showThought.setText(journal.getThought());
//
//                                    //String title = documentSnapshot.getString(KEY_TITLE);
//                                    //String thought = documentSnapshot.getString(KEY_THOUGHT);
//
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Nothing to show", Toast.LENGTH_LONG)
//                                            .show();
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("TAG", "onFailure:" + e.toString());
//                            }
//                        });
//            }
//        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        collectionRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Log.d("TAG", "onStart: "+ e.toString());
                }
                String data = "";
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots){
                    Journal journal = snapshots.toObject(Journal.class);

                    data += "Title: " + journal.getTitle() + " \n"
                            + "Thought: " + journal.getThought() + "\n\n";

                    //showThought.setText(journal.getThought());
                }
                showTitle.setText(data);
            }

        });
//        docRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
//                                @Nullable FirebaseFirestoreException e) {
//                if(e != null){
//                    Toast.makeText(MainActivity.this,"Something went wrong",
//                            Toast.LENGTH_LONG)
//                            .show();
//                }
//                if(documentSnapshot != null && documentSnapshot.exists()){
//                    String data = "";
//                    Journal journal = documentSnapshot.toObject(Journal.class);
//
//                    data += "Title: " + journal.getTitle() + " \n"
//                            + "Thought: " + journal.getThought() + "\n\n";
//
//                    showTitle.setText(data);
//                    //showThought.setText(journal.getThought());
//                } else {
//                    showTitle.setText("");
//                    //showThought.setText("");
//                }
//            }
//        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.update_button:
//            updateEntry();
//            break;
//            case R.id.delete_button:
//             deleteEntry();
//                break;
//        }
//    }
//
//    private void deleteEntry() {
//        docRef.update(KEY_THOUGHT, FieldValue.delete());
//        docRef.update(KEY_TITLE, FieldValue.delete());
//    }

    public void updateEntry(){
        String title = enterTitle.getText().toString().trim();
        String thought = enterThought.getText().toString().trim();


        Map<String, Object> data = new HashMap<>();
        data.put(KEY_TITLE, title);
        data.put(KEY_THOUGHT, thought);

        docRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Update successful",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure:" + e.toString());
            }
        });
    }

    private void addEntry(){
        String title = enterTitle.getText().toString().trim();
        String thought = enterThought.getText().toString().trim();

        Journal journal = new Journal(title, thought);
        collectionRef.add(journal);
    }

    private void getThoughts(){
        collectionRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String data = "";
                        for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots){
                            Journal journal = snapshots.toObject(Journal.class);

                            data += "Title: " + journal.getTitle() + " \n"
                                    + "Thought: " + journal.getThought() + "\n\n";

                            //showThought.setText(journal.getThought());
                        }
                        showTitle.setText(data);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
