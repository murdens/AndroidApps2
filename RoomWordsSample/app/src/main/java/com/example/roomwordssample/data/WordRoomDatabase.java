package com.example.roomwordssample.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.roomwordssample.model.Word;

@Database(entities = {Word.class}, version = 2, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static WordRoomDatabase INSTANCE;

    public abstract WordDao wordDao();

    public static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                /**
                 * Populate the database in the background.
                 */
                class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

                    private final WordDao mDao;
                    String[] words = {"dolphin", "crocodile", "cobra"};

                    PopulateDbAsync(WordRoomDatabase db) {
                        mDao = db.wordDao();
                    }

                    @Override
                    protected Void doInBackground(final Void... params) {

                        // If we have no words, then create the initial list of words
                        if (mDao.getAnyWord().length < 1) {
                            for (int i = 0; i <= words.length - 1; i++) {
                                Word word = new Word(words[i]);
                                mDao.insert(word);
                            }
                        }
                        return null;
                    }
                }

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
}
