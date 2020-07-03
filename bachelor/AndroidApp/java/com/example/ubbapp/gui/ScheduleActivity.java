package com.example.ubbapp.gui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ubbapp.R;
import com.example.ubbapp.controller.CurrentWeekController;
import com.example.ubbapp.controller.ScheduleController;
import com.example.ubbapp.model.Discipline;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.structures.SaveSharedPreference;
import com.example.ubbapp.structures.SimpleCallBack;
import com.example.ubbapp.structures.WeekDays;

import java.util.Calendar;
import java.util.Locale;

public class ScheduleActivity extends AppCompatActivity {

    ImageButton button_back;

    ImageButton button_menu;
    ImageButton button_settings;
    ImageButton button_logout;
    boolean isOpen = false;

    private int currentDayButtonId;
    private Button selectedDayButton;
    private String selectedDay;

    private long lastClickTime = 0;

    private long lastClickTimeMonday = 0;
    private long lastClickTimeTuesday = 0;
    private long lastClickTimeWednesday = 0;
    private long lastClickTimeThursday = 0;
    private long lastClickTimeFriday = 0;

    private Button mondayButton;
    private Button tuesdayButton;
    private Button wednesdayButton;
    private Button thursdayButton;
    private Button fridayButton;

    private ImageButton leftArrow, rightArrow;
    private int currentSelectedWeek;
    private TextView selectedWeek;
    private int currentWeek = -1;

    private Button button_weekly;
    private Button button_daily;
    private TextView textView_weekly;
    private TextView textView_daily;
    private ConstraintLayout bottom_menu_weekly;
    private ConstraintLayout bottom_menu_daily;

    private CurrentWeekController currentWeekController;
    private ScheduleController scheduleController;
    private Student student;
    private LinearLayout scheduleView;

