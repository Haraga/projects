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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ubbapp.R;
import com.example.ubbapp.controller.CurrentWeekController;
import com.example.ubbapp.controller.HomeworkController;
import com.example.ubbapp.model.Homework;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.structures.SaveSharedPreference;
import com.example.ubbapp.structures.SimpleCallBack;

public class HomeworkActivity extends AppCompatActivity {

    ImageButton button_back;

    TextView textView_homework;
    TextView textView_currentweektext;
    TextView textView_currentweeknumber;

    ImageButton button_menu;
    ImageButton button_settings;
    ImageButton button_logout;
    boolean isOpen = false;

    private HomeworkController homeworkController;
    private CurrentWeekController currentWeekController;
    private LinearLayout homeworkView;
    private Student student;

    Animation fromtop;
    Animation fromleft;
    Animation fromright;

    private long lastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        getDataFromBundle();
        initializeComponents();
        generateHomework();
    }

    private void initializeComponents() {
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);

        homeworkController = new HomeworkController();
        currentWeekController = new CurrentWeekController();

        homeworkView = findViewById(R.id.homeworkView);
        homeworkView.setAnimation(fromright);

        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        button_menu = findViewById(R.id.imageButton_menu);
        menuButtonEvent();

        button_settings = findViewById(R.id.imageButton_settings);

        button_logout = findViewById(R.id.imageButton_logout);
        logoutButtonEvent();

        textView_homework = findViewById(R.id.textView_homework);
        textView_homework.setAnimation(fromleft);

        textView_currentweektext = findViewById(R.id.textView_currentweektext);
        textView_currentweektext.setAnimation(fromleft);

        textView_currentweeknumber = findViewById(R.id.textView_currentweeknumber);
        textView_currentweeknumber.setAnimation(fromleft);
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

    private void logoutButtonEvent() {
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                SaveSharedPreference.clearSharedPreferences(HomeworkActivity.this);
                                Intent intent = new Intent(HomeworkActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeworkActivity.this);
                builder.setMessage("Are you sure you want to log out?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    private void generateHomework() {
        currentWeekController.getCurrentWeek(new SimpleCallBack<String>() {
            @Override
            public void callback(String currentWeek) {
                textView_currentweeknumber.setText(currentWeek);
                homeworkController.getHomework(student.getGroup(), Integer.parseInt(currentWeek),
                        new SimpleCallBack<Homework>() {
                            @Override
                            public void callback(Homework data) {
                                createHomeworkCardView(data);
                            }
                        });
            }
        });
    }

    public void createHomeworkCardView(final Homework homework) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View child = inflater.inflate(R.layout.homework_card, null, false);
        CardView homeworkCardView = child.findViewById(R.id.cardView_homework);

        TextView homeworkDeadlineView = child.findViewById(R.id.textView_deadline);
        TextView disciplineView = child.findViewById(R.id.textView_discipline);
        TextView disciplineTypeView = child.findViewById(R.id.textView_disciplinetype);

        String deadlineWeek = String.valueOf(homework.getToCompleteWeek());
        deadlineWeek = "WEEK " + deadlineWeek;

        homeworkDeadlineView.setText(deadlineWeek);
        disciplineView.setText(homework.getDiscipline());

        if (homework.isLaboratoryHomework()) {
            disciplineTypeView.setText(CommonVariables.LABORATORY);
        } else if (homework.isSeminaryHomework()) {
            disciplineTypeView.setText(CommonVariables.SEMINARY);
        }

        homeworkCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(HomeworkActivity.this, HomeworkDetailsActivity.class);
                intent.putExtra(CommonVariables.HOMEWORK, homework);
                startActivity(intent);
            }
        });

        homeworkView.addView(child);
    }

    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            student = getIntent().getParcelableExtra(CommonVariables.STUDENT);
        }
    }
}
