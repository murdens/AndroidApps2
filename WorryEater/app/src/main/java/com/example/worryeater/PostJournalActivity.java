package com.example.worryeater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.worryeater.data.model.JournalData;
import com.example.worryeater.util.JournalApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.Objects;

public class PostJournalActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int GALLERY_CODE = 1;
    private static final String TAG = "PostJournalActivity" ;
    private Button postJournalBtn;
    private ImageView postPhotoBtn, postImageView;
    private EditText postJournalTitle, postJournalThoughts;
    private TextView postUser, postDate;
    private ProgressBar progressBar;

    private String currentUserId;
    private String currentUsername;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    //Connection to firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Journal");
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_journal);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        mAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        progressBar = findViewById(R.id.post_progressBar);
        postJournalBtn = findViewById(R.id.post_save_button);
        postJournalBtn.setOnClickListener(this);
        postPhotoBtn = findViewById(R.id.post_photo_button);
        postPhotoBtn.setOnClickListener(this);

        postUser = findViewById(R.id.post_user_textview);
        postJournalTitle = findViewById(R.id.post_title_et);
        postJournalThoughts = findViewById(R.id.post_desc_et);
        postImageView = findViewById(R.id.image);

        progressBar.setVisibility(View.INVISIBLE);

        if (JournalApi.getInstance() != null){
            currentUserId = JournalApi.getInstance().getUserId();
            currentUsername = JournalApi.getInstance().getUsername();

            postUser.setText(currentUsername);
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //   TODO
                }

            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post_save_button:
                saveJournal();
                break;
            case R.id.post_photo_button:
                // add photo from gallery
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
                break;
        }
    }

    private void saveJournal() {
        final String title = postJournalTitle.getText().toString().trim();
        final String thoughts = postJournalThoughts.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(title)
        && !TextUtils.isEmpty(thoughts)
        && imageUri != null){

            final StorageReference filepath = storageReference
                    .child("journal_images")
                    .child("my_image_" + Timestamp.now().getSeconds()); // my_image_84t74878

            filepath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.INVISIBLE);

                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                                @Override
                                public void onSuccess(Uri uri) {

                                    String imageUrl =uri.toString();
                                    Timestamp timeAdded = new Timestamp(new Date());

//                                    JournalData journalData = new JournalData();

//                                    journalData.setTitle(title);
//                                    journalData.setThoughts(thoughts);
//                                    journalData.setImageUrl(imageUrl);
//                                    journalData.setUserId(currentUserId);
//                                    journalData.setUsername(currentUsername);
//                                    journalData.setTimeAdded(new Timestamp(new Date()));

                                    collectionReference.add(new JournalData(
                                            title,
                                            thoughts,
                                            currentUserId,
                                            currentUsername,
                                            timeAdded,
                                            imageUrl))

//                                    ))
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "onSuccess: start list");
                                            progressBar.setVisibility(View.INVISIBLE);
                                            startActivity(new Intent(PostJournalActivity.this,
                                                    JournalListActivity.class));
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            Log.d("TAG", "onFailure: " + e.getMessage());
                                        }
                                    });

                                    // TODO save journal instance

                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                postImageView.setImageURI(imageUri);
            }
        } else {
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        user = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuth != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

}
