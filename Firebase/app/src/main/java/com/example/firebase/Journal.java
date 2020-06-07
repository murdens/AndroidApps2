package com.example.firebase;

public class Journal {

    private String title;
    private String thought;

    public Journal(String title, String thought) {
        this.title = title;
        this.thought = thought;
    }
    // Firestore requires empty constructore
    public Journal() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }
}
