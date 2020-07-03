package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ubbapp.structures.WeekDays;

/**
 * The Discipline class describes the information for a discipline
 * laboratory, seminary or course
 */
public class Discipline implements Parcelable {

    /**
     * The attributes of the Discipline class
     */
    private String name, fullName, type;
    private HourInterval interval;
    private Professor professor;
    private WeekDays day;
    private int weekNumber;
    private Address address;

    /**
     * The default constructor of the Discipline class
     */
    public Discipline() {
    }

    /**
     * The constructor of the Discipline which initialize the attributes of the
     * class
     *
     * @param name       A string, the name of the discipline
     * @param interval   A HourInterval object, the hour interval in which the discipline is sustained
     * @param professor  Professor object, object which holds the information about the
     *                   professor of the Discipline
     * @param day        WeekDays object, object which represents the week days
     * @param weekNumber An integer, which is the number of the week
     * @param address    Address object, which holds the information about the address
     * @param fullName   A string, the full name of the discipline
     */
    public Discipline(String name, HourInterval interval, Professor professor, WeekDays day
            , int weekNumber, Address address, String type, String fullName) {
        this.name = name;
        this.interval = interval;
        this.professor = professor;
        this.day = day;
        this.weekNumber = weekNumber;
        this.address = address;
        this.type = type;
        this.fullName = fullName;
    }

    protected Discipline(Parcel dest) {
        this.name = dest.readString();
        this.fullName = dest.readString();
        this.type = dest.readString();
        this.interval = (HourInterval) dest.readValue(HourInterval.class.getClassLoader());
        this.professor = (Professor) dest.readValue(Professor.class.getClassLoader());
        this.day = (WeekDays) dest.readValue(WeekDays.class.getClassLoader());
        this.address = (Address) dest.readValue(Address.class.getClassLoader());
        this.weekNumber = dest.readInt();
    }

    /**
     * The getName method returns the name of the Discipline
     *
     * @return A string, name of the Discipline
     */
    public String getName() {
        return name;
    }

    /**
     * The setName method sets a new name for the Discipline
     *
     * @param name A string, which will be the new name of the Discipline
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getHours method returns the hour interval in which the Discipline is sustained
     *
     * @return A HourInterval object, hours interval of the Discipline
     */
    public HourInterval getInterval() {
        return interval;
    }

    /**
     * The setHours method sets a new hour interval for the Discipline
     *
     * @param interval A HourInterval object, which will be the new hour interval of
     *                 the Discipline
     */
    public void setInterval(HourInterval interval) {
        this.interval = interval;
    }

    /**
     * The getProfessor method returns the professor of the Discipline
     *
     * @return A Professor object, professor of the Discipline
     */
    public Professor getProfessor() {
        return professor;
    }

    /**
     * The setProfessor method sets a new professor for the Discipline
     *
     * @param professor A Professor object, which will be the new professor of the
     *                  Discipline
     */
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    /**
     * The getDay method returns the day in which the Discipline is sustained
     *
     * @return A WeekDays object, day of in which the Discipline is sustained
     */
    public WeekDays getDay() {
        return day;
    }

    /**
     * The setDay method sets a new day in which the Discipline is sustained
     *
     * @param day A WeekDays object, which will be the new day the Discipline is
     *            sustained
     */
    public void setDay(WeekDays day) {
        this.day = day;
    }

    /**
     * The getWeekNumber method returns the week number of the Discipline
     *
     * @return An integer, the week number of the Discipline
     */
    public int getWeekNumber() {
        return weekNumber;
    }

    /**
     * The setWeekNumber method sets a new week number for the Discipline
     *
     * @param weekNumber An integer, which will be the new week number of the Discipline
     */
    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    /**
     * The getAddress method return the address where the Discipline is sustained
     *
     * @return An Address object, the address of the Discipline
     */
    public Address getAddress() {
        return address;
    }

    /**
     * The setAddress method sets a new address for the Discipline
     *
     * @param address An Address object, which will be the new address of the Discipline
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * The getType method returns the name of the type of the Discipline
     *
     * @return A string, the name of the type of the Discipline
     */
    public String getType() {
        return type;
    }

    /**
     * The setType method sets a new name of the type of the Discipline
     *
     * @param type A string, which will be the new name of the type of the Discipline
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The getFullName method returns the full name of the Discipline
     *
     * @return A string, the full name of the Discipline
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * The setFullName method sets a new full name of the Discipline
     *
     * @param fullName A string, which will be the new full name of the Discipline
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeString(type);
        dest.writeValue(interval);
        dest.writeValue(professor);
        dest.writeValue(day);
        dest.writeValue(address);
        dest.writeInt(weekNumber);
    }

    public static final Creator<Discipline> CREATOR = new Creator<Discipline>() {
        @Override
        public Discipline createFromParcel(Parcel source) {
            return new Discipline(source);
        }

        @Override
        public Discipline[] newArray(int size) {
            return new Discipline[size];
        }
    };
}