    Animation fromright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule);

        getDataFromBundle();
        initializeComponents();
        getCurrentWeek();
    }

    private void initializeComponents() {
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);

        scheduleController = new ScheduleController();
        currentWeekController = new CurrentWeekController();

        scheduleView = findViewById(R.id.scheduleView);
        scheduleView.setAnimation(fromright);

        button_menu = findViewById(R.id.imageButton_menu);
        menuButtonEvent();

        button_settings = findViewById(R.id.imageButton_settings);

        button_logout = findViewById(R.id.imageButton_logout);
        logOutButtonEvent();

        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        mondayButton = findViewById(R.id.button_monday);
        tuesdayButton = findViewById(R.id.button_tuesday);
        wednesdayButton = findViewById(R.id.button_wednesday);
        thursdayButton = findViewById(R.id.button_thursday);
        fridayButton = findViewById(R.id.button_friday);

        leftArrow = findViewById(R.id.imageButton_leftarrow);
        rightArrow = findViewById(R.id.imageButton_rightarrow);
        selectedWeek = findViewById(R.id.textView_week);

        button_weekly = findViewById(R.id.button_weekly);
        button_daily = findViewById(R.id.button_daily);

        textView_weekly = findViewById(R.id.textView_weekly);
        textView_daily = findViewById(R.id.textView_daily);

        bottom_menu_weekly = findViewById(R.id.bottom_menu_weekly);
        bottom_menu_daily = findViewById(R.id.bottom_menu_daily);

        clickWeekly();
        clickDaily();

        scheduleWeekButtons();
        scheduleDayButtons();
    }

    private void getCurrentWeek() {
        currentWeekController.getCurrentWeek(new SimpleCallBack<String>() {
            @Override
            public void callback(String data) {
                currentWeek = Integer.parseInt(data);
                if (currentWeek % 2 == 0) {
                    currentWeek = 2;
                } else {
                    currentWeek = 1;
                }
                generateDailySchedule(getCurrentDay());
            }
        });
    }

    private void clickWeekly() {
        button_weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                scheduleView.removeAllViews();

                textView_daily.setVisibility(View.GONE);
                textView_weekly.setVisibility(View.VISIBLE);

                button_weekly.setVisibility(View.GONE);
                button_daily.setVisibility(View.VISIBLE);

                bottom_menu_daily.setVisibility(View.GONE);
                bottom_menu_weekly.setVisibility(View.VISIBLE);

                currentWeekController.getCurrentWeek(new SimpleCallBack<String>() {
                    @Override
                    public void callback(String data) {
                        currentSelectedWeek = Integer.parseInt(data);
                        if (currentSelectedWeek % 2 == 0) {
                            currentSelectedWeek = 2;
                            selectedWeek.setText("Week 2");
                        } else {
                            currentSelectedWeek = 1;
                            selectedWeek.setText("Week 1");
                        }
                        generateWeeklySchedule(currentSelectedWeek);
                    }
                });
            }
        });
    }

    private void clickDaily() {
        button_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();

                scheduleView.removeAllViews();

                textView_weekly.setVisibility(View.GONE);
                textView_daily.setVisibility(View.VISIBLE);

                button_daily.setVisibility(View.GONE);
                button_weekly.setVisibility(View.VISIBLE);

                bottom_menu_weekly.setVisibility(View.GONE);
                bottom_menu_daily.setVisibility(View.VISIBLE);

                generateDailySchedule(selectedDay);
            }
        });
    }

    private void scheduleWeekButtons() {
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                scheduleWeekButtonsAction();
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                scheduleWeekButtonsAction();
            }
        });
    }

    private void scheduleWeekButtonsAction() {
        if (currentSelectedWeek == 1) {
            scheduleView.removeAllViews();
            generateWeeklySchedule(2);
            selectedWeek.setText("Week 2");
            currentSelectedWeek = 2;
        } else {
            scheduleView.removeAllViews();
            generateWeeklySchedule(1);
            selectedWeek.setText("Week 1");
            currentSelectedWeek = 1;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void scheduleDayButtons() {

        mondayButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (SystemClock.elapsedRealtime() - lastClickTimeMonday < 1000) {
                    return true;
                }
                mondayButton.setPressed(true);
                lastClickTimeMonday = SystemClock.elapsedRealtime();
                scheduleViewContent(mondayButton, "Monday");
                return true;
            }
        });

        tuesdayButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (SystemClock.elapsedRealtime() - lastClickTimeTuesday < 1000) {
                    return true;
                }
                tuesdayButton.setPressed(true);
                lastClickTimeTuesday = SystemClock.elapsedRealtime();
                scheduleViewContent(tuesdayButton, "Tuesday");
                return true;
            }
        });

        wednesdayButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (SystemClock.elapsedRealtime() - lastClickTimeWednesday < 1000) {
                    return true;
                }
                wednesdayButton.setPressed(true);
                lastClickTimeWednesday = SystemClock.elapsedRealtime();
                scheduleViewContent(wednesdayButton, "Wednesday");
                return true;
            }
        });


        thursdayButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (SystemClock.elapsedRealtime() - lastClickTimeThursday < 1000) {
                    return true;
                }
                thursdayButton.setPressed(true);
                lastClickTimeThursday = SystemClock.elapsedRealtime();
                scheduleViewContent(thursdayButton, "Thursday");
                return true;
            }
        });

        fridayButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (SystemClock.elapsedRealtime() - lastClickTimeFriday < 1000) {
                    return true;
                }
                fridayButton.setPressed(true);
                lastClickTimeFriday = SystemClock.elapsedRealtime();
                scheduleViewContent(fridayButton, "Friday");
                return true;
            }
        });
    }

    private void scheduleViewContent(Button clickedButton, String day) {
        if (currentDayButtonId != clickedButton.getId()) {
            selectedDayButton.setPressed(false);
            clickedButton.setPressed(true);

            final int childCount = scheduleView.getChildCount();
            if (childCount > 0) {
                scheduleView.removeAllViews();
            }

            generateDailySchedule(clickedButton.getContentDescription().toString());
            currentDayButtonId = clickedButton.getId();
            selectedDayButton = clickedButton;
            selectedDay = day;
        }
    }

    private void getDaySchedule(final String currentDay, int currentWeek) {
        scheduleController.getDisciplineWeeklySchedule(student.getGroup(), currentDay, currentWeek, new SimpleCallBack<int[]>() {
            @Override
            public void callback(int[] data) {
                createWeeklyCardView(data, currentDay);
            }
        });
    }

    private void generateDailySchedule(String selectedDay) {
        scheduleController.getDisciplinesDailySchedule(student.getGroup(), selectedDay, currentWeek, new SimpleCallBack<Discipline>() {
            @Override
            public void callback(Discipline data) {
                createDailyCardView(data);
            }
        });
    }

    private void generateWeeklySchedule(int currentWeek) {
        for (WeekDays day : WeekDays.values()) {
            getDaySchedule(day.name(), currentWeek);
        }
    }

    private String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SATURDAY:
            case Calendar.SUNDAY:
            case Calendar.MONDAY:
                setNextDayButton(mondayButton, "Monday");
                return "Monday";
            case Calendar.TUESDAY:
                setNextDayButton(tuesdayButton, "Tuesday");
                return "Tuesday";
            case Calendar.WEDNESDAY:
                setNextDayButton(wednesdayButton, "Wednesday");
                return "Wednesday";
            case Calendar.THURSDAY:
                setNextDayButton(thursdayButton, "Thursday");
                return "Thursday";
            case Calendar.FRIDAY:
                setNextDayButton(fridayButton, "Friday");
                return "Friday";
            default:
                return "";
        }
    }

    private void setNextDayButton(Button button, String day) {
        currentDayButtonId = button.getId();
        selectedDayButton = button;
        selectedDay = day;
        selectedDayButton.setPressed(true);
    }

    private void createDailyCardView(final Discipline discipline) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View child = inflater.inflate(R.layout.schedule_day_card, null, false);
        CardView scheduleCardView = child.findViewById(R.id.cardView_schedule);

        TextView disciplineTypeView = child.findViewById(R.id.disciplineType);
        TextView disciplineName = child.findViewById(R.id.disciplineName);
        TextView disciplineInterval = child.findViewById(R.id.interval);
        TextView dayOfTheWeek = child.findViewById(R.id.dayOfTheWeek);
        TextView roomName = child.findViewById(R.id.textView_room);
        View disciplineColor = child.findViewById(R.id.disciplineColor);

        disciplineName.setText(discipline.getName());
        disciplineInterval.setText(discipline.getInterval().toString());
        dayOfTheWeek.setText(discipline.getDay().name());
        roomName.setText(discipline.getAddress().getRoom());
        switch (discipline.getType()) {
            case CommonVariables.LABORATORY:
                disciplineColor.setBackgroundColor(getResources().getColor(R.color.red));
                disciplineTypeView.setText(CommonVariables.LAB);
                break;
            case CommonVariables.SEMINARY:
                disciplineColor.setBackgroundColor(getResources().getColor(R.color.yellow));
                disciplineTypeView.setText(CommonVariables.SEMINARY);
                break;
            case CommonVariables.COURSE:
                disciplineColor.setBackgroundColor(getResources().getColor(R.color.green));
                disciplineTypeView.setText(CommonVariables.COURSE);
                break;
        }

        scheduleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                String uri = String.format(Locale.ENGLISH,
                                        "http://maps.google.com/maps?q=loc:%f,%f",
                                        discipline.getAddress().getLatitude(),
                                        discipline.getAddress().getLongitude());
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
                builder.setMessage("Are you sure you want to open Google Maps?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        scheduleView.addView(child);
    }

    private void createWeeklyCardView(int[] data, String currentDay) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View child = inflater.inflate(R.layout.schedule_week_card, null, false);

        TextView laboratories = child.findViewById(R.id.textView_week_laboratoriesNumber);
        laboratories.setText(String.valueOf(data[2]));

        TextView seminaries = child.findViewById(R.id.textView_week_seminariesNumber);
        seminaries.setText(String.valueOf(data[1]));

        TextView courses = child.findViewById(R.id.textView_week_coursesNumber);
        courses.setText(String.valueOf(data[0]));

        TextView day = child.findViewById(R.id.textView_dayOfWeek);

        if (currentDay.equals("MONDAY")) {
            day.setText("Mo");
        }

        if (currentDay.equals("TUESDAY")) {
            day.setText("Tu");
        }

        if (currentDay.equals("WEDNESDAY")) {
            day.setText("We");
        }

        if (currentDay.equals("THURSDAY")) {
            day.setText("Th");
        }

        if (currentDay.equals("FRIDAY")) {
            day.setText("Fr");
        }

        scheduleView.addView(child);
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
                                SaveSharedPreference.clearSharedPreferences(ScheduleActivity.this);
                                Intent intent = new Intent(ScheduleActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);
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
}
