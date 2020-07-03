package com.example.ubbapp.repository;

import androidx.annotation.NonNull;

import com.example.ubbapp.repository.database.Database;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttendanceRepository {

    private Database database;

    public AttendanceRepository() {
        this.database = new Database(FirebaseDatabase.getInstance().
                getReference().child(FirebaseConstants.ATTENDANCE_TABLE));
    }

    public void getAttendance(final String studentID, final String disciplineName, final String type, final SimpleCallBack<String> simpleCallBack) {
        database.getDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(FirebaseConstants.USER).getValue().toString().equals(studentID)) {
                        for (DataSnapshot attendance : data.child(disciplineName).child(type).getChildren()) {
                            simpleCallBack.callback(attendance.getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
