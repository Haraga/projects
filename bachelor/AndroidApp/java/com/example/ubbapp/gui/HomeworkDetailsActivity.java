package com.example.ubbapp.gui;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ubbapp.R;
import com.example.ubbapp.model.Homework;
import com.example.ubbapp.model.variables.CommonVariables;

public class HomeworkDetailsActivity extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    private static long lastClickTimeMonday = 0;

    ImageButton button_back;
    Button downloadButton;

    private Homework homework;
    TextView textView_currentweektext;

    Animation fromleft;
    Animation fromright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework_details);

        getDataFromBundle();
        initializeComponents();
    }

    private void initializeComponents() {
        fromright = AnimationUtils.loadAnimation(this, R.anim.fromright);
        fromleft = AnimationUtils.loadAnimation(this, R.anim.fromleft);

        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        TextView title = findViewById(R.id.textView_homeworktitle);
        title.setText(homework.getTitle());
        title.setAnimation(fromleft);

        TextView text = findViewById(R.id.textView_homeworkcontent);
        text.setText(homework.getText());
        text.setAnimation(fromright);

        TextView deadline = findViewById(R.id.textView_deadlineweekno);
        deadline.setText(String.valueOf(homework.getToCompleteWeek()));
        deadline.setAnimation(fromleft);

        textView_currentweektext = findViewById(R.id.textView_currentweektext);
        textView_currentweektext.setAnimation(fromleft);

        downloadButton = findViewById(R.id.button_downloadpdf);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - lastClickTimeMonday < 1000) {
                    return;
                }
                lastClickTimeMonday = SystemClock.elapsedRealtime();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_STORAGE_CODE);
                    } else {
                        startDownload();
                    }
                } else {
                    startDownload();
                }
            }
        });
    }

    private void startDownload() {
        String url = homework.getHomeworkDownloadLink();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        //allowed network types
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        //title and description of download
        request.setTitle(homework.getTitle());
        request.setDescription("Your homework is downloading!");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + homework.getTitle());
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_STORAGE_CODE) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                startDownload();
            } else {
                Toast.makeText(this, "Permission denied....!", Toast.LENGTH_SHORT).show();
            }
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

    private void getDataFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            homework = getIntent().getParcelableExtra(CommonVariables.HOMEWORK);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
