package com.example.ubbapp.gui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ubbapp.R;
import com.example.ubbapp.controller.LogInController;
import com.example.ubbapp.model.User;
import com.example.ubbapp.structures.SaveSharedPreference;
import com.example.ubbapp.structures.SimpleCallBack;
import com.example.ubbapp.structures.Validator;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotPasswordButton;
    private Button logInButton;
    private EditText editText_email, editText_password;
    private LogInController logInController;
    private Validator validator;
    private boolean backButtonSure = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.initializeComponents();
        this.validator = new Validator();
        this.logInController = new LogInController(validator);
    }

    public void initializeComponents() {
        this.logInButton = findViewById(R.id.button_login);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        forgotPasswordButton = findViewById(R.id.textView_forgotpasswordbutton);
        resetPassword();

        this.editText_email = findViewById(R.id.editText_username);
        this.editText_password = findViewById(R.id.editText_password);
    }

    public void logIn() {
        if (this.checkIfFieldsAreCompleted()) {
            this.logInController.checkCredentials(this.editText_email.getText().toString()
                    , this.editText_password.getText().toString(),
                    new SimpleCallBack<User>() {
                        @Override
                        public void callback(User data) {
                            if (data != null) {
                                Toast.makeText(getApplicationContext(), "Logged in successfully!"
                                        , Toast.LENGTH_LONG).show();
                                SaveSharedPreference.setUserName(LoginActivity.this, editText_email.getText().toString());
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                intent.putExtra("user", data);
                                intent.putExtra("validator", validator);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Username or password is incorrect!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    public void resetPassword() {
        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText_email.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please fill in the email to reset your password!",
                            Toast.LENGTH_LONG).show();
                } else {
                    logInController.resetPassword(editText_email.getText().toString());
                    Toast.makeText(getApplicationContext(), "We have sent a password reset email to that address!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean checkIfFieldsAreCompleted() {
        if (TextUtils.isEmpty(editText_email.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please fill in the email!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(editText_password.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please fill in the password!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!backButtonSure) {
            Toast.makeText(getBaseContext(), "Are you sure you want to quit?", Toast.LENGTH_LONG).show();
            backButtonSure = true;
        } else {
            backButtonSure = false;
            moveTaskToBack(true);
        }
    }
}
