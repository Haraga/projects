package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Mark class describes the mark a Student got on a homework
 */
public class Mark implements Parcelable {

    /**
     * The attributes of the Mark class
     */
    private String remark;
    private float mark;

    /**
     * The constructor of the Mark class
     *
     * @param remark A string, the content of the remark
     * @param mark   A float, the number of the Mark
     */
    public Mark(String remark, float mark) {
        this.remark = remark;
        this.mark = mark;
    }

    protected Mark(Parcel dest) {
        this.remark = dest.readString();
        this.mark = dest.readFloat();
    }

    /**
     * The getRemark method returns the content of the remark of the Mark
     *
     * @return A string, the content of the remark of the Mark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * The setRemark method sets a new content for the remark of the Mark
     *
     * @param remark A string, which will be the new content of the remark of the Mark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * The getMark method returns the mark of the Mark
     *
     * @return An integer, number of the Mark
     */
    public float getMark() {
        return mark;
    }

    /**
     * The setMark method sets a new number for the Mark
     *
     * @param mark An integer, which will be the new number of the Mark
     */
    public void setMark(float mark) {
        this.mark = mark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(remark);
        dest.writeFloat(mark);
    }

    public static final Creator<Mark> CREATOR = new Creator<Mark>() {
        @Override
        public Mark createFromParcel(Parcel source) {
            return new Mark(source);
        }

        @Override
        public Mark[] newArray(int size) {
            return new Mark[size];
        }
    };
}
