package com.example.mab.ui.bottom_navigation.models;

import java.util.List;

public class AnilistTypeModel {
    private String title;
    private List<DataModel> data;

    public AnilistTypeModel(String title, List<DataModel> data) {
        this.title = title;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public List<DataModel> getData() {
        return data;
    }
}
