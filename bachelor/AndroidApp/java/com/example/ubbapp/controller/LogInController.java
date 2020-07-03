package com.example.ubbapp.controller;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.User;
import com.example.ubbapp.repository.LogInRepository;
import com.example.ubbapp.structures.SimpleCallBack;
import com.example.ubbapp.structures.Validator;

public class LogInController {

    private LogInRepository logInRepository;
    private Validator validator;

    public LogInController(Validator validator) {
        this.logInRepository = new LogInRepository();
        this.validator = validator;
    }

    public void checkCredentials(final String email, final String password, @NonNull final SimpleCallBack<User> simpleCallBack) {
        if (validator.validateEmail(email) && validator.validatePassword(password)) {
            logInRepository.checkCredentials(email, password, new SimpleCallBack<User>() {
                @Override
                public void callback(User data) {
                    simpleCallBack.callback(data);
                }
            });
        }
    }

    public void resetPassword(final String email) {
        logInRepository.resetPassword(email);
    }
}
