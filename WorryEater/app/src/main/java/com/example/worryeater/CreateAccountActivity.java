package com.example.worryeater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.worryeater.util.JournalApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {
    private Button createAccBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    //Firestore connection
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    private EditText mEmailEditText, mPasswordEditText, mUsernameEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        createAccBtn = findViewById(R.id.create_account);
        progressBar = findViewById(R.id.createacc_progress);
        mEmailEditText = findViewById(R.id.email_acc);
        mPasswordEditText = findViewById(R.id.password_acc);
        mUsernameEditText = findViewById(R.id.username_account);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    //user already logged in
                } else {
                    //no user yet?
                }
            }
        };

        createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mEmailEditText.getText().toString())
                && !TextUtils.isEmpty(mPasswordEditText.getText().toString())
                && !TextUtils.isEmpty(mUsernameEditText.getText().toString())) {

                    String username = mUsernameEditText.getText().toString().trim();

                    String email = null;
                    if (mEmailEditText.getText().toString().trim().matches(emailPattern)) {
                        email = mEmailEditText.getText().toString().trim();
                    } else {
                        Toast.makeText(CreateAccountActivity.this,
                                "Enter valid email address",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                    String password = null;
                    if (mPasswordEditText.getText().toString().trim().length() >= 6) {
                        password = mPasswordEditText.getText().toString().trim();
                    } else {
                        Toast.makeText(CreateAccountActivity.this,
                                "Password must be longer than 6 characters",
                                Toast.LENGTH_LONG)
                                .show();
                    }

                    createUserEmailAcc(email, password, username);

                } else {
                    Toast.makeText(CreateAccountActivity.this,
                            "Empty fields not allowed",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void createUserEmailAcc(String email, String password, final String username) {
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(username)) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // we take user to AddJournalActivity
                                currentUser = mAuth.getCurrentUser();
                                assert currentUser != null;
                                final String currentUserId = currentUser.getUid();

                                //Create user map, create user in collection
                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("username", username);

                                // save to firebase firestore
                            collectionReference.add(userObj)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(Objects.requireNonNull(task.getResult()).exists()){
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        String name = task.getResult()
                                                                .getString("username");

                                                        JournalApi journalApi = JournalApi.getInstance(); // global APi
                                                        journalApi.setUsername(name);
                                                        journalApi.setUserId(currentUserId);

                                                        Intent intent = new Intent(CreateAccountActivity.this,
                                                                PostJournalActivity.class);
                                                        intent.putExtra("username", name);
                                                        intent.putExtra("UserId", currentUserId);
                                                        startActivity(intent);
                                                    } else {
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                    }

                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                            } else {

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        } else {
           // TO DO

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // set currentUser and authenticated user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(authStateListener);
    }
}
