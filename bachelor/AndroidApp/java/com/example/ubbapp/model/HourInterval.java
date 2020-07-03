package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The HourInterval class describes the start and the end hour
 */
public class HourInterval implements Parcelable {

    /**
     * The attributes of the HourInterval class
     */
    private String startHour, endHour;

    /**
     * The constructor of the HourInterval class
     *
     * @param startHour A string, the start hour of the HourInterval
     * @param endHour   A string, the end hour of the HourInterval
     */
    public HourInterval(String startHour, String endHour) {
        this.startHour = startHour;
        this.endHour = endHour;
    }

    protected HourInterval(Parcel dest) {
        this.startHour = dest.readString();
        this.endHour = dest.readString();
    }

    /**
     * The getStartHour method returns the start hour of the HourInterval
     *
     * @return A string, the start hour of the HourInterval
     */
    public String getStartHour() {
        return startHour;
    }

    /**
     * The setStartHour method sets a new start hour for the HourInterval
     *
     * @param startHour A string, which will be the new start hour of the HourInterval
     */
    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    /**
     * The getEndHour method returns the end hour of the HourInterval
     *
     * @return A string, the end hour of the HourInterval
     */
    public String getEndHour() {
        return endHour;
    }

    /**
     * The setEndHour method sets a new end hour for the HourInterval
     *
     * @param endHour A string, which will be the new end hour of the HourInterval
     */
    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return startHour + " - " + endHour;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startHour);
        dest.writeString(endHour);
    }

    public static final Creator<HourInterval> CREATOR = new Creator<HourInterval>() {
        @Override
        public HourInterval createFromParcel(Parcel source) {
            return new HourInterval(source);
        }

        @Override
        public HourInterval[] newArray(int size) {
            return new HourInterval[size];
        }
    };
}
