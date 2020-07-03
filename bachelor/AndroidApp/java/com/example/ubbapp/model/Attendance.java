package com.example.ubbapp.model;

/**
 * The Attendance class describes the attendance of a Student
 * to a Laboratory or Seminary of a Discipline
 */
public class Attendance {

    /**
     * The attributes of the Attendance class
     */
    private String attendanceID;
    private boolean attended;

    /**
     * The constructor of the Attendance class
     *
     * @param attendanceID An integer, the number of the Attendance
     * @param attended     A boolean, true if the Student attended, false if not
     */
    public Attendance(String attendanceID, boolean attended) {
        this.attendanceID = attendanceID;
        this.attended = attended;
    }

    /**
     * The getAttendanceNumber method returns the number of the Attendance
     *
     * @return An integer, number of the Attendance
     */
    public String getAttendanceNumber() {
        return attendanceID;
    }

    /**
     * The setAttendanceNumber method sets a new number for the Attendance
     *
     * @param attendanceID An integer, which will be the new Attendance number
     */
    public void setAttendanceNumber(String attendanceID) {
        this.attendanceID = attendanceID;
    }

    /**
     * The isAttended method returns if a Student attend or not
     *
     * @return A boolean, true if the Student attended, false if not
     */
    public boolean isAttended() {
        return attended;
    }

    /**
     * The setAttended methos sets a new value for the Attendance
     *
     * @param attended A boolean, true if the Student attended, false if not
     */
    public void setAttended(boolean attended) {
        this.attended = attended;
    }
}
