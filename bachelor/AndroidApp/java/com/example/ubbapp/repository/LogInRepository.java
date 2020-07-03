package com.example.ubbapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.User;
import com.example.ubbapp.repository.database.DatabaseAuthentication;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LogInRepository {

    private DatabaseAuthentication databaseAuthentication;

    public LogInRepository() {
        this.databaseAuthentication = new DatabaseAuthentication(FirebaseAuth.getInstance());
    }

    public void checkCredentials(final String email, final String password, @NonNull final SimpleCallBack<User> simpleCallBack) {

        this.databaseAuthentication.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        User user = null;
                        if (task.isSuccessful()) {
                            user = new User(email);
                        }
                        simpleCallBack.callback(user);
                    }
                }
        );
    }

    public void resetPassword(final String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }
}
