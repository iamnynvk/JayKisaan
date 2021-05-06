package com.example.jaykisaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.jaykisaan.R;

public class ForgottenPasswordSuccessfullActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password_successfull);

        // As like Splash Screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(ForgottenPasswordSuccessfullActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        },2000);
    }
}