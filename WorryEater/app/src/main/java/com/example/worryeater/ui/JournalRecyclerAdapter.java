package com.example.worryeater.ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worryeater.R;
import com.example.worryeater.data.model.JournalData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<JournalData> journalDataList;
    private static final String TAG = "JournalRecyclerAdapter";

    public JournalRecyclerAdapter(Context context, List<JournalData> journalDataList) {
        this.context = context;
        this.journalDataList = journalDataList;
    }

    @NonNull
    @Override
    public JournalRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: action starter");

        View view = LayoutInflater.from(context)
                .inflate(R.layout.journal_row, viewGroup, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRecyclerAdapter.ViewHolder holder, int position) {
        JournalData journalData = journalDataList.get(position);
        String imageUrl;

        holder.title.setText(journalData.getTitle());
        holder.thoughts.setText(journalData.getThoughts());
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                journalData.getTimeAdded().getSeconds() *1000);
        holder.dateAdded.setText(timeAgo);
        holder.name.setText(journalData.getUsername());

        imageUrl = journalData.getImageUrl();
        /*
        Use Picasso library to download image
         */
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.clouds)
                .fit()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return journalDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, thoughts, dateAdded, name;
        public ImageView image;
        public ImageButton shareButton;

        //TODO are these required
        String userId;
        String username;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            title = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thoughts_list);
            dateAdded = itemView.findViewById(R.id.journal_timestamp_list);
            image = itemView.findViewById(R.id.journal_image_list);
            name = itemView.findViewById(R.id.journal_row_username);
            shareButton = itemView.findViewById(R.id.journal_row_share_btn);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO add share code
                    //ctx.startActivity();
                }
            });
        }
    }
}
