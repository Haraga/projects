package com.example.ubbapp.controller;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Discipline;
import com.example.ubbapp.model.Group;
import com.example.ubbapp.repository.ScheduleRepository;
import com.example.ubbapp.structures.SimpleCallBack;

public class ScheduleController {

    private ScheduleRepository scheduleRepository;


    public ScheduleController() {
        scheduleRepository = new ScheduleRepository();
    }

    public void getDisciplinesDailySchedule(Group group, String currentDay, int currentWeek, @NonNull final SimpleCallBack<Discipline> simpleCallBack) {
        scheduleRepository.getDisciplinesDailySchedule(group, currentDay, currentWeek, new SimpleCallBack<Discipline>() {
            @Override
            public void callback(Discipline data) {
                simpleCallBack.callback(data);
            }
        });
    }

    public void getDisciplineWeeklySchedule(Group group, String currentDay, int currentWeek, @NonNull final SimpleCallBack<int[]> simpleCallBack) {
        scheduleRepository.getDisciplinesWeeklySchedule(group, currentDay, currentWeek, new SimpleCallBack<int[]>() {
            @Override
            public void callback(int[] data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
