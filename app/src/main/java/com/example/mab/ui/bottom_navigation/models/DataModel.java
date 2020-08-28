package com.example.mab.ui.bottom_navigation.models;

public class DataModel {
    private String afishaUrl;
    private String title;
    private int id;

    public DataModel(String afishaUrl, String title, int id) {
        this.afishaUrl = afishaUrl;
        this.title = title;
        this.id = id;
    }

    public String getAfishaUrl() {
        return afishaUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
