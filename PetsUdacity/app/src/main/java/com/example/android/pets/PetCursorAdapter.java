package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import data.PetContract;

public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.pet_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView textViewName = (TextView) view.findViewById(R.id.name);
        TextView textViewSummary = (TextView) view.findViewById(R.id.summary);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);

        // Read the pet attributes from the Cursor for the current pet
        String petName = cursor.getString(nameColumnIndex);
        String petBreed = cursor.getString(breedColumnIndex);

        if (TextUtils.isEmpty(petBreed)){
            petBreed = context.getString(R.string.unknown_breed);
        }
        // Update the TextViews with the attributes for the current pet
        textViewName.setText(petName);
        textViewSummary.setText(petBreed);
    }
}
