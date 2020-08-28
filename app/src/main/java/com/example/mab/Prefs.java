package com.example.mab;

import android.content.SharedPreferences;

public class Prefs {

    private SharedPreferences sharedPreferences;

    public Prefs(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void isShown(boolean value) {
        sharedPreferences
                .edit()
                .putBoolean("isShown", value)
                .apply();
    }

    boolean isShown(){
        return sharedPreferences.getBoolean("isShown", false);
    }

}
