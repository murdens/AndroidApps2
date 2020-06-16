package com.example.worryeater.data.model;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

import java.net.URL;

public class JournalData {
    private String title;
    private String thoughts;
    private Timestamp timeAdded;
    private String imageUrl;
    private String userId;
    private String username;
    private String id;


    public JournalData(){}

    public JournalData(String title, String thoughts, String userId, String username, Timestamp timeAdded, String imageUrl) {
        this.title = title;
        this.thoughts = thoughts;
        this.userId = userId;
        this.username = username;
        this.timeAdded = timeAdded;
        this.imageUrl = imageUrl;

    }

    public JournalData(String title, String thoughts, String imageUrl) {
        this.title = title;
        this.thoughts = thoughts;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThoughts() {
        return thoughts;
    }

    public void setThoughts(String thoughts) {
        this.thoughts = thoughts;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Exclude
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }



}
