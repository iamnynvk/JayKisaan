package com.example.jaykisaan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jaykisaan.R;
import com.example.jaykisaan.util.Utility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // For Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Splash Screen Handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utility.getIsLogin(getApplicationContext()) != null && Utility.getIsLogin(getApplicationContext()).trim().length() > 0) {
                    Log.e("task", "=====>" + Utility.getIsLogin(getApplicationContext()).trim().length());
                    Intent mainActivity = new Intent(MainActivity.this, FarSeedWholeActivity.class);
                    startActivity(mainActivity);
                    finish();
                } else {
                    Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
            }
        }, 2000);
    }
}