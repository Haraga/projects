package com.example.ubbapp.controller;

import com.example.ubbapp.repository.CurrentWeekRepository;
import com.example.ubbapp.structures.SimpleCallBack;

public class CurrentWeekController {

    private CurrentWeekRepository repository;

    public CurrentWeekController() {
        repository = new CurrentWeekRepository();
    }

    public void getCurrentWeek(final SimpleCallBack<String> simpleCallBack) {
        repository.getCurrentWeek(new SimpleCallBack<String>() {
            @Override
            public void callback(String data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
