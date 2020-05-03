package com.example.android.pets;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;

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
        ImageView petImage = (ImageView) view.findViewById(R.id.image);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
        int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);
        String cursorImageURI = cursor.getString(cursor.getColumnIndexOrThrow(PetContract.PetEntry.COLUMN_PET_IMAGE));

        // Read the pet attributes from the Cursor for the current pet
        String petName = cursor.getString(nameColumnIndex);
        String petBreed = cursor.getString(breedColumnIndex);

        if (TextUtils.isEmpty(petBreed)){
            petBreed = context.getString(R.string.unknown_breed);
        }

        if (TextUtils.isEmpty(cursorImageURI)){
         cursorImageURI = "";
        }
        // Update the TextViews with the attributes for the current pet
        petImage.setImageURI(Uri.parse(cursorImageURI));
        textViewName.setText(petName);
        textViewSummary.setText(petBreed);
    }

}
