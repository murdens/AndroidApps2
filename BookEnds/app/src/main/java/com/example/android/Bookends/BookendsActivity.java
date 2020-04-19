/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/"LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.Bookends;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Loader;
import android.app.LoaderManager;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookendsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Bookends>> {

    private static final String LOG_TAG = BookendsLoader.class.getName();

    // set variable for empty state
    private TextView emptyView;
    //set variable for progress bar
    private ProgressBar progressBar;

    private static final int BOOKSEARCH_LOADER_ID = 1;
    /**
     * URL to query the Google Books volume API
     */
    private static final String USGS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?q=gaiman&maxResults=15";
    /**
     * Adapter for the list of books
     */
    private BookendsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookends_activity);

        // check internet connection, if not connected show empty state no internet connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = this.getLoaderManager();
            //initialise loaderManager with ID
            loaderManager.initLoader(BOOKSEARCH_LOADER_ID, null, this);

        } else {
            // Find reference to empty state TextView
            emptyView = (TextView) findViewById(R.id.empty);
            // empty state to display connection error.
            emptyView.setText(R.string.no_net);

            // Find reference to the {@link loading_spinner.
            progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
            progressBar.setVisibility(progressBar.GONE);
        }

        // Kick off an {@link AsyncTask} to perform the network request
        //BookendsAsyncTask task = new BookendsAsyncTask();
        //task.execute(USGS_REQUEST_URL);

        // Find a reference to the {@link ListView} in the layout
        ListView BookendsListView = (ListView) findViewById(R.id.list);
        emptyView = (TextView) findViewById(R.id.empty);
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        // Create a new {@link ArrayAdapter} of Bookends
        mAdapter = new BookendsAdapter(this, new ArrayList<Bookends>());

        // so the list can be populated in the user interface
        BookendsListView.setAdapter(mAdapter);
        BookendsListView.setEmptyView(emptyView);

        // Set the adapter on the {@link ListView}
        BookendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bookends currentBookends = mAdapter.getItem(position);

                Uri webpage = Uri.parse(currentBookends.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

    @NonNull
    @Override
    public Loader<List<Bookends>> onCreateLoader(int i, @Nullable Bundle bundle) {

        return new BookendsLoader(this, USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Bookends>> loader, List<Bookends> bookends) {
        //set empty state text to no Bookends found.
        emptyView.setText(R.string.empty);

        //do not show spinner when empty state displayed.
        progressBar.setVisibility(findViewById(R.id.loading_spinner).GONE);

        // Clear the adapter of previous Bookends data
        mAdapter.clear();

        // If there is a valid list of {@link Bookends}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (bookends != null && !bookends.isEmpty()) {
            mAdapter.addAll(bookends);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Bookends>> loader) {

        mAdapter.clear();
    }


    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first Bookends in the response.
     */
    // private class BookendsAsyncTask extends AsyncTask<String, Void, List<Bookends>> {

    //   protected List<Bookends> doInBackground(String... urls) {
    // Don't perform the request if there are no URLs, or the first URL is null.
    //     if (urls.length < 1 || urls[0] == null) {
    //       return null;
    //  }
    // List<Bookends> Bookends = QueryUtils.fetchBookendsData(urls[0]);
    // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
    //       return Bookends;
    //  }

    /**
     * Update the screen with the given Bookends (which was the result of the
     * {@link BookendsAsyncTask}).
     */
    //   protected void onPostExecute(List<Bookends> Bookends) {
    //    mAdapter.clear();

    //          if (Bookends != null && !Bookends.isEmpty()) {
    //            mAdapter.addAll(Bookends);
    //      }
    //  }

}





