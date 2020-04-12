package com.example.android.quakereport;

public class Earthquake {

    private String eMagnitude;
    private String eCity;
    private long eTimeInMilliseconds;

    /* Create earthquake object
     *
     * @param magnitude - measure of the earthquake
     * @param city - location where took place
     * @param date - date of event
     */
    public Earthquake(String magnitude, String city, long timeInMilliseconds)
    {
        eMagnitude = magnitude;
        eCity = city;
        eTimeInMilliseconds = timeInMilliseconds;
    }

    /*
     * Get the date
     */
    public long getTimeInMilliseconds() { return eTimeInMilliseconds; }

    /*
     * Get the city
     */
    public String geteCity() { return eCity; }

    /*
     * Get the magnitude
     */
    public String geteMagnitude() { return eMagnitude; }


}
