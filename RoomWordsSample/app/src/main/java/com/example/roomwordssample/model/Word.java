package com.example.roomwordssample.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word){this.mWord = word;}

    //Room expects only one constructor so use Ignore for second.
   @Ignore
    public Word(int id, @NonNull String word) {
        this.mWord = word;
        this.id = id;}

    public String getWord(){return this.mWord;}

    public int getId(){return this.id;}
}
