package com.example.ubbapp.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ubbapp.R;
import com.example.ubbapp.structures.SaveSharedPreference;

public class SendEmailActivity extends AppCompatActivity {

    ImageButton button_back;

    ImageButton button_menu;
    ImageButton button_settings;
    ImageButton button_logout;
    boolean isOpen = false;

    TextView textView_subject;
    TextView textView_message;

    EditText editText_subject;
    EditText editText_message;
    Button button_sendemail;

    private String professorEmail;

    Animation fromtop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        getDataFromBundle();
        initializeComponents();
    }

    private void initializeComponents() {
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);

        textView_subject = findViewById(R.id.textView_subject);
        textView_message = findViewById(R.id.textView_message);

        textView_subject.setAnimation(fromtop);
        textView_message.setAnimation(fromtop);

        editText_subject = findViewById(R.id.editText_subject);
        editText_message = findViewById(R.id.editText_message);

        editText_subject.setAnimation(fromtop);
        editText_message.setAnimation(fromtop);

        button_sendemail = findViewById(R.id.button_sendemail);
        sendEmailButtonEvent();

        button_settings = findViewById(R.id.imageButton_settings);

        button_menu = findViewById(R.id.imageButton_menu);
        menuButtonEvent();

        button_logout = findViewById(R.id.imageButton_logout);
        logOutButtonEvent();

        button_back = findViewById(R.id.button_back);
        backButtonEvent();
    }

    private void sendEmailButtonEvent() {
        button_sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{professorEmail});
                intent.putExtra(Intent.EXTRA_SUBJECT, editText_subject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, editText_message.getText().toString());

                try {
                    startActivity(Intent.createChooser(intent, "Send email...."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendEmailActivity.this, "You have no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                                SaveSharedPreference.clearSharedPreferences(SendEmailActivity.this);
                                Intent intent = new Intent(SendEmailActivity.this, LoginActivity.class);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(SendEmailActivity.this);
                builder.setMessage("Are you sure you want to log out?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            professorEmail = bundle.getString("professorEmail");
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
        finish();
    }
}
