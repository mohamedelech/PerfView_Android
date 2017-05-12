package com.example.mohamedelech.perfview;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Date;

/**
 * Created by mohamed.elech on 11.05.2017.
 */

public class Agenda {

    private Integer id=null;
    private Long date=null;

    public Agenda() {
    }

    public Agenda(Integer id, Long date) {
        this.id = id;
        this.date = date;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public String toString() {
        android.icu.text.SimpleDateFormat sdf = new android.icu.text.SimpleDateFormat("dd MM yyyy");
        String dateString = sdf.format(date + 86400000);
        return dateString;
    }
}
