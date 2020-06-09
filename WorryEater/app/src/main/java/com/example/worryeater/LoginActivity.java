package com.example.worryeater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.worryeater.util.JournalApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn, createAccBtn;
    private AutoCompleteTextView emailAddress;
    private EditText password;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empty);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        mAuth = FirebaseAuth.getInstance();

        loginBtn = findViewById(R.id.email_sign_in);
        createAccBtn = findViewById(R.id.create_account_login);
        emailAddress = findViewById(R.id.email);
        password = findViewById(R.id.password);

        progressBar = findViewById(R.id.login_progress);

        progressBar.setVisibility(View.INVISIBLE);

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEmailPasswordUser(emailAddress.getText().toString().trim(),
                        password.getText().toString().trim());
            }

            private void LoginEmailPasswordUser(String email, String pwd) {
                progressBar.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(pwd)){
                    mAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String currentUserId = mAuth.getUid();

                                    collectionReference
                                            .whereEqualTo("userId", currentUserId)
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                                                    @Nullable FirebaseFirestoreException e) {
                                                    assert queryDocumentSnapshots != null;
                                                    if (!queryDocumentSnapshots.isEmpty()){

                                                        progressBar.setVisibility(View.INVISIBLE);

                                                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){

                                                            JournalApi journalApi =  JournalApi.getInstance();
                                                            journalApi.setUsername(snapshot.getString("username"));
                                                            journalApi.setUserId(snapshot.getString("userId"));

                                                            // Go to ListActivity
                                                            startActivity(new Intent(LoginActivity.this,
                                                                    JournalListActivity.class));
                                                        }
                                                    }
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: " + e.getMessage());
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });

                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, " Please enter email and password",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }
}
