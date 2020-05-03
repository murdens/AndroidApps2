package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import data.PetContract.PetEntry;


public class PetDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "Animalinstincts.db";

    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + PetEntry.TABLE_NAME + " ("
                        + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL,"
                        + PetEntry.COLUMN_PET_SPECIES + " TEXT NOT NULL,"
                        + PetEntry.COLUMN_PET_BREED + " TEXT ,"
                        + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL,"
                        + PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0,"
                        + PetEntry.COLUMN_PET_IMAGE + " BLOB NOT NULL);";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + PetEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}

