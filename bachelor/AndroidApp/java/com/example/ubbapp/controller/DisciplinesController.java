package com.example.ubbapp.controller;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Discipline;
import com.example.ubbapp.model.Group;
import com.example.ubbapp.repository.ScheduleRepository;
import com.example.ubbapp.structures.SimpleCallBack;

public class DisciplinesController {

    private ScheduleRepository repository;

    public DisciplinesController() {
        repository = new ScheduleRepository();
    }

    public void getDisciplinesNames(final Group group, @NonNull final SimpleCallBack<Discipline> simpleCallBack) {
        repository.getDisciplinesNames(group, new SimpleCallBack<Discipline>() {
            @Override
            public void callback(Discipline data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
