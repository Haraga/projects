package com.example.ubbapp.controller;

import com.example.ubbapp.model.Group;
import com.example.ubbapp.model.Professor;
import com.example.ubbapp.repository.ProfessorRepository;
import com.example.ubbapp.structures.SimpleCallBack;

public class ProfessorsController {

    private ProfessorRepository repository;

    public ProfessorsController() {
        repository = new ProfessorRepository();
    }

    public void getProfessors(Group group, final SimpleCallBack<Professor> simpleCallBack) {
        repository.getProfessors(group, new SimpleCallBack<Professor>() {
            @Override
            public void callback(Professor data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
