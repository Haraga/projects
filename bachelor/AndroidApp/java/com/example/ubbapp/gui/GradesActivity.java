package com.example.ubbapp.gui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ubbapp.R;
import com.example.ubbapp.controller.MarkController;
import com.example.ubbapp.model.Discipline;
import com.example.ubbapp.model.Mark;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.structures.SimpleCallBack;

public class GradesActivity extends AppCompatActivity {

    ImageButton button_back;

    Animation fromleft;
    Animation fromright;

    private LinearLayout gradesLayout;
    private TextView disciplineName;

    private MarkController controller;

    private Student student;
    private Discipline discipline;

    private int gradeNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        getDataFromBundle();
        initializeComponents();
        getGrades();
    }


    private void initializeComponents() {
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);

        controller = new MarkController();

        disciplineName = findViewById(R.id.textView_disciplinename);
        disciplineName.setText(discipline.getFullName());
        disciplineName.setAnimation(fromleft);

        gradesLayout = findViewById(R.id.gradesView);
        gradesLayout.setAnimation(fromright);

        button_back = findViewById(R.id.button_back);
        backButtonEvent();
    }

    private void getGrades() {
        controller.getMarks(student.getEmail(), discipline.getName(), new SimpleCallBack<Mark>() {
            @Override
            public void callback(Mark data) {
                createGradeCard(data);
            }
        });
    }

    private void createGradeCard(final Mark mark) {
        gradeNumber++;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View child = inflater.inflate(R.layout.grades_card, null, false);
        CardView view = child.findViewById(R.id.cardView_grades);

        TextView gradeLaboratoryNumber = child.findViewById(R.id.textView_lab);
        TextView gradeNumberText = child.findViewById(R.id.textView_grade);

        gradeLaboratoryNumber.setText("Laboratory " + gradeNumber);
        gradeNumberText.setText(String.valueOf(mark.getMark()));
        if (mark.getMark() < 5.0) {
            gradeNumberText.setTextColor(Color.RED);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GradesActivity.this);
                builder.setMessage(mark.getRemark());
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        gradesLayout.addView(child);
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
}
