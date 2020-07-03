package com.example.ubbapp.controller;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.ForAttendance;
import com.example.ubbapp.repository.ForAttendanceRepository;
import com.example.ubbapp.structures.SimpleCallBack;

public class ForAttendanceController {

    private ForAttendanceRepository forAttendanceRepository;

    public ForAttendanceController() {
        this.forAttendanceRepository = new ForAttendanceRepository();
    }

    public void getForAttendance(final String disciplineName, final String code, @NonNull final SimpleCallBack<ForAttendance> simpleCallBack) {
        this.forAttendanceRepository.getForAttendance(disciplineName, code, new SimpleCallBack<ForAttendance>() {
            @Override
            public void callback(ForAttendance data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
