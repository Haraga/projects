package com.example.ubbapp.gui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.ubbapp.R;
import com.example.ubbapp.controller.ProfessorsController;
import com.example.ubbapp.model.Professor;
import com.example.ubbapp.model.Student;
import com.example.ubbapp.model.variables.CommonVariables;
import com.example.ubbapp.repository.database.FirebaseConstants;
import com.example.ubbapp.structures.SaveSharedPreference;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProfessorsActivity extends AppCompatActivity {

    ImageButton button_back;

    ImageButton button_menu;
    ImageButton button_settings;
    ImageButton button_logout;
    boolean isOpen = false;

    private LinearLayout layout;
    private TextView textView_professors;

    private long lastClickTime = 0;

    private Student student;
    private ProfessorsController controller;
    private StorageReference storage;

    private List<String> professors = new ArrayList<>();

    Animation fromleft;
    Animation fromright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professors);

        getDataFromBundle();
        initializeComponents();
        generateProfessors();
    }

    private void initializeComponents() {
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);

        button_settings = findViewById(R.id.imageButton_settings);

        button_menu = findViewById(R.id.imageButton_menu);
        menuButtonEvent();

        button_logout = findViewById(R.id.imageButton_logout);
        logOutButtonEvent();

        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        controller = new ProfessorsController();
        storage = FirebaseStorage.getInstance().getReference().child(FirebaseConstants.PROFESSOR);

        layout = findViewById(R.id.professorsView);
        layout.setAnimation(fromright);

        textView_professors = findViewById(R.id.textView_professors);
        textView_professors.setAnimation(fromleft);
    }

    private void generateProfessors() {
        controller.getProfessors(student.getGroup(), new SimpleCallBack<Professor>() {
            @Override
            public void callback(Professor data) {
                if(!professors.contains(data.getFirstName() + " " + data.getLastName())){
                    createProfessorCardView(data);
                }
            }
        });
    }

    private void createProfessorCardView(final Professor professor) {
        professors.add(professor.getFirstName() + " " + professor.getLastName());
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View child = inflater.inflate(R.layout.professor_card, null, false);
        CardView professorCardView = child.findViewById(R.id.cardView_professor);

        final ImageView imageView = child.findViewById(R.id.circleImageView_professorimage);
        TextView professorRank = child.findViewById(R.id.textView_professortitle);
        TextView professorName = child.findViewById(R.id.textView_professorname);
        TextView professorEmail = child.findViewById(R.id.textView_emailaddress);

        professorRank.setText(professor.getTitle());
        String name = professor.getFirstName() + " " + professor.getLastName();
        professorName.setText(name);
        professorEmail.setText(professor.getEmail());
        final String path = professor.getFirstName() + "-" + professor.getLastName() + ".jpg";

        storage.child(path).getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadPhoto("defaultUser.png", imageView);
            }
        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                loadPhoto(path, imageView);
            }
        });

        professorCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
                    return;
                }
                lastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(ProfessorsActivity.this, SendEmailActivity.class);
                intent.putExtra("professorEmail", professor.getEmail());
                startActivity(intent);
            }
        });

        layout.addView(child);
    }

    private void loadPhoto(String path, ImageView view) {
        Glide.with(this).load(storage.child(path)).into(view);
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
                                SaveSharedPreference.clearSharedPreferences(ProfessorsActivity.this);
                                Intent intent = new Intent(ProfessorsActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfessorsActivity.this);
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
