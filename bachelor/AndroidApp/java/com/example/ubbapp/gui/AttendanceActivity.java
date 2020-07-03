package com.example.ubbapp.gui;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ubbapp.R;
import com.example.ubbapp.controller.AttendanceController;
import com.example.ubbapp.model.Discipline;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.structures.SimpleCallBack;

public class AttendanceActivity extends AppCompatActivity {

    ImageButton button_back;

    Button buttonS;
    Button buttonL;

    TextView attendanceName;
    private LinearLayout attendanceView;

    private AttendanceController controller;

    private Student student;
    private Discipline discipline;
    private int disciplineNumber;

    Animation fromright;
    Animation fromleft;

    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        getDataFromBundle();
        initializeComponents();

        startGenerateAttendance(CommonVariables.LABORATORY);
    }

    private void initializeComponents() {
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);

        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        controller = new AttendanceController();

        attendanceName = findViewById(R.id.textView_disciplinename);
        attendanceName.setText(discipline.getFullName());
        attendanceName.setAnimation(fromleft);

        attendanceView = findViewById(R.id.attendanceView);
        attendanceView.setAnimation(fromright);

        buttonL = findViewById(R.id.button_L);
        buttonS = findViewById(R.id.button_S);
        clickS();
        clickL();
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
            student = getIntent().getParcelableExtra(CommonVariables.STUDENT);
            discipline = getIntent().getParcelableExtra(CommonVariables.DISCIPLINE);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void createCardViewAttendance(String type, String data) {
        disciplineNumber++;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View child = inflater.inflate(R.layout.attendance_card, null, false);

        TextView text = child.findViewById(R.id.textView_laborsem);
        ImageView imageView = child.findViewById(R.id.imageView_checkedorunchecked);

        if (type.equals(CommonVariables.SEMINARY)) {
            String name = "Seminary " + disciplineNumber;
            text.setText(name);
        } else if (type.equals(CommonVariables.LABORATORY)) {
            String name = "Laboratory " + disciplineNumber;
            text.setText(name);
        }

        if (data.equals("0")) {
            imageView.setImageResource(R.drawable.ic_unchecked_icon);
        } else if (data.equals("1")) {
            imageView.setImageResource(R.drawable.ic_checked_icon);
        } else {
            imageView.setImageResource(0);
        }

        attendanceView.addView(child);
    }

    private void startGenerateAttendance(final String type) {
        controller.getAttendance(student.getEmail(), discipline.getName(),
                type, new SimpleCallBack<String>() {
                    @Override
                    public void callback(String data) {
                        createCardViewAttendance(type, data);
                    }
                });
    }

    private void clickS() {
        buttonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                disciplineNumber = 0;
                buttonS.setVisibility(View.GONE);
                buttonL.setVisibility(View.VISIBLE);
                attendanceView.removeAllViews();
                startGenerateAttendance(CommonVariables.SEMINARY);
            }
        });
    }

    private void clickL() {
        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                disciplineNumber = 0;
                buttonL.setVisibility(View.GONE);
                buttonS.setVisibility(View.VISIBLE);
                attendanceView.removeAllViews();
                startGenerateAttendance(CommonVariables.LABORATORY);
            }
        });
    }
}
