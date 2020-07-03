package com.example.ubbapp.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ubbapp.R;
import com.example.ubbapp.controller.DisciplinesController;
import com.example.ubbapp.model.Discipline;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.structures.SimpleCallBack;

public class DisciplinesActivity extends AppCompatActivity {

    ImageButton button_back;
    TextView textView_disciplines;

    private Student student;
    private long lastClickTime = 0;
    private DisciplinesController controller;
    private LinearLayout disciplinesView;

    Animation fromright;
    Animation fromleft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplines);

        getDataFromBundle();
        initializeComponents();
        generateDisciplineText();
    }

    private void initializeComponents() {
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);

        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        textView_disciplines = findViewById(R.id.textView_disciplines);
        textView_disciplines.setAnimation(fromleft);

        controller = new DisciplinesController();

        disciplinesView = findViewById(R.id.disciplinesView);
        disciplinesView.setAnimation(fromright);
    }

    private void generateDisciplineText() {
        controller.getDisciplinesNames(student.getGroup(), new SimpleCallBack<Discipline>() {
            @Override
            public void callback(Discipline data) {
                createDisciplineCardView(data);
            }
        });
    }

    private void createDisciplineCardView(final Discipline discipline) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View child = inflater.inflate(R.layout.discipline_card, null, false);
        CardView disciplineCardView = child.findViewById(R.id.cardView_discipline);

        final TextView disciplineFullName = child.findViewById(R.id.textView_disciplinename);
        TextView professorName = child.findViewById(R.id.textView_teachername);
        TextView professorTitle = child.findViewById(R.id.textView_teachertitle);
        TextView disciplineName = child.findViewById(R.id.textView_disciplinenameshortcut);

        professorName.setText(discipline.getProfessor().getFirstName() + " " + discipline.getProfessor().getLastName());
        professorTitle.setText(discipline.getProfessor().getTitle());
        disciplineFullName.setText(discipline.getFullName());
        disciplineName.setText(discipline.getName());

        disciplineCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(DisciplinesActivity.this, GradesOrAttendancesActivity.class);
                intent.putExtra(CommonVariables.DISCIPLINE, discipline);
                intent.putExtra(CommonVariables.STUDENT, student);
                startActivity(intent);
            }
        });

        disciplinesView.addView(child);
    }

    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            student = getIntent().getParcelableExtra(CommonVariables.STUDENT);
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
