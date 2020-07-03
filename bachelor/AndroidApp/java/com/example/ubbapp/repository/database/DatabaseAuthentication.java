package com.example.ubbapp.repository.database;

import com.google.firebase.auth.FirebaseAuth;

/**
 * The DatabaseAuthentication class describes the reference to the database
 * authentication part
 */
public class DatabaseAuthentication {

    /**
     * The DatabaseAuthentication attributes
     */
    private FirebaseAuth auth;

    /**
     * The DatabaseAuthentication constructor
     *
     * @param auth FirebaseAuth, reference to DatabaseAuthentication authentication
     */
    public DatabaseAuthentication(FirebaseAuth auth) {
        this.auth = auth;
    }

    /**
     * The getAuth method returns the DatabaseAuthentication reference
     *
     * @return FirebaseAuth, the reference to DatabaseAuthentication authentication
     */
    public FirebaseAuth getAuth() {
        return auth;
    }

    /**
     * The setAuth method sets a new DatabaseAuthentication reference
     *
     * @param auth FirebaseAuth, which will be the new DatabaseAuthentication reference
     */
    public void setAuth(FirebaseAuth auth) {
        this.auth = auth;
    }
}
