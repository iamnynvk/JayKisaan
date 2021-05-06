package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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

public class CropingVideoSlide extends AppCompatActivity {
    RecyclerView recyclerView_view;

    ArrayList<VideoLinksData> video_link;

    DatabaseReference mdatabaseref;
    StorageReference mstorageref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croping_video_slide);
        recyclerView_view = findViewById(R.id.videosetrecycleview_croping);
        
        recyclerView_view.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_view.setHasFixedSize(true);

        mdatabaseref = FirebaseDatabase.getInstance().getReference();
        mstorageref = FirebaseStorage.getInstance().getReference();
        video_link = new ArrayList<VideoLinksData>();

        cropvideo();
    }

    private void cropvideo() {
        final Query query = mdatabaseref.child("Croping_Videos");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    VideoLinksData datao = new VideoLinksData();

                    datao.setLink(dataSnapshot.child("url").getValue().toString());

                    video_link.add(datao);
                }
                YoutubeVideoHolder holder = new YoutubeVideoHolder(video_link,getApplicationContext());
                recyclerView_view.setAdapter(holder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CropingVideoSlide.this,FarSeedWholeActivity.class));
        finish();
    }
}