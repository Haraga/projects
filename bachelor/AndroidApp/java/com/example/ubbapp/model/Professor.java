package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Professor class describes a professor that has
 * email, first name, last name, address, phone number and web
 */
public class Professor implements Parcelable {

    /**
     * The Professor class attributes
     */
    private String email, title, firstName, lastName, phoneNumber, web;

    /**
     * The Professor class constructor
     *
     * @param email       A string, the email of the Professor
     * @param firstName   A string, the first name of the Professor
     * @param lastName    A string, the last name of the Professor
     * @param phoneNumber A string, the phone number of the Professor
     * @param web         A string, the web address of the Professor
     * @param title       A string, the title of the Professor
     */
    public Professor(String email, String firstName, String lastName, String phoneNumber, String web, String title) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.web = web;
        this.title = title;
    }

    protected Professor(Parcel dest) {
        this.email = dest.readString();
        this.title = dest.readString();
        this.firstName = dest.readString();
        this.lastName = dest.readString();
        this.phoneNumber = dest.readString();
        this.web = dest.readString();
    }

    /**
     * The getEmail method returns the email of the Professor
     *
     * @return A string, email of the Professor
     */
    public String getEmail() {
        return email;
    }

    /**
     * The setEmail method sets a new email for the Professor
     *
     * @param email A string, which will be the new email of the Professor
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * The getFirstName method returns the first name of the Professor
     *
     * @return A string, first name of the Professor
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The setFirstName method sets a new first name for the Professor
     *
     * @param firstName A string, which will be the new first name of the Professor
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * The getLastName method returns the last name of the Professor
     *
     * @return A string, last name of the Professor
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The setLastName method sets a new last name for the Professor
     *
     * @param lastName A string, which will be the new last name of the Professor
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * The getPhoneNumber method returns the phone number of the Professor
     *
     * @return A string, the phone number of the Professor
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * The setPhoneNumber method sets a new phone number for the Professor
     *
     * @param phoneNumber A string, which will be the new phone number of the Professor
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * The getWeb method returns the web address of the Professor
     *
     * @return A string, thw web address of the Professor
     */
    public String getWeb() {
        return web;
    }

    /**
     * The setWeb method sets a new web address for the Professor
     *
     * @param web A string, which will be the new web address of the Professor
     */
    public void setWeb(String web) {
        this.web = web;
    }

    /**
     * The getTitle method returns the title of the Professor
     *
     * @return A string, the title of the Professor
     */
    public String getTitle() {
        return title;
    }

    /**
     * The setTitle method sets a new title for the Professor
     *
     * @param title A strins, which will be the new title of the Professor
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(title);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(phoneNumber);
        dest.writeString(web);
    }

    public static final Creator<Professor> CREATOR = new Creator<Professor>() {
        @Override
        public Professor createFromParcel(Parcel source) {
            return new Professor(source);
        }

        @Override
        public Professor[] newArray(int size) {
            return new Professor[size];
        }
    };
}
