package com.example.ubbapp.gui;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ubbapp.R;
import com.example.ubbapp.controller.ForAttendanceController;
import com.example.ubbapp.model.ForAttendance;
import com.example.ubbapp.structures.SimpleCallBack;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class CheckLocationActivity extends AppCompatActivity {

    private ForAttendanceController forAttendanceController;

    Button button_getlocation;
    ImageButton button_back;

    private FusedLocationProviderClient fusedLocationProviderClient;
    TextView textView_locationtest;
    TextView textView_distance;
    TextView textView_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_location);

        button_back = findViewById(R.id.button_back);
        backButtonEvent();

        button_getlocation = findViewById(R.id.button_getlocation);
        getLocationButtonEvent();

        textView_locationtest = findViewById(R.id.textView_locationtest);
        textView_distance = findViewById(R.id.textView_distance);
        textView_test = findViewById(R.id.textView_test);
        test();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    private void getLocationButtonEvent() {
        button_getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });
    }

    private void getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    textView_locationtest.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());
                    double dist = distance(location.getLatitude(), location.getLongitude(), 45.706841, 24.132563);
                    textView_distance.setText("Distance: " + dist);
                }
            }
        });
    }

    private void test() {
        forAttendanceController.getForAttendance("SO", "12345", new SimpleCallBack<ForAttendance>() {
            @Override
            public void callback(ForAttendance data) {
                textView_test.setText("undoi");
            }
        });
    }

    //calculates the distance between two locations in kilometers
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 6371; //kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
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
