package com.example.ubbapp.repository;

import androidx.annotation.NonNull;

import com.example.ubbapp.repository.database.Database;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CurrentWeekRepository {

    private Database database;

    public CurrentWeekRepository() {
        this.database = new Database(FirebaseDatabase.getInstance().
                getReference().child(FirebaseConstants.CURRENT_WEEK_TABLE));
    }

    public void getCurrentWeek(final SimpleCallBack<String> simpleCallBack) {
        this.database.getDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                simpleCallBack.callback(dataSnapshot.child(FirebaseConstants.CURRENT_WEEK_VALUE).getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
