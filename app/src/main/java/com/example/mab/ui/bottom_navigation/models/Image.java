package com.example.mab.ui.bottom_navigation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("medium")
    @Expose
    private String medium;
    @SerializedName("original")
    @Expose
    private String original;

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getOriginal() {
        return original;
    }

    public Image setOriginal(String original) {
        this.original = original;
        return this;
    }

    @Override
    public String toString() {
        return "Image{" +
                "medium='" + medium + '\'' +
                ", original='" + original + '\'' +
                '}';
    }
}