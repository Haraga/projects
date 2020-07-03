package com.example.ubbapp.repository;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Group;
import com.example.ubbapp.model.Homework;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.repository.database.Database;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeworkRepository {

    private Database homeworkDatabase;

    public HomeworkRepository() {
        this.homeworkDatabase = new Database(FirebaseDatabase.getInstance().
                getReference().child(FirebaseConstants.HOMEWORK_TABLE));
    }

    public void getHomework(final Group group, final int currentWeek, @NonNull final SimpleCallBack<Homework> simpleCallBack) {
        this.homeworkDatabase.getDatabase().orderByChild(FirebaseConstants.HOMEWORK_DEADLINE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (group.getGroup().substring(0, 2).equals(data.child(FirebaseConstants.YEAR_OF_STUDY).getValue().toString()) &&
                            Integer.parseInt(data.child(FirebaseConstants.HOMEWORK_DEADLINE).getValue().toString()) >= currentWeek) {
                        Homework homework = new Homework(
                                data.child(FirebaseConstants.DISCIPLINE).getValue().toString(),
                                data.child(FirebaseConstants.HOMEWORK_TEXT).getValue().toString(),
                                data.child(FirebaseConstants.HOMEWORK_TITLE).getValue().toString(),
                                data.child(FirebaseConstants.HOMEWORK_DOWNLOAD_LINK).getValue().toString(),
                                Integer.parseInt(data.child(FirebaseConstants.HOMEWORK_START).getValue().
                                        toString()),
                                Integer.parseInt(data.child(FirebaseConstants.HOMEWORK_DEADLINE).getValue().
                                        toString()),
                                (Boolean) data.child(CommonVariables.LABORATORY).getValue(),
                                (Boolean) data.child(CommonVariables.SEMINARY).getValue());
                        simpleCallBack.callback(homework);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
