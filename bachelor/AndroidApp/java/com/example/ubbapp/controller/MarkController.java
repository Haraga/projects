package com.example.ubbapp.controller;

import com.example.ubbapp.model.Mark;
import com.example.ubbapp.repository.MarksRepository;
import com.example.ubbapp.structures.SimpleCallBack;

public class MarkController {

    private MarksRepository marksRepository;

    public MarkController() {
        this.marksRepository = new MarksRepository();
    }

    public void getMarks(final String studentID, final String disciplineName, final SimpleCallBack<Mark> simpleCallBack) {
        marksRepository.getMarks(studentID, disciplineName, new SimpleCallBack<Mark>() {
            @Override
            public void callback(Mark data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
