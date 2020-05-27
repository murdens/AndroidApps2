package com.example.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private SharedPreferences sharedPreferences;

    public SharedPrefs(Activity activity){
        this.sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
    }

    public void saveHighScore(int score){
        int currentScore = score;
        int lastScore = sharedPreferences.getInt("high_score", 0);
        if(currentScore>lastScore){
            //new highest score and save it
            sharedPreferences.edit().putInt("high_score", currentScore).apply();
        }
    }
    public int getHighScore(){
        return sharedPreferences.getInt("high_score",0);
    }

    public  void setState(int index) {
       sharedPreferences.edit().putInt("index_state", index).apply();
    }

    public int getState() {
        return sharedPreferences.getInt("index_state", 0);
    }

}