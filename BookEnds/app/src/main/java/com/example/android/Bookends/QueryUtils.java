package com.example.android.Bookends;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * Helper methods related to requesting and receiving book data from Google Book Volume API.
 */
public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = BookendsActivity.class.getName();

    /**
     * Query the Google Book APIs and return an {@link List< Bookends >} object to represent a single book.
     */
    public static List<Bookends> fetchBookData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Bookends> bookends = extractFromJson(jsonResponse);

        // Return the {@link Event}
        return bookends;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Google books JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Bookends} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Bookends> extractFromJson(String bookJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding book data to it.
        List<Bookends> bookendsList = new ArrayList<>();

        List<String> authors = new ArrayList<String>();
        double rating = 0;
        String imageUrl = " ";

        // Try to parse the JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject jsonRootObject = new JSONObject(bookJSON);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("items");

            //Iterate the jsonArray and print the info of JSONObjects
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject currentObject = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = currentObject.getJSONObject("volumeInfo");

//          Title of book

                String title = volumeInfo.getString("title");

//          Authors
                try {
                    if (volumeInfo.has("authors"))
                        authors = jsonArrayToStringArray(volumeInfo.getJSONArray("authors"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//          Average Rating
                try {
                    if (volumeInfo.has("averageRating")) {
                        rating = volumeInfo.getDouble("averageRating");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//          Url link to book details
                String url = volumeInfo.getString("infoLink");

//          Bitmap smallThumbnail.

                try {
                    if (volumeInfo.has("imageLinks")) {
                        JSONObject imageObject = volumeInfo.getJSONObject("imageLinks");
                        if (imageObject.has("smallThumbnail")) {
                        imageUrl = imageObject.getString("smallThumbnail");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Bookends bookends = new Bookends(title, (ArrayList<String>) authors, rating, url, imageUrl);
                bookendsList.add(bookends);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Google Book JSON results", e);
        }

        // Return the list of books
        return bookendsList;
    }

    // helper method to convert JSONArray to StringArray to display authors in TextView
    public static ArrayList<String> jsonArrayToStringArray(JSONArray jsArray) {
        ArrayList<String> strArray = new ArrayList<String>();

        for (int j = 0; j < jsArray.length(); j++) {
            try {
                strArray.add(jsArray.get(j).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return strArray;
    }
}