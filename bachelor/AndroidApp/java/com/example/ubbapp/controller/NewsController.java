package com.example.ubbapp.controller;

import androidx.annotation.NonNull;

import com.example.ubbapp.model.Group;
import com.example.ubbapp.model.News;
import com.example.ubbapp.repository.NewsRepository;
import com.example.ubbapp.structures.SimpleCallBack;

import java.util.List;

public class NewsController {

    private NewsRepository newsRepository;

    public NewsController() {
        this.newsRepository = new NewsRepository();
    }

    public void getNews(final Group group, @NonNull final SimpleCallBack<List<News>> simpleCallBack) {
        this.newsRepository.getNews(group, new SimpleCallBack<List<News>>() {
            @Override
            public void callback(List<News> data) {
                simpleCallBack.callback(data);
            }
        });
    }
}
