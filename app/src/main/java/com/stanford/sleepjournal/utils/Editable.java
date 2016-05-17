package com.stanford.sleepjournal.utils;

/**
 * Created by Santiago Gutierrez on 5/14/16.
 */
public class Editable {

    public static final int TYPE_TIME = 0;
    public static final int TYPE_NUMBER = 1;

    private String mName;
    private String mDefault;
    private int mType;
    private EditableAction mAction;

    public Editable(String name, String defaultVal, int type){
        this.mDefault = defaultVal;
        this.mName = name;
        this.mType = type;
    }

    public Editable(String name, String defaultVal, int type, EditableAction action){
        this.mDefault = defaultVal;
        this.mAction = action;
        this.mName = name;
        this.mType = type;
    }

    public String getDefault() {
        return this.mDefault;
    }

    public void setDefault(String defaultVal) {
        this.mDefault = defaultVal;
    }

    public EditableAction getAction(){
        return this.mAction;
    }

    public void setSaveAction(EditableAction action){
        this.mAction = action;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public interface EditableAction {
        void saveAction(String value);
    }
}
