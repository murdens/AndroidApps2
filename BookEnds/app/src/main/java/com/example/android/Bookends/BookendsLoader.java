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
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Bookends> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of Books.
        List<Bookends> bookends = QueryUtils.fetchBookData(mUrl);
        return bookends;
    }
}
