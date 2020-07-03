package com.example.ubbapp.controller;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.User;
import com.example.ubbapp.repository.StudentRepository;
import com.example.ubbapp.structures.SimpleCallBack;

public class StudentController {

    private StudentRepository repository;

    public StudentController() {
        this.repository = new StudentRepository();
    }

    public void getStudentKey(final User user, @NonNull final SimpleCallBack<String> simpleCallBack) {
        repository.getStudentKey(user, new SimpleCallBack<String>() {
            @Override
            public void callback(String data) {
                if (data != null)
                    simpleCallBack.callback(data);
            }
        });
    }

    public void getStudent(final User user, @NonNull final SimpleCallBack<Student> simpleCallBack) {
        repository.getStudent(user, new SimpleCallBack<Student>() {
            @Override
            public void callback(Student data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
