package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jaykisaan.Data.YoutubeVideoHolder;
import com.example.jaykisaan.Model.VideoLinksData;
import com.example.jaykisaan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TechnicalVideoSlide extends AppCompatActivity {
    RecyclerView recyclerView;

    ArrayList<VideoLinksData> videolink;

    DatabaseReference mdatabaseref;
    StorageReference mstorageref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_video_slide);
        recyclerView = findViewById(R.id.videosetrecycleview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mdatabaseref = FirebaseDatabase.getInstance().getReference();
        mstorageref = FirebaseStorage.getInstance().getReference();
        videolink = new ArrayList<>();
        videoinit();
    }

    private void videoinit() {
        final Query query = mdatabaseref.child("Technical_Videos");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    VideoLinksData data = new VideoLinksData();

                    data.setLink(dataSnapshot.child("url").getValue().toString());

                    videolink.add(data);
                }
                YoutubeVideoHolder holder = new YoutubeVideoHolder(videolink,getApplicationContext());
                recyclerView.setAdapter(holder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TechnicalVideoSlide.this,FarSeedWholeActivity.class));
        finish();
    }
}