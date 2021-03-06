/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.animalinstincts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Loader;
import android.content.CursorLoader;
import android.app.LoaderManager;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;

import data.PetContract;
import data.PetContract.PetEntry;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = CatalogActivity.class.getName();

    private String orderBy;
    private  String filterSpecies;

    // Shared preferences object
    private SharedPreferences mPreferences;

    /**
     * Identifier for the pet data loader
     */
    private static final int PET_LOADER = 0;
    /**
     * Adapter for the ListView
     */
    PetCursorAdapter mCursorAdapter;

    /**
     * @param context
     * @param drawableId
     * @return
     */
    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {

        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId));
        return imageUri;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Obtain a reference to the SharedPreferences file for this app
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // And register to be notified of preference changes
        // So we know when the user has adjusted the query settings
        mPreferences.registerOnSharedPreferenceChangeListener(this);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the pet data
        GridView petListView = findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        mCursorAdapter = new PetCursorAdapter(this, null);
        // Attach the adapter to the ListView.
        petListView.setAdapter(mCursorAdapter);

        //enable filtering in Listview
        petListView.setTextFilterEnabled(true);

        // Start loader
        getLoaderManager().initLoader(PET_LOADER, null, this);

        // Setup the item click listener
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                Uri currentPetUri = ContentUris.withAppendedId(PetEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(getString(R.string.settings_filter_byspecies_key)) ||
                key.equals(getString(R.string.settings_order_by_key))) {

            // Clear the ListView as a new query will be kicked off
            mCursorAdapter.swapCursor(null);

            // Restart the loader to requery the database settings have been updated
            getLoaderManager().restartLoader(PET_LOADER, null, this);
        }
    }

    /**
     * Dummy data for testing.
     */
    private void insertPet() {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        Uri imageUri = getUriToDrawable(this, R.drawable.hamster);
        values.put(PetEntry.COLUMN_PET_NAME, "Harry");
        values.put(PetEntry.COLUMN_PET_SPECIES, "Hamster");
        values.put(PetEntry.COLUMN_PET_BREED, "Golden");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 2);
        values.put(PetEntry.COLUMN_PET_IMAGE, imageUri.toString());

        getContentResolver().insert(PetEntry.CONTENT_URI, values);

    }

    /**
     * check if user wants to delete all pets.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteAll_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteAllPets();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllPets() {
        /**
         * Before deleting database table values, we need to delete the image files.
         */
        String[] projection = new String[]{PetEntry.COLUMN_PET_IMAGE};
        Cursor cursor = getContentResolver().query(
                PetEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            String imageFilePath = cursor.getString(cursor.getColumnIndex(PetEntry.COLUMN_PET_IMAGE));
            File imageFile = new File(imageFilePath);
            imageFile.delete();
        }
        int rowsDeleted = getContentResolver().delete(PetEntry.CONTENT_URI, null, null);
        if (rowsDeleted == 0) {
            Toast.makeText(CatalogActivity.this, R.string.main_delete_fail, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CatalogActivity.this, R.string.main_delete_all, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertPet();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on action settings
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        orderBy = mPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        //filterBreed = sharedPrefs.getString(
        //        getString(R.string.settings_filter_bybreed_key),
        //        getString(R.string.settings_filter_by_default)
        //);

        filterSpecies = mPreferences.getString(
                getString(R.string.settings_filter_byspecies_key).trim(),
                "".trim()
        );

        Log.i(LOG_TAG, "preference: TEST" + orderBy);
        Log.i(LOG_TAG, "prefspecies: TEST" + filterSpecies);

// define a projection.
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_SPECIES,
                PetEntry.COLUMN_PET_BREED,
                PetEntry.COLUMN_PET_IMAGE
        };

        /**
         * This defines a one-element String array to contain the selection argument.
         */
        //TODO need to some how change this I think.

        String[] selectionArgs = {""};
        String selection = "";

        /**
         *If filter is the empty string, gets everything
         */
        if (TextUtils.isEmpty(filterSpecies)) {
            // Setting the selection clause to null will return all words
            selectionArgs[0] = "";

        } else {
            // Constructs a selection clause that matches the word that the user entered.
            selection = PetEntry.COLUMN_PET_SPECIES + "=?";

            // Moves the user's input string to the selection arguments.
            selectionArgs[0] = filterSpecies;

            Log.i(LOG_TAG, "TEST:species" + filterSpecies);
            Log.i(LOG_TAG, "TEST:args" + selectionArgs);
        }
        /**
         * Define a sort by column
         */
        String sortOrder;

        if (orderBy == "Name") {
            orderBy = PetEntry.COLUMN_PET_NAME;
        } else if (orderBy == "Species") {
            orderBy = PetEntry.COLUMN_PET_BREED;
        }

        // If the order is the empty string, gets everything
        if (TextUtils.isEmpty(orderBy)) {
            // Setting the sort order to null will return all db
            sortOrder = null;

        } else {
            // Constructs a selection clause that matches the word that the user entered.
            sortOrder = orderBy + " ASC";
        }

        if (filterSpecies == "") {
            String test = "empty";
            Log.i(LOG_TAG, "TEST loader" + test);
            // Loader to execute ContentProviders query method
            return new CursorLoader(this,
                    PetEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder);
        } else {
            String test = "false";
            Log.i(LOG_TAG, "TEST loader" + test);
            return new CursorLoader(this,
                    PetEntry.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder);
        }
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }

}
