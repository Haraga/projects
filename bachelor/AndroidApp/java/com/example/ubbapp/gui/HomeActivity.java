package com.example.ubbapp.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ubbapp.R;
import com.example.ubbapp.controller.StudentController;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.User;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.structures.SaveSharedPreference;
import com.example.ubbapp.structures.SimpleCallBack;

public class HomeActivity extends AppCompatActivity {

    ImageButton button_menu;
    ImageButton button_settings;
    ImageButton button_logout;
    boolean isOpen = false;
    private StudentController controller;

    private User user;
    private long lastClickTime = 0;

    CardView cardView_schedule;
    CardView cardView_news;
    CardView cardView_homework;
    CardView cardView_activity;
    CardView cardView_academicinfo;
    CardView cardView_sendemail;

    Button button_participating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        controller = new StudentController();

        getDataFromBundle();
        checkIfUserLogged();
        initializeComponents();
    }

    private void initializeComponents() {
        button_settings = findViewById(R.id.imageButton_settings);

        button_menu = findViewById(R.id.imageButton_menu);
        menuButtonEvent();

        button_logout = findViewById(R.id.imageButton_logout);
        logOutButtonEvent();

        cardView_schedule = findViewById(R.id.cardView_schedule);
        scheduleButtonEvent();

        cardView_news = findViewById(R.id.cardView_news);
        newsButtonEvent();

        cardView_homework = findViewById(R.id.cardView_homework);
        homeworkButtonEvent();

        cardView_activity = findViewById(R.id.cardView_activity);
        activityButtonEvent();

        cardView_academicinfo = findViewById(R.id.cardView_locations);
        academicinfoButtonEvent();

        cardView_sendemail = findViewById(R.id.cardView_sendemail);
        sendEmailButtonEvent();

        button_participating = findViewById(R.id.button_participating);
        participatingButtonEvent();

    }

    private void participatingButtonEvent() {
        button_participating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(HomeActivity.this, CheckLocationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendEmailButtonEvent() {
        cardView_sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.getStudent(user, new SimpleCallBack<Student>() {
                    @Override
                    public void callback(Student student) {
                        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                            return;
                        }
                        lastClickTime = SystemClock.elapsedRealtime();
                        Intent intent = new Intent(HomeActivity.this, ProfessorsActivity.class);
                        intent.putExtra("student", student);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void academicinfoButtonEvent() {
        cardView_academicinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(HomeActivity.this, AcademicInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void scheduleButtonEvent() {
        cardView_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.getStudent(user, new SimpleCallBack<Student>() {
                    @Override
                    public void callback(Student student) {
                        if (student != null) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            Intent intent = new Intent(HomeActivity.this, ScheduleActivity.class);
                            intent.putExtra(CommonVariables.STUDENT, student);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private void newsButtonEvent() {
        cardView_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.getStudent(user, new SimpleCallBack<Student>() {
                    @Override
                    public void callback(Student student) {
                        if (student != null) {
                            if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                                return;
                            }
                            lastClickTime = SystemClock.elapsedRealtime();
                            Intent intent = new Intent(HomeActivity.this, NewsActivity.class);
                            intent.putExtra(CommonVariables.STUDENT, student);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    private void homeworkButtonEvent() {
        cardView_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.getStudent(user, new SimpleCallBack<Student>() {
                    @Override
                    public void callback(Student student) {
                        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                            return;
                        }
                        lastClickTime = SystemClock.elapsedRealtime();
                        Intent intent = new Intent(HomeActivity.this, HomeworkActivity.class);
                        intent.putExtra(CommonVariables.STUDENT, student);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void activityButtonEvent() {
        cardView_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.getStudent(user, new SimpleCallBack<Student>() {
                    @Override
                    public void callback(Student student) {
                        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                            return;
                        }
                        lastClickTime = SystemClock.elapsedRealtime();
                        Intent intent = new Intent(HomeActivity.this, DisciplinesActivity.class);
                        intent.putExtra(CommonVariables.STUDENT, student);
                        startActivity(intent);
                    }
                });
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
                                SaveSharedPreference.clearSharedPreferences(HomeActivity.this);
                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Are you sure you want to log out?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        user = null;
        if (bundle != null) {
            user = getIntent().getParcelableExtra("user");
            if (user != null)
                SaveSharedPreference.setUser(HomeActivity.this, user.getEmail());
        }
    }

    private void checkIfUserLogged() {
        if (SaveSharedPreference.getUserName(HomeActivity.this).length() == 0) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            if (SaveSharedPreference.getUser(HomeActivity.this).length() == 0) {
                Toast.makeText(HomeActivity.this,
                        "Something went wrong, please log in again!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                user = new User(SaveSharedPreference.getUser(HomeActivity.this));
            }
        }
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
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
