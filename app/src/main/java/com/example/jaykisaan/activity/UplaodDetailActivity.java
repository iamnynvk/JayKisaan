package com.example.jaykisaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.jaykisaan.R;

public class UplaodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uplaod_detail);

        // Splash Screen Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(UplaodDetailActivity.this,FarSeedWholeActivity.class);
                startActivity(i);
                finish();
            }
        },1000);
    }
}