package com.example.ubbapp.controller;

import com.example.ubbapp.repository.AttendanceRepository;
import com.example.ubbapp.structures.SimpleCallBack;

public class AttendanceController {

    private AttendanceRepository attendanceRepository;

    public AttendanceController() {
        attendanceRepository = new AttendanceRepository();
    }

    public void getAttendance(final String studentID, String disciplineName, String type, final SimpleCallBack<String> simpleCallBack) {
        attendanceRepository.getAttendance(studentID, disciplineName, type, new SimpleCallBack<String>() {
            @Override
            public void callback(String data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
