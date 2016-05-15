package com.stanford.sleepjournal.utils;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class Editable {

    public static final int TYPE_TIME = 0;
    public static final int TYPE_NUMBER = 1;
    public static final int TYPE_NAME = 1;

    private String mName;
    private String mDefault;
    private int mType;

    public Editable(String name, int type){
        mName = name;
        mType = type;
        mDefault = "?";
    }

    public Editable(String name, int type, String defaultFill){
        mName = name;
        mType = type;
        mDefault = defaultFill;
    }

    public String getmDefault() {
        return mDefault;
    }

    public void setmDefault(String mDefault) {
        this.mDefault = mDefault;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
