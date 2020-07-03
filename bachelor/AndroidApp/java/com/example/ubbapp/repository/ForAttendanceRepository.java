package com.example.ubbapp.repository;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.ForAttendance;
import com.example.ubbapp.repository.database.Database;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForAttendanceRepository {
    private Database database;

    public ForAttendanceRepository() {
        this.database = new Database(FirebaseDatabase.getInstance().getReference().child(FirebaseConstants.FORATTENDANCE_TABLE));
    }

    public void getForAttendance(final String disciplineName, final String code, @NonNull final SimpleCallBack<ForAttendance> simpleCallBack) {
        this.database.getDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("code").getValue().toString().equals("12345")
                            && data.child("discipline").getValue().toString().equals("SO")) {
                        ForAttendance forAttendance = new ForAttendance(
                                data.child("code").getValue().toString(),
                                data.child("discipline").getValue().toString(),
                                (double) data.child("latitude").getValue(),
                                (double) data.child("longitude").getValue(),
                                data.child("type").getValue().toString(),
                                data.child("typeNumber").getValue().toString());
                        simpleCallBack.callback(forAttendance);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
