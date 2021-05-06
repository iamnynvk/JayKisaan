package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jaykisaan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewsActivity extends AppCompatActivity {
    ImageView notification,cancel;
    ImageView banner;
    TextView heading_title,description_colums;
    ProgressBar progressBar,progress_bar_image;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    String imageurl = "";
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        imageurl = getIntent().getStringExtra("imageurl");
        title = getIntent().getStringExtra("title");


        // Hooks
        notification = findViewById(R.id.notification);
        cancel = findViewById(R.id.cancel);
        banner = findViewById(R.id.banner);
        heading_title = findViewById(R.id.heading_title);
        description_colums = findViewById(R.id.description);
        progressBar = findViewById(R.id.progress_bar);
        progress_bar_image = findViewById(R.id.progress_bar_image);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(i);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Log.e("Image_Url", "===>" + imageurl);
        Log.e("Title", "===>" + title);

        progressBar.setVisibility(View.VISIBLE);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference().child("BannerNews");
        progress_bar_image.setVisibility(View.VISIBLE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    Glide.with(NewsActivity.this).load(imageurl).into(banner);
                    progress_bar_image.setVisibility(View.GONE);
                    heading_title.setText(title);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void onBackPressed(){
        Intent i = new Intent(NewsActivity.this,FarSeedWholeActivity.class);
        startActivity(i);
        finish();
    }
}