package com.example.android.Bookends;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;


import java.util.List;

public class BookendsLoader extends AsyncTaskLoader<List<Bookends>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = BookendsLoader.class.getName();

    private String mUrl;

    public BookendsLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.i(LOG_TAG, "BookendsLoader: TEST "+url);
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoader call");
        forceLoad();
    }

    @Override
    public List<Bookends> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.i(LOG_TAG, "loadinBackground called");
        // Perform the network request, parse the response, and extract a list of Books.
        List<Bookends> bookends = QueryUtils.fetchBookData(mUrl);
        return bookends;
    }
}
