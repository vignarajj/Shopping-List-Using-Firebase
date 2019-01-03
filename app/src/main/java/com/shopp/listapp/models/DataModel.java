package com.shopp.listapp.models;

public class DataModel {
    String mType = "";
    double aAmount = 0;
    String mNote = "";
    String mDate = "";
    String mId = "";

    public DataModel(){

    }

    public DataModel(String mType, double aAmount, String mNote, String mDate, String mId) {
        this.mType = mType;
        this.aAmount = aAmount;
        this.mNote = mNote;
        this.mDate = mDate;
        this.mId = mId;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public double getaAmount() {
        return aAmount;
    }

    public void setaAmount(double aAmount) {
        this.aAmount = aAmount;
    }

    public String getmNote() {
        return mNote;
    }

    public void setmNote(String mNote) {
        this.mNote = mNote;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
