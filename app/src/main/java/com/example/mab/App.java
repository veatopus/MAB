package com.example.mab;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mab.data.AnilistService;
import com.example.mab.data.TvMazeService;

public class App extends Application {

    private static App instance;
    private AnilistService anilistService;
    private TvMazeService tvMazeService;
    private Prefs prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        anilistService = new AnilistService();
        prefs = new Prefs(getSharedPreferences("Potom",Context.MODE_PRIVATE));
        tvMazeService = new TvMazeService();
    }

    public static App getInstance() {
        return instance;
    }

    public AnilistService getAnilistService() {
        return anilistService;
    }

    public Prefs getPrefs() {
        return prefs;
    }

    public TvMazeService getTvMazeService() {
        return tvMazeService;
    }
}
