package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ForAttendance implements Parcelable {

    private String code;
    private String disciplineName;
    private double latitude, longitude;
    private String type;
    private String typeNumber;

    public ForAttendance(String code, String disciplineName, double latitude, double longitude, String type, String typeNumber) {
        this.code = code;
        this.disciplineName = disciplineName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.typeNumber = typeNumber;
    }

    protected ForAttendance(Parcel in) {
        code = in.readString();
        disciplineName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        type = in.readString();
        typeNumber = in.readString();
    }

    public ForAttendance() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(String typeNumber) {
        this.typeNumber = typeNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(disciplineName);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(type);
        dest.writeString(typeNumber);
    }

    public static final Creator<ForAttendance> CREATOR = new Creator<ForAttendance>() {
        @Override
        public ForAttendance createFromParcel(Parcel in) {
            return new ForAttendance(in);
        }

        @Override
        public ForAttendance[] newArray(int size) {
            return new ForAttendance[size];
        }
    };
}
