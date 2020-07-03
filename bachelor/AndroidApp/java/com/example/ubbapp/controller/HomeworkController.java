package com.example.ubbapp.controller;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Group;
import com.example.ubbapp.model.Homework;
import com.example.ubbapp.repository.HomeworkRepository;
import com.example.ubbapp.structures.SimpleCallBack;

public class HomeworkController {

    private HomeworkRepository repository;

    public HomeworkController() {
        this.repository = new HomeworkRepository();
    }

    public void getHomework(Group group, int currentWeek, @NonNull final
    SimpleCallBack<Homework> simpleCallBack) {
        this.repository.getHomework(group, currentWeek, new SimpleCallBack<Homework>() {
            @Override
            public void callback(Homework data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
