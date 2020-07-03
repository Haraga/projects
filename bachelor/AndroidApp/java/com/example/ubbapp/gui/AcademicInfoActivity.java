package com.example.ubbapp.gui;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ubbapp.R;

public class AcademicInfoActivity extends AppCompatActivity {

    private WebView webView_academicinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_info);

        webView_academicinfo = findViewById(R.id.webView_academicinfo);
        webView_academicinfo.setWebViewClient(new WebViewClient());
        webView_academicinfo.loadUrl("https://academicinfo.ubbcluj.ro/Info/");

        WebSettings webSettings = webView_academicinfo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
    }

    @Override
    public void onBackPressed() {
        if (webView_academicinfo.canGoBack()) {
            webView_academicinfo.goBack();
        } else {
            finish();
        }
    }
}
