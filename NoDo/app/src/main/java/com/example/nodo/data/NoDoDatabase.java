package com.example.nodo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.nodo.model.NoDO;

@Database(entities = {NoDO.class},version = 1)
public abstract class NoDoDatabase extends RoomDatabase {

    private static volatile NoDoDatabase INSTANCE;
    public abstract NoDoDao noDoDao();

    public static NoDoDatabase getInstance(final Context context){
        if(INSTANCE==null){
            synchronized (NoDoDatabase.class){
                if (INSTANCE==null){
                    //create our db
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                         NoDoDatabase.class, "nodo_db"   )
                            .addCallback(roomDbCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback roomDbCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void> {
        private final NoDoDao noDoDao;

        public PopulateDbAsync(NoDoDatabase db) {
            noDoDao = db.noDoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           // noDoDao.deleteAll(); // remove all items from out table
            // for testing
//            NoDO noDO = new NoDO("Buy a new carpet");
//            noDoDao.insert(noDO);
//
//            noDO = new NoDO("Get a new shed");
//            noDoDao.insert(noDO);

            return null;
        }
    }
}
