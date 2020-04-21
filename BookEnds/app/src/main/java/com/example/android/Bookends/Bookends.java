package com.example.android.Bookends;


import java.util.ArrayList;

public class Bookends {

    //private Image aImage;
    private String aTitle, aUrl;
    private ArrayList<String> aAuthors;
    private double aAverageRating;
    private String aSmallThumbnailUrl;

    /* Create books object
     *
     * @param title - book tile
     * @param author - book author(s)
     * @param averageRating - book rating based on reviews
     * @param url - @link to book
     * @param thumbnail - image icon of books
     */
    public Bookends(String title, ArrayList<String> authors, double averageRating, String url, String smallThumbnailUrl) {
        aTitle = title;
        aAuthors = authors;
        aAverageRating = averageRating;
        aUrl = url;
        aSmallThumbnailUrl = smallThumbnailUrl;
    }

    // get small image
    public String getSmallThumbnail() {
        return aSmallThumbnailUrl;

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
