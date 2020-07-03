package com.example.ubbapp.gui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.ubbapp.R;
import com.example.ubbapp.controller.NewsController;
import com.example.ubbapp.model.News;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SaveSharedPreference;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Collections;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private long lastClickTime = 0;
    private Student student;
    private StorageReference storage;

    ImageButton button_back;

    ImageButton button_menu;
    ImageButton button_settings;
    ImageButton button_logout;
    boolean isOpen = false;

    private NewsController newsController;
    private LinearLayout newsView;

    TextView textView_news;

    Animation fromleft;
    Animation fromright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getDataFromBundle();
        initializeComponents();
        generateNewsfeed();
    }

    private void initializeComponents() {
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);

        newsController = new NewsController();

        newsView = findViewById(R.id.newsView);
        newsView.setAnimation(fromright);

        textView_news = findViewById(R.id.textView_news);
        textView_news.setAnimation(fromleft);

        button_settings = findViewById(R.id.imageButton_settings);

        button_menu = findViewById(R.id.imageButton_menu);
        menuButtonEvent();

        button_logout = findViewById(R.id.imageButton_logout);
        logOutButtonEvent();

        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        storage = FirebaseStorage.getInstance().getReference().child(FirebaseConstants.PROFESSOR);
    }

    private void backButtonEvent() {
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void menuButtonEvent() {
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    openMenu();
                } else {
                    closeMenu();
                }
            }
        });
    }

    private void logOutButtonEvent() {
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                SaveSharedPreference.clearSharedPreferences(NewsActivity.this);
                                Intent intent = new Intent(NewsActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(NewsActivity.this);
                builder.setMessage("Are you sure you want to log out?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    private void openMenu() {
        isOpen = true;
        button_menu.animate().translationY(0);
        button_menu.animate().rotation(90);
        button_settings.setVisibility(View.VISIBLE);
        button_logout.setVisibility(View.VISIBLE);
    }

    private void closeMenu() {
        isOpen = false;
        button_menu.animate().translationX(0);
        button_menu.animate().rotation(0);
        button_settings.setVisibility(View.GONE);
        button_logout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void generateNewsfeed() {
        newsController.getNews(student.getGroup(), new SimpleCallBack<List<News>>() {
            @Override
            public void callback(List<News> data) {
                Collections.reverse(data);
                for (News news : data) {
                    createNewsCardView(news);
                }
            }
        });
    }

    private void createNewsCardView(final News news) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View child = inflater.inflate(R.layout.news_card, null, false);
        CardView newsCardView = child.findViewById(R.id.cardView_news);

        TextView newsTitleView = child.findViewById(R.id.textView_title);
        TextView newsDateView = child.findViewById(R.id.textView_date);
        ImageView imageView = child.findViewById(R.id.circleImageView);

        newsTitleView.setText(news.getTitle());
        newsDateView.setText(news.getDate());

        String path = news.getAuthor().replace(" ", "-");
        Glide.with(this).load(storage.child(path + ".jpg")).into(imageView);

        newsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(NewsActivity.this, ArticleActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
            }
        });
        newsView.addView(child);
    }

    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            student = getIntent().getParcelableExtra("student");
        }
    }
}
