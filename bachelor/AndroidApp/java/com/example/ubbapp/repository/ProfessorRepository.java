package com.example.ubbapp.repository;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Group;
import com.example.ubbapp.model.Professor;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.repository.database.Database;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfessorRepository {

    private Database database;

    public ProfessorRepository() {
        this.database = new Database(FirebaseDatabase.getInstance().
                getReference().child(FirebaseConstants.DISCIPLINE));
    }

    public void getProfessors(final Group group, final SimpleCallBack<Professor> simpleCallBack) {
        database.getDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.COURSE) &&
                            data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup().substring(0, 2))
                            || (data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup()) &&
                            data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.SEMINARY)) ||
                            (data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup() + "/" + group.getSemigroup())
                                    && data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.LABORATORY))) {
                        Professor professor = new Professor(
                                data.child(FirebaseConstants.PROFESSOR).child(FirebaseConstants.EMAIL).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).child(FirebaseConstants.FIRST_NAME).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).child(FirebaseConstants.LAST_NAME).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).child(FirebaseConstants.PROFESSOR_PHONE_NUMBER).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).child(FirebaseConstants.PROFESSOR_WEB_SITE).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).child(FirebaseConstants.PROFESSOR_RANK).getValue().toString()
                        );
                        simpleCallBack.callback(professor);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
