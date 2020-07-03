package com.example.ubbapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Homework class describes a homework that has
 * discipline, text, assignedWeek, toCompleteWeek,
 * laboratoryHomework, seminaryHomework
 */
public class Homework implements Parcelable {

    /**
     * The attributes of the Homework class
     */
    private String discipline, text, title, homeworkDownloadLink;
    private int assignedWeek, toCompleteWeek;
    private boolean laboratoryHomework, seminaryHomework;

    /**
     * The Homework class constructor
     *
     * @param discipline           A string, the name of the discipline
     * @param text                 A string, the text of the homework
     * @param title                A string, the name of the homework
     * @param homeworkDownloadLink A string, the link for the file download
     * @param assignedWeek         An integer, the number of week in which the homework was
     *                             assigned
     * @param toCompleteWeek       An integer, the number of week in which the homework
     *                             should be completed
     * @param laboratoryHomework   A boolean, true for laboratory homework, false if not
     * @param seminaryHomework     A boolean, true for seminary homework, false if not
     */
    public Homework(String discipline, String text, String title, String homeworkDownloadLink, int assignedWeek, int toCompleteWeek, boolean laboratoryHomework, boolean seminaryHomework) {
        this.discipline = discipline;
        this.text = text;
        this.title = title;
        this.homeworkDownloadLink = homeworkDownloadLink;
        this.assignedWeek = assignedWeek;
        this.toCompleteWeek = toCompleteWeek;
        this.laboratoryHomework = laboratoryHomework;
        this.seminaryHomework = seminaryHomework;
    }

    protected Homework(Parcel dest) {
        this.discipline = dest.readString();
        this.text = dest.readString();
        this.title = dest.readString();
        this.homeworkDownloadLink = dest.readString();
        this.assignedWeek = dest.readInt();
        this.toCompleteWeek = dest.readInt();
        this.laboratoryHomework = dest.readByte() != 0;
        this.seminaryHomework = dest.readByte() != 0;
    }

    /**
     * The getDiscipline method returns the name of the discipline of the Homework
     *
     * @return A string, the name of the discipline of the Homework
     */
    public String getDiscipline() {
        return discipline;
    }

    /**
     * The setDiscipline method sets a new name for the discipline of the Homework
     *
     * @param discipline A string, which will be the new name of the Homework
     */
    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    /**
     * The getText method returns the content of the Homework
     *
     * @return A string, the content of the Homework
     */
    public String getText() {
        return text;
    }

    /**
     * The setText method sets a new content for the Homework
     *
     * @param text A string, which will be the new content of the Homework
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * The getAssignedWeek method returns the week number in which the Homework
     * was assigned
     *
     * @return An integer, the week number of the assigned Homework
     */
    public int getAssignedWeek() {
        return assignedWeek;
    }

    /**
     * The setAssignedWeek method sets a new week number in which the Homework
     * was assigned
     *
     * @param assignedWeek An integer, which will be the new week number of the
     *                     assigned Homework
     */
    public void setAssignedWeek(int assignedWeek) {
        this.assignedWeek = assignedWeek;
    }

    /**
     * The getToCompleteWeek method returns the week number in which the Homework
     * should be completed
     *
     * @return An integer, the week number for completion of Homework
     */
    public int getToCompleteWeek() {
        return toCompleteWeek;
    }

    /**
     * The setToCompleteWeek method sets a new week number in which the Homework
     * should be completed
     *
     * @param toCompleteWeek An integer, which will be the new week number for the
     *                       completion of Homework
     */
    public void setToCompleteWeek(int toCompleteWeek) {
        this.toCompleteWeek = toCompleteWeek;
    }

    /**
     * The isLaboratoryHomework method returns if the Homework is for laboratory
     * or not
     *
     * @return true, if the Homework is for laboratory, false if not
     */
    public boolean isLaboratoryHomework() {
        return laboratoryHomework;
    }

    /**
     * The setLaboratoryHomework sets if the Homework is for laboratory or not
     *
     * @param laboratoryHomework A boolean, true if the Homework is for laboratory,
     *                           false if not
     */
    public void setLaboratoryHomework(boolean laboratoryHomework) {
        this.laboratoryHomework = laboratoryHomework;
    }

    /**
     * The isSeminaryHomework method returns if the Homework is for seminary
     * or not
     *
     * @return true, if the Homework is for seminary, false if not
     */
    public boolean isSeminaryHomework() {
        return seminaryHomework;
    }

    /**
     * The setSeminaryHomework sets if the Homework is for seminary or not
     *
     * @param seminaryHomework A boolean, true if the Homework is for seminary,
     *                         false if not
     */
    public void setSeminaryHomework(boolean seminaryHomework) {
        this.seminaryHomework = seminaryHomework;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomeworkDownloadLink() {
        return homeworkDownloadLink;
    }

    public void setHomeworkDownloadLink(String homeworkDownloadLink) {
        this.homeworkDownloadLink = homeworkDownloadLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(discipline);
        dest.writeString(text);
        dest.writeString(title);
        dest.writeString(homeworkDownloadLink);
        dest.writeInt(assignedWeek);
        dest.writeInt(toCompleteWeek);
        dest.writeByte((byte) (laboratoryHomework ? 1 : 0));
        dest.writeByte((byte) (seminaryHomework ? 1 : 0));
    }

    public static final Creator<Homework> CREATOR = new Creator<Homework>() {
        @Override
        public Homework createFromParcel(Parcel source) {
            return new Homework(source);
        }

        @Override
        public Homework[] newArray(int size) {
            return new Homework[size];
        }
    };
}
