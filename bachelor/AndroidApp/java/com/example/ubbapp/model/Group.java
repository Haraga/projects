package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Parcelable {

    private String group, semigroup;

    public Group() {
    }

    public Group(String group, String semigroup) {
        this.group = group;
        this.semigroup = semigroup;
    }

    protected Group(Parcel dest) {
        this.group = dest.readString();
        this.semigroup = dest.readString();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSemigroup() {
        return semigroup;
    }

    public void setSemigroup(String semigroup) {
        this.semigroup = semigroup;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(group);
        dest.writeString(semigroup);
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel source) {
            return new Group(source);
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };
}
