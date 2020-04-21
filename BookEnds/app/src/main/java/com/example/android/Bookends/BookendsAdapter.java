package com.example.android.Bookends;

import android.app.Activity;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import android.graphics.drawable.GradientDrawable;

import org.json.JSONArray;

public class BookendsAdapter extends ArrayAdapter<Bookends> {

    public BookendsAdapter(Activity context, ArrayList<Bookends> bookends) {
        super(context, 0, bookends);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Bookends currentBookends = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID magnitude
       // TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Format the magnitude to show 1 decimal place
       // String formattedMagnitude = formatMagnitude(currentBookends.geteMagnitude());
        // Display the magnitude of the current earthquake in that TextView
       // magnitudeView.setText(formattedMagnitude);


        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView authorsTextView = (TextView) listItemView.findViewById(R.id.authors);
        // Get the version number from the current AndroidFlavor object and
        ArrayList<String> author = currentBookends.getAuthors();
        // set this text on the number TextView
        authorsTextView.setText("");
        for (int j=0; j< author.size(); j++) {
            if (j == author.size()) {
                authorsTextView.append(author.get(j));
            } else {
                authorsTextView.append(author.get(j) + ", ");
            }
        }
        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        // Get the version number from the current AndroidFlavor object and
        String title = currentBookends.getTitle();
        // set this text on the number TextView
        titleTextView.setText(title);

        // Find the TextView in the list_item.xml layout with the ID magnitude
        TextView ratingView = (TextView) listItemView.findViewById(R.id.rating);
        // Format the magnitude to show 1 decimal place
        String formattedRating = formatRating(currentBookends.getAverageRating());
        // Display the magnitude of the current earthquake in that TextView
        ratingView.setText(formattedRating);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatRating(double averageRating) {
        DecimalFormat ratingFormat = new DecimalFormat("0.0");
        return ratingFormat.format(averageRating);
    }

}
