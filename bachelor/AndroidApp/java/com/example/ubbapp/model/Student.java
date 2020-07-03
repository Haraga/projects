package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/***
 *  The Student class describes a student that has
 *  email. first name, last name, group and semigroup
 *  The class is serializable, so it can be passed from an Activity to another
 */
public class Student implements Parcelable {

    /**
     * The attributes of the Student class
     */
    private String email, firstName, lastName;
    private Group group;

    /**
     * The Student class constructor
     *
     * @param email     A string, the email of the Student
     * @param firstName A string, the first name of the Student
     * @param lastName  A string, the last name of the Student
     * @param group     A string, the group number of the Student
     */
    public Student(String email, String firstName, String lastName, Group group) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
    }

    protected Student(Parcel dest) {
        this.email = dest.readString();
        this.firstName = dest.readString();
        this.lastName = dest.readString();
        this.group = (Group) dest.readValue(Group.class.getClassLoader());
    }

    /**
     * The getEmail method returns the email of the Student
     *
     * @return A string, email of the Student
     */
    public String getEmail() {
        return email;
    }

    /**
     * The setEmail method sets a new email for the Student
     *
     * @param email A string, which will be the new email of the Student
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * The getFirstName method returns the first name of the Student
     *
     * @return A string, first name of the Student
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The setFirstName method sets a new first name for the Student
     *
     * @param firstName A string, which will be the new first name of the Student
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * The getLastName method returns the last name of the Student
     *
     * @return A string, last name of the Student
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The setLastName method sets a new last name for the Student
     *
     * @param lastName A string, which will be the new last name of the Student
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * The getGroup method returns the group number of the Student
     *
     * @return A string, group number of the Student
     */
    public Group getGroup() {
        return group;
    }

    /**
     * The setGroup method sets the group number for the Student
     *
     * @param group A string, which will be the new group number of the Student
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeValue(group);
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
