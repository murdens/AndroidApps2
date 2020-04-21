package com.example.android.Bookends;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

        // Get the {@link Bookends} object located at this position in the list
        Bookends currentBookends = getItem(position);

        String requiredUrl;
        if (!currentBookends.getSmallThumbnail().isEmpty()) {
            ImageView bookImage = listItemView.findViewById(R.id.image);
            String[] parts = currentBookends.getSmallThumbnail().split(":");
            requiredUrl = parts[0] + "s:" + parts[1];
            Glide.with(getContext()).load(requiredUrl).placeholder(R.drawable.work_in_progress_icon).into(bookImage);
        }

        // Find the TextView in the list_item.xml layout with the ID authors
        TextView authorsTextView = listItemView.findViewById(R.id.authors);
        // Get the author(s) from the current Bookends object
        ArrayList<String> author = currentBookends.getAuthors();
        // set this text on the authors TextView, iterate through author array.
        authorsTextView.setText("");
        for (int j = 0; j < author.size(); j++) {
            if (j == (author.size()-1)) {
                authorsTextView.append(author.get(j));
            } else {
                authorsTextView.append(author.get(j) + ", ");
            }
        }
        // Find the TextView in the list_item.xml layout with the ID title
        TextView titleTextView = listItemView.findViewById(R.id.title);
        // Get the title from the current Bookends object
        String title = currentBookends.getTitle();
        // set this text on the title TextView
        titleTextView.setText(title);

        // Find the TextView in the list_item.xml layout with the ID rating
        TextView ratingView = listItemView.findViewById(R.id.rating);
        // Format the rating to show 1 decimal place
        String formattedRating = formatRating(currentBookends.getAverageRating());
        // Display the rating of the current book in that TextView
        ratingView.setText(formattedRating);

        // Return the whole list item layout (containing 3 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }

    /**
     * Return the formatted rating string showing 1 decimal place (i.e. "3.2")
     * from a decimal average rating value
     */
    private String formatRating(double averageRating) {
        DecimalFormat ratingFormat = new DecimalFormat("0.0");
        return ratingFormat.format(averageRating);
    }

}
