package com.example.ubbapp.repository;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Group;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.User;
import com.example.ubbapp.repository.database.Database;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentRepository {

    private Database database;

    public StudentRepository() {
        this.database = new Database(FirebaseDatabase.getInstance().getReference().child(FirebaseConstants.STUDENT_TABLE));
    }

    public void getStudentKey(final User user, @NonNull final SimpleCallBack<String> simpleCallBack) {
        database.getDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(FirebaseConstants.EMAIL).getValue().toString().equals(user.getEmail())) {
                        simpleCallBack.callback(data.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getStudent(final User user, @NonNull final SimpleCallBack<Student> simpleCallBack) {
        database.getDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(FirebaseConstants.EMAIL).getValue().toString().equals(user.getEmail())) {
                        Student student = new Student(data.child(FirebaseConstants.EMAIL).getValue().toString(),
                                data.child(FirebaseConstants.FIRST_NAME).getValue().toString(),
                                data.child(FirebaseConstants.LAST_NAME).getValue().toString(),
                                new Group(data.child(FirebaseConstants.GROUP).getValue().toString().substring(0, 3),
                                        data.child(FirebaseConstants.GROUP).getValue().toString().substring(4, 5)));
                        simpleCallBack.callback(student);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
