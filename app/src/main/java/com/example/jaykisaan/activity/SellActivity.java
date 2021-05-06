package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jaykisaan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SellActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageView crops;
    ImageView vegetable;
    ImageView fruits;
    ImageView seeds;
    ImageView cancel;
    ImageView bell;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        crops = findViewById(R.id.crop_sell);
        vegetable = findViewById(R.id.vegetable_sell);
        fruits = findViewById(R.id.fruit_sell);
        seeds = findViewById(R.id.seed_sell);
        cancel = findViewById(R.id.cancel);
        bell = findViewById(R.id.notification);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellActivity.this,FarSeedWholeActivity.class));
                finish();
            }
        });

        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellActivity.this,NotificationActivity.class));
                finish();
            }
        });

        crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellActivity.this,OptionCropsActivity.class));
                finish();
            }
        });

        vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellActivity.this,OptionVegetablesActivity.class));
                finish();
            }
        });

        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellActivity.this,OptionFruitsActivity.class));
                finish();
            }
        });

        seeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SellActivity.this,OptionSeedsActivity.class));
                finish();
            }
        });

        // Bottom Navigation Manage here..
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.sell);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        Intent i = new Intent(getApplicationContext(),FarSeedWholeActivity.class);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.search:
                        Intent ii = new Intent(getApplicationContext(),SearchActivity.class);
                        startActivity(ii);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.sell:
                        onBackPressed();
                        return true;

                    case R.id.myads:
                        Intent iii = new Intent(getApplicationContext(),MyAdsActivity.class);
                        startActivity(iii);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.profile:
                        Intent iv= new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(iv);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(getApplicationContext(),FarSeedWholeActivity.class);
        startActivity(in);
        overridePendingTransition(0,0);
        finish();
    }
}