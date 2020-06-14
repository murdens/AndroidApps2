package com.example.worryeater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchUIUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worryeater.data.model.JournalData;
import com.example.worryeater.ui.JournalAdapter;
import com.example.worryeater.util.JournalApi;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JournalListActivity extends AppCompatActivity implements JournalAdapter.onItemClickListener {

    private static final String TAG = "JournalListActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storageReference;
    private DatabaseReference database;
    private RecyclerView recyclerView;
    private JournalAdapter journalAdapter;
    private CollectionReference collectionReference = db.collection("Journal");
    private TextView noJournalEntry;
    //private ImageButton deleteButton, shareButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_list);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        mAuth = FirebaseAuth.getInstance();
        user =  mAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Journal");

        setUpRecyclerView(user);

    }

    private void setUpRecyclerView(FirebaseUser user) {
        String currentUserId = user.getUid();
        // invoke recycler view
       // Query query = db.collection("Journal");
        Query query = collectionReference
                .whereEqualTo("userId", currentUserId)
                .orderBy("timeAdded", Query.Direction.DESCENDING);
        final FirestoreRecyclerOptions<JournalData> options = new FirestoreRecyclerOptions.Builder<JournalData>()
                .setQuery(query, new SnapshotParser<JournalData>() {
                    @NonNull
                    @Override
                    public JournalData parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        JournalData journalData = snapshot.toObject(JournalData.class);
                        journalData.setId(snapshot.getId());
                        return journalData;
                    }
                })
                .setLifecycleOwner(this)
                .build();
//                .setQuery(query, JournalData.class)
//                .build();

        journalAdapter = new JournalAdapter(options);

        // journalDataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(journalAdapter);

        journalAdapter.setOnItemClickListener(JournalListActivity.this);

        noJournalEntry = findViewById(R.id.list_no_thoughts);
        // deleteButton = findViewById(R.id.journal_row_delete_btn);
        // shareButton = findViewById(R.id.journal_row_share_btn);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                // Take users to add journal
                if (user != null && mAuth != null){
                    startActivity(new Intent(JournalListActivity.this,
                            PostJournalActivity.class));
                    finish();
                }
                break;
            case R.id.action_signout:
                // take user to login activity, sign out.
                if (user != null && mAuth != null){
                    mAuth.signOut();
                    startActivity(new Intent(JournalListActivity.this,
                            MainActivity.class));
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        journalAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        journalAdapter.stopListening();
    }

    @Override
    public void onItemClick(int position) {
        //TODO
    }

    @Override
    public void onDeleteClick(final int position) {
        //TODO
        //final String journalRef
//        final JournalData journalRef = journalAdapter.getSnapshots().getSnapshot(position).toObject(JournalData.class);   //getReference().getId();
//            collectionReference
//                .document(journalRef.getId())
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                       // StorageReference imageRef = storageReference.getReferenceFromUrl(journalRef.getImageUrl());
//                       // imageRef.delete();
//                        Toast.makeText(JournalListActivity.this, "Delete successful",
//                                Toast.LENGTH_LONG).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(JournalListActivity.this, "Deletion failed",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//        journalAdapter.notifyDataSetChanged();
    }

//        AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("Do you want to delete this image?")
//                .setPositiveButton("Yes", (dialog, which) -> {
//
//                }).setNegativeButton("Cancel", (dialog, which) -> {
//
//
//                });
//        builder.show();



    @Override
    public void onEditClick(int position) {
            //TODO

    }

    @Override
    public void onShareClick(int position) {
            //TODO
        JournalData journal = journalAdapter.getSnapshots().getSnapshot(position).toObject(JournalData.class);
        assert journal != null;
        Uri imageUri = Uri.parse(journal.getImageUrl());

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
//Send to Whattssap
        //shareIntent.setPackage("com.whatsapp");
//Even you can add text to the image

        //sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, journal.getTitle());
        sendIntent.putExtra(Intent.EXTRA_TEXT, journal.getThoughts());
        sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        sendIntent.setType("image/*");
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        try {
            startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(JournalListActivity.this,"Whatsapp have not been installed.",
                    Toast.LENGTH_LONG).show();
        }
    }
}
