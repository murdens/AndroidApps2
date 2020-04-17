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
package com.example.android.quaked;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;
    /**
     * URL to query the USGS dataset for earthquake information
     */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=15";
    /**
     * Adapter for the list of earthquakes
     */
    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Kick off an {@link AsyncTask} to perform the network request
        //     EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        //   task.execute(USGS_REQUEST_URL);

        LoaderManager loaderManager = this.getLoaderManager();

        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);


        // Set the adapter on the {@link ListView}
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = mAdapter.getItem(position);

                Uri webpage = Uri.parse(currentEarthquake.geteUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle bundle) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> earthquake) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquake != null && !earthquake.isEmpty()) {
            mAdapter.addAll(earthquake);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        mAdapter.clear();
    }


    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first earthquake in the response.
     */
    //  private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

    //    protected List<Earthquake> doInBackground(String... urls) {
    // Don't perform the request if there are no URLs, or the first URL is null.
    //      if (urls.length < 1 || urls[0] == null) {
    //        return null;
    //   }
    // List<Earthquake> earthquake = QueryUtils.fetchEarthquakeData(urls[0]);
    // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
    //  return earthquake;
    // }

    /**
     * Update the screen with the given earthquake (which was the result of the
     * {@link EarthquakeAsyncTask}).
     */
    //protected void onPostExecute(List<Earthquake> earthquake) {
    //    mAdapter.clear();

    //     if (earthquake != null && !earthquake.isEmpty()) {
    //        mAdapter.addAll(earthquake);
    //      }
    // }

}



