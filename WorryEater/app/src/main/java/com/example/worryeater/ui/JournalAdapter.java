package com.example.worryeater.ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worryeater.R;
import com.example.worryeater.data.model.JournalData;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;


public class JournalAdapter extends FirestoreRecyclerAdapter<JournalData, JournalAdapter.ViewHolder> {
    private onItemClickListener listener ;
    private Context context;

    public JournalAdapter(@NonNull FirestoreRecyclerOptions<JournalData> options) {
        super(options);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journal_row, parent, false);
        return new ViewHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
        notifyItemRemoved(position);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull JournalData model) {

        String imageUrl;

        holder.title.setText(model.getTitle());
        holder.thoughts.setText(model.getThoughts());
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                model.getTimeAdded().getSeconds() *1000);
        holder.dateAdded.setText(timeAgo);
        holder.name.setText(model.getUsername());

        imageUrl = model.getImageUrl();
        /*
        Use Picasso library to download image
         */

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.clouds)
                .fit()
                .centerCrop()
                .into(holder.image);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView title, thoughts, dateAdded, name;
        public ImageView image;
        //public ImageButton shareButton, deleteButton;

        //TODO are these required
        String userId;
        String username;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thoughts_list);
            dateAdded = itemView.findViewById(R.id.journal_timestamp_list);
            image = itemView.findViewById(R.id.journal_image_list);
            name = itemView.findViewById(R.id.journal_row_username);

          //  shareButton = itemView.findViewById(R.id.journal_row_share_btn);
          //  deleteButton = itemView.findViewById(R.id.journal_row_delete_btn);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null){
                listener.onItemClick(position);
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem share = menu.add(Menu.NONE, 1, 1, "Share post");
            MenuItem edit = menu.add(Menu.NONE, 2, 2, "Edit post");
            MenuItem delete = menu.add(Menu.NONE, 3, 3, "Delete post");
            share.setOnMenuItemClickListener(this);
            edit.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && listener != null) {
                switch (item.getItemId()) {
                    case 1:
                        listener.onShareClick(position);
                        return true;
                    case 2:
                        listener.onEditClick(position);
                        return true;
                    case 3:
                        listener.onDeleteClick(position);
                        return true;

                }
            }
            return false;
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onEditClick(int position);
        void onShareClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onDataChanged() {
        // Called each time there is a new query snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }

    @Override
    public void onError(FirebaseFirestoreException e) {
        // Called when there is an error getting a query snapshot. You may want to update
        // your UI to display an error message to the user.
        // ...
    }
}
