package com.example.android.Bookends;

//import android.media.Image;

public class Bookends {

    //private Image aImage;
    private String aTitle;
    private String aAuthor;
    private double aAverageRating;
    private String aUrl;

    /* Create earthquake object
     *
     * @param title - book tile
     * @param author - book author(s)
     * @param averageRating - book rating based on reviews
     * @param url - @link to book
     */
    public Bookends(String title, String author, double averageRating, String url) {
        aTitle= title;
        aAuthor = author;
        aAverageRating = averageRating;
        aUrl = url;
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
    public String getAuthor() {
        return aAuthor;
    }

    /*
     * Get the URL
     */
    public String getUrl() {
        return aUrl;
    }

}
