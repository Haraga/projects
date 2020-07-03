package com.example.ubbapp.structures;

import java.io.Serializable;

public class Validator implements Serializable {

    public Validator() {

    }

    public boolean validateEmail(final String email) {
        return true;
    }

    public boolean validatePassword(final String password) {
        return true;
    }
}
