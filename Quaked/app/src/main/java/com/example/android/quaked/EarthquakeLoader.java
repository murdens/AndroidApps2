package com.example.android.quaked;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;


import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoader call");
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.i(LOG_TAG, "loadinBackground called");
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Earthquake> earthquake = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquake;
    }
}
