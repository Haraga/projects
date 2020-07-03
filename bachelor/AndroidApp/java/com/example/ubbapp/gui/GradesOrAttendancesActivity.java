package com.example.ubbapp.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ubbapp.R;
import com.example.ubbapp.model.Discipline;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.variables.CommonVariables;

public class GradesOrAttendancesActivity extends AppCompatActivity {

    ImageButton button_back;
    TextView textView_viewtoday;

    CardView attendancesCardView;
    CardView gradesCardView;

    ImageView imageView_choose;
    Animation fromtop;
    Animation frombottom;
    Animation fromleft;

    private long lastClickTime = 0;
    private Discipline discipline;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradesorattendances);

        getDataFromBundle();
        initializeComponents();
    }

    private void initializeComponents() {
        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);

        textView_viewtoday = findViewById(R.id.textView_viewtoday);
        textView_viewtoday.setAnimation(fromleft);

        attendancesCardView = findViewById(R.id.cardView_attendances);
        attendancesCardView.setAnimation(frombottom);

        gradesCardView = findViewById(R.id.cardView_grades);
        gradesCardView.setAnimation(frombottom);

        imageView_choose = findViewById(R.id.imageView_choose);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        imageView_choose.setAnimation(fromtop);

        attendancesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(GradesOrAttendancesActivity.this, AttendanceActivity.class);
                intent.putExtra(CommonVariables.STUDENT, student);
                intent.putExtra(CommonVariables.DISCIPLINE, discipline);
                startActivity(intent);
            }
        });

        gradesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(GradesOrAttendancesActivity.this, GradesActivity.class);
                intent.putExtra(CommonVariables.STUDENT, student);
                intent.putExtra(CommonVariables.DISCIPLINE, discipline);
                startActivity(intent);
            }
        });
    }

    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            student = getIntent().getParcelableExtra(CommonVariables.STUDENT);
            discipline = getIntent().getParcelableExtra(CommonVariables.DISCIPLINE);
        }
    }

    private void backButtonEvent() {
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
