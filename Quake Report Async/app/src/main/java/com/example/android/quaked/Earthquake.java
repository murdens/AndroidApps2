package com.example.android.quaked;

public class Earthquake {

    private double eMagnitude;
    private String eCity;
    private long eTimeInMilliseconds;
    private String eUrl;

    /* Create earthquake object
     *
     * @param magnitude - measure of the earthquake
     * @param city - location where took place
     * @param date - date of event
     */
    public Earthquake(double magnitude, String city, long timeInMilliseconds, String Url) {
        eMagnitude = magnitude;
        eCity = city;
        eTimeInMilliseconds = timeInMilliseconds;
        eUrl = Url;
    }

    /*
     * Get the date
     */
    public long getTimeInMilliseconds() {
        return eTimeInMilliseconds;
    }

    /*
     * Get the city
     */
    public String geteCity() {
        return eCity;
    }

    /*
     * Get the magnitude
     */
    public double geteMagnitude() {
        return eMagnitude;
    }

    /*
     * Get the URL
     */
    public String geteUrl() {
        return eUrl;
    }

}
