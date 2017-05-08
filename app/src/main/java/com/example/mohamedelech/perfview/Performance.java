package com.example.mohamedelech.perfview;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mohamed.elech on 15.04.2017.
 */

public class Performance {

    private Integer id=null;
    private String date=null;
    private String movement=null;
    private String reps=null;
    private String weight=null;
    private String photo=null;
    private String adresse=null;

    public Performance() {
    }

    public Performance(Integer id, String date, String movement, String reps, String weight, String photo, String adresse) {
        this.id = id;
        this.date = date;
        this.movement = movement;
        this.reps = reps;
        this.weight = weight;
        this.photo = photo;
        this.adresse = adresse;
    }

    public Performance(Integer id, String date, String movement, String reps, String weight) {
        this.id = id;
        this.date = date;
        this.movement = movement;
        this.reps = reps;
        this.weight = weight;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date + "\n" + weight + " Kg      " + reps + " Reps";
    }
}
