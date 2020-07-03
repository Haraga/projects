package com.example.ubbapp.repository;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Group;
import com.example.ubbapp.model.News;
import com.example.ubbapp.repository.database.Database;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsRepository {

    private Database database;

    public NewsRepository() {
        this.database = new Database(FirebaseDatabase.getInstance()
                .getReference().child(FirebaseConstants.NEWS_TABLE));
    }

    public void getNews(final Group group, @NonNull final SimpleCallBack<List<News>> simpleCallBack) {
        this.database.getDatabase().orderByChild(FirebaseConstants.NEWS_DATE).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<News> newsList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child(FirebaseConstants.NEWS_ALL).getValue().equals(true)
                            || data.child(FirebaseConstants.YEAR_OF_STUDY).getValue().toString().equals((group.getGroup().substring(0, 2)))) {
                        News news = new News(
                                data.child(FirebaseConstants.NEWS_TITLE).getValue().toString(),
                                data.child(FirebaseConstants.NEWS_CONTENT).getValue().toString(),
                                data.child(FirebaseConstants.NEWS_DATE).getValue().toString(),
                                data.child(FirebaseConstants.NEWS_AUTHOR).getValue().toString());
                        newsList.add(news);
                    }
                }
                simpleCallBack.callback(newsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
