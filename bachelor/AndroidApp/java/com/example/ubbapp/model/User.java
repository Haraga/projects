package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String email, password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected User(Parcel dest) {
        this.email = dest.readString();
        this.password = dest.readString();
    }

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
