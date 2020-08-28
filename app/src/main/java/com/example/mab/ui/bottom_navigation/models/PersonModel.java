package com.example.mab.ui.bottom_navigation.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonModel {

    @SerializedName("score")
    @Expose
    private Double score;
    @SerializedName("person")
    @Expose
    private Person person;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}