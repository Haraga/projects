package com.example.ubbapp.gui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ubbapp.R;
import com.example.ubbapp.model.News;

public class ArticleActivity extends AppCompatActivity {

    private ImageButton button_back;
    private News news;

    Animation fromleft;
    Animation fromright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        getDataFromBundle();
        initializeComponents();
    }

    private void initializeComponents() {
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);

        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        TextView title, author, date, content;
        title = findViewById(R.id.textView_articletitle);
        author = findViewById(R.id.textView_articleauthor);
        date = findViewById(R.id.textView_articledate);
        content = findViewById(R.id.textView_articlecontent);

        title.setText(news.getTitle());
        author.setText(news.getAuthor());
        date.setText(news.getDate());
        content.setText(news.getContent());

        title.setAnimation(fromleft);
        author.setAnimation(fromleft);
        date.setAnimation(fromleft);
        content.setAnimation(fromright);
    }

    private void backButtonEvent() {
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            news = getIntent().getParcelableExtra("news");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
