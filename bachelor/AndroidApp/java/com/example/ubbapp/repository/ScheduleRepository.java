package com.example.ubbapp.repository;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Address;
import com.example.ubbapp.model.Discipline;
import com.example.ubbapp.model.Group;
import com.example.ubbapp.model.HourInterval;
import com.example.ubbapp.model.Professor;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.repository.database.Database;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SimpleCallBack;
import com.example.ubbapp.structures.WeekDays;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScheduleRepository {

    private Database disciplineDatabase;

    public ScheduleRepository() {
        this.disciplineDatabase = new Database(FirebaseDatabase.getInstance().
                getReference().child(FirebaseConstants.DISCIPLINE));
    }

    public void getDisciplinesWeeklySchedule(final Group group, final String currentDay, final int currentWeek, @NonNull final SimpleCallBack<int[]> simpleCallBack) {
        disciplineDatabase.getDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int[] disciplines = {0, 0, 0};
                for (DataSnapshot data : dataSnapshot.getChildren()) {


                    if (data.child(FirebaseConstants.DISCIPLINE_DAY).getValue().toString().equalsIgnoreCase(currentDay) &&
                            (Integer.parseInt(data.child(FirebaseConstants.DISCIPLINE_WEEK).getValue().toString()) == currentWeek ||
                                    Integer.parseInt(data.child(FirebaseConstants.DISCIPLINE_WEEK).getValue().toString()) == 0)) {


                        if (data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup()) &&
                                data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.SEMINARY)) {
                            disciplines[1]++;
                        }

                        if (data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.COURSE) &&
                                data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup().substring(0, 2))) {
                            disciplines[0]++;
                        }

                        if (data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup() + "/" + group.getSemigroup())
                                && data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.LABORATORY)) {
                            disciplines[2]++;
                        }
                    }
                }
                simpleCallBack.callback(disciplines);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDisciplinesDailySchedule(final Group group, final String currentDay, final int currentWeek, @NonNull final SimpleCallBack<Discipline> simpleCallBack) {
        disciplineDatabase.getDatabase().orderByChild(FirebaseConstants.DISCIPLINE_START_HOUR).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (Integer.parseInt(data.child(FirebaseConstants.DISCIPLINE_WEEK).getValue().toString()) == currentWeek
                            ||
                            Integer.parseInt(data.child(FirebaseConstants.DISCIPLINE_WEEK).getValue().toString()) == 0) {

                        if (data.child(FirebaseConstants.DISCIPLINE_DAY).getValue().toString().equals(currentDay)) {

                            if ((data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.COURSE) &&
                                    data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup().substring(0, 2))
                                    || (data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup()) &&
                                    data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.SEMINARY)) ||
                                    (data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup() + "/" + group.getSemigroup())
                                            && data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.LABORATORY)))) {

                                Address address = new Address(
                                        Float.parseFloat(data.child(FirebaseConstants.DISCIPLINE_ADDRESS)
                                                .child(FirebaseConstants.ADDRESS_LATITUDE).getValue().toString()),
                                        Float.parseFloat(data.child(FirebaseConstants.DISCIPLINE_ADDRESS)
                                                .child(FirebaseConstants.ADDRESS_LONGITUDE).getValue().toString()),
                                        data.child(FirebaseConstants.DISCIPLINE_ADDRESS)
                                                .child(FirebaseConstants.ADDRESS_NAME).getValue().toString(),
                                        data.child(FirebaseConstants.DISCIPLINE_ADDRESS)
                                                .child(FirebaseConstants.ADDRESS_ROOM).getValue().toString()
                                );
                                Professor professor = new Professor(
                                        data.child(FirebaseConstants.PROFESSOR).
                                                child(FirebaseConstants.EMAIL).getValue().toString(),
                                        data.child(FirebaseConstants.PROFESSOR).
                                                child(FirebaseConstants.FIRST_NAME).getValue().toString(),
                                        data.child(FirebaseConstants.PROFESSOR).
                                                child(FirebaseConstants.LAST_NAME).getValue().toString(),
                                        data.child(FirebaseConstants.PROFESSOR).
                                                child(FirebaseConstants.PROFESSOR_PHONE_NUMBER).getValue().toString(),
                                        data.child(FirebaseConstants.PROFESSOR).
                                                child(FirebaseConstants.PROFESSOR_WEB_SITE).getValue().toString(),
                                        ""
                                );
                                Discipline discipline = new Discipline(
                                        data.child(FirebaseConstants.NAME).getValue().toString(),
                                        new HourInterval(data.child(FirebaseConstants.DISCIPLINE_START_HOUR).getValue().toString(),
                                                data.child(FirebaseConstants.DISCIPLINE_END_HOUR).getValue().toString()),
                                        professor,
                                        getWeekDay(data.child(FirebaseConstants.DISCIPLINE_DAY).getValue().toString()),
                                        Integer.parseInt(data.child(FirebaseConstants.DISCIPLINE_WEEK).getValue().toString()),
                                        address,
                                        data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString(),
                                        ""
                                );
                                simpleCallBack.callback(discipline);
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDisciplinesNames(final Group group, @NonNull final SimpleCallBack<Discipline> simpleCallBack) {
        disciplineDatabase.getDatabase().orderByChild(FirebaseConstants.NAME).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString().equals(CommonVariables.COURSE) &&
                            data.child(FirebaseConstants.GROUP).getValue().toString().equals(group.getGroup().substring(0, 2))) {
                        Address address = new Address(
                                Float.parseFloat(data.child(FirebaseConstants.DISCIPLINE_ADDRESS)
                                        .child(FirebaseConstants.ADDRESS_LATITUDE).getValue().toString()),
                                Float.parseFloat(data.child(FirebaseConstants.DISCIPLINE_ADDRESS)
                                        .child(FirebaseConstants.ADDRESS_LONGITUDE).getValue().toString()),
                                data.child(FirebaseConstants.DISCIPLINE_ADDRESS)
                                        .child(FirebaseConstants.ADDRESS_NAME).getValue().toString(),
                                data.child(FirebaseConstants.DISCIPLINE_ADDRESS)
                                        .child(FirebaseConstants.ADDRESS_ROOM).getValue().toString()
                        );
                        Professor professor = new Professor(
                                data.child(FirebaseConstants.PROFESSOR).
                                        child(FirebaseConstants.EMAIL).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).
                                        child(FirebaseConstants.FIRST_NAME).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).
                                        child(FirebaseConstants.LAST_NAME).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).
                                        child(FirebaseConstants.PROFESSOR_PHONE_NUMBER).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).
                                        child(FirebaseConstants.PROFESSOR_WEB_SITE).getValue().toString(),
                                data.child(FirebaseConstants.PROFESSOR).
                                        child(FirebaseConstants.PROFESSOR_RANK).getValue().toString()
                        );
                        Discipline discipline = new Discipline(
                                data.child(FirebaseConstants.NAME).getValue().toString(),
                                new HourInterval(data.child(FirebaseConstants.DISCIPLINE_START_HOUR).getValue().toString(),
                                        data.child(FirebaseConstants.DISCIPLINE_END_HOUR).getValue().toString()),
                                professor,
                                getWeekDay(data.child(FirebaseConstants.DISCIPLINE_DAY).getValue().toString()),
                                Integer.parseInt(data.child(FirebaseConstants.DISCIPLINE_WEEK).getValue().toString()),
                                address,
                                data.child(FirebaseConstants.DISCIPLINE_TYPE).getValue().toString(),
                                data.child(FirebaseConstants.DISCIPLINE_FULL_NAME).getValue().toString()
                        );
                        simpleCallBack.callback(discipline);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private WeekDays getWeekDay(String weekDay) {
        if (WeekDays.MONDAY.name().equalsIgnoreCase(weekDay)) {
            return WeekDays.MONDAY;
        }
        if (WeekDays.TUESDAY.name().equalsIgnoreCase(weekDay)) {
            return WeekDays.TUESDAY;
        }
        if (WeekDays.WEDNESDAY.name().equalsIgnoreCase(weekDay)) {
            return WeekDays.WEDNESDAY;
        }
        if (WeekDays.THURSDAY.name().equalsIgnoreCase(weekDay)) {
            return WeekDays.THURSDAY;
        }
        if (WeekDays.FRIDAY.name().equalsIgnoreCase(weekDay)) {
            return WeekDays.FRIDAY;
        }
        return null;
    }
}
