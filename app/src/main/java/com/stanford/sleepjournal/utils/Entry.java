package com.stanford.sleepjournal.utils;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class Entry extends SugarRecord {
    private String date = "";
    private String hour = "";

    private int mood = 0;

    public Entry(){}

    public Entry(String date, String hour){
        this.date = date;
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        if(mood < 0) mood = 0;
        this.mood = mood;
    }

    @Override
    public String toString(){
        return date + " - " + hour + " - " + mood;
    }
}
