package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Address class describes the location of an address
 */
public class Address implements Parcelable {

    /**
     * The attributes of the Address class
     */
    private String name, room;
    private double latitude, longitude;

    /**
     * The constructor of the Address class
     *
     * @param latitude  A double, the latitude of the Address
     * @param longitude A double, the longitude of the Address
     * @param name      A string, the name of the Address
     * @param room      A string, the name of the room from the Address
     */
    public Address(double latitude, double longitude, String name, String room) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.room = room;
    }

    protected Address(Parcel dest) {
        this.name = dest.readString();
        this.room = dest.readString();
        this.latitude = dest.readDouble();
        this.longitude = dest.readDouble();
    }

    /**
     * The getLatitude method returns the latitude of the Address
     *
     * @return A double, the latitude of the Address
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * The setLatitude method sets a new latitude for the Address
     *
     * @param latitude A double, which will be the new latitude of the Address
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * The getLongitude method returns the longitude of the Address
     *
     * @return A double, the longitude of the Address
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * The setLongitude method sets a new longitude for the Address
     *
     * @param longitude A double, which will be the new longitude of the Address
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * The getName method returns the name of the Address
     *
     * @return A string, the name of the Address
     */
    public String getName() {
        return name;
    }

    /**
     * The setName method sets a new name for the Address
     *
     * @param name A string, which will be the new name of the Address
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(room);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
