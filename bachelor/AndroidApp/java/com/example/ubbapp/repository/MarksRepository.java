package com.example.ubbapp.repository;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Mark;
import com.example.ubbapp.repository.database.Database;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MarksRepository {

    private Database database;

    public MarksRepository() {
        this.database = new Database(FirebaseDatabase.getInstance().
                getReference().child(FirebaseConstants.MARK_TABLE));
    }

    public void getMarks(final String studentID, final String disciplineName, final SimpleCallBack<Mark> simpleCallBack) {
        database.getDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(FirebaseConstants.USER).getValue().toString().equals(studentID)) {
                        for (DataSnapshot mark : data.child(disciplineName).getChildren()) {
                            simpleCallBack.callback(new Mark(mark.child(FirebaseConstants.MARK_REMARK).getValue().toString(),
                                    Float.parseFloat(mark.child(FirebaseConstants.MARK_MARK).getValue().toString())));
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
