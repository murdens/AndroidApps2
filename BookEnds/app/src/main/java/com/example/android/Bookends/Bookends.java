package com.example.android.Bookends;

//import android.media.Image;

import android.media.Image;

import org.json.JSONArray;

import java.util.ArrayList;

public class Bookends {

    //private Image aImage;
    private String aTitle, aUrl;
    private ArrayList<String> aAuthors = new ArrayList<String>();
    private double aAverageRating;
    private Image iconOfBook;

    /* Create earthquake object
     *
     * @param title - book tile
     * @param author - book author(s)
     * @param averageRating - book rating based on reviews
     * @param url - @link to book
     */
    public Bookends(String title, ArrayList<String> authors, double averageRating, String url) {
        aTitle= title;
        aAuthors = authors;
        aAverageRating = averageRating;
        aUrl = url;
    }

    // get small image
    public Image getIcon() {
        return iconOfBook;

    }
    //  setter for image
    public void setIcon(Image icon) {

        this.iconOfBook = icon;
    }
    /*
     * Get the rating
     */
    public double getAverageRating() {
        return aAverageRating;
    }

    /*
     * Get the title
     */
    public String getTitle() {
        return aTitle;
    }


    /*
     * Get the author(s)
     */
    public ArrayList<String> getAuthors() {
        return aAuthors;
    }

    /*
     * Get the URL
     */
    public String getUrl() {
        return aUrl;
    }

}
