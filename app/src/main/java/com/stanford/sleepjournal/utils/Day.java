package com.stanford.sleepjournal.utils;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.List;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class Day extends SugarRecord {

    public static final String[] DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @Unique
    private String date;
    private String formatDate = "";

    private String inBed = "";
    private String asleep = "";
    private String awake = "";
    private String outOfBed = "";
    private String dayOfWeek = "";

    private int sleptFor = -1; //in min.
    private int nappedFor = -1; //in min.
    private int groggyFor = -1; //in min.
    private int timeAwakeNight = -1; //in min.

    public Day() {}

    public Day(String date) {
        this.date = date;
    }

    @Override
    public long save() {
        //here, calculate sleptFor and other similar variables.
        return super.save();
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getTimeAwakeAtNight() {
        return timeAwakeNight;
    }

    public void setTimeAwakeAtNight(int timeAwakeNight) {
        this.timeAwakeNight = timeAwakeNight;
    }

    public String getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    public String getAsleep() {
        return asleep;
    }

    public void setAsleep(String asleep) {
        this.asleep = asleep;
    }

    public String getAwake() {
        return awake;
    }

    public void setAwake(String awake) {
        this.awake = awake;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInBed() {
        return inBed;
    }

    public void setInBed(String inBed) {
        this.inBed = inBed;
    }

    public String getOutOfBed() {
        return outOfBed;
    }

    public void setOutOfBed(String outOfBed) {
        this.outOfBed = outOfBed;
    }

    public int getGroggyFor() {
        return groggyFor;
    }

    public void setGroggyFor(int groggyFor) {
        this.groggyFor = groggyFor;
    }

    public int getNappedFor() {
        return nappedFor;
    }

    public void setNappedFor(int nappedFor) {
        this.nappedFor = nappedFor;
    }

    public int getSleptFor() {
        return sleptFor;
    }

    public void setSleptFor(int sleptFor) {
        this.sleptFor = sleptFor;
    }

    public List<AlertnessEntry> getAlertness() {
        return AlertnessEntry.find(AlertnessEntry.class, "date = ?", date);
    }

}
