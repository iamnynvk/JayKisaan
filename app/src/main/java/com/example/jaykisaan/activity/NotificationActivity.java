package com.example.jaykisaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jaykisaan.R;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}