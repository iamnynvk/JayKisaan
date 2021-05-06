package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaykisaan.R;
import com.example.jaykisaan.util.Utility;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageButton userphoto;
    TextView username;
    TextView usertype;
    Button editprofile;
    RelativeLayout setting_menu;
    TextView setting_textview;
    RelativeLayout help_support_menu;
    TextView help_support_textview;
    Button logout_button;
    ImageView notification;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Bottom Navigation Manage here..
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        notification = findViewById(R.id.notification);
        username = findViewById(R.id.user_name);
        usertype = findViewById(R.id.user_type);
        editprofile = findViewById(R.id.edit_profile_button);
        setting_menu = findViewById(R.id.setting_menu);
        setting_textview = findViewById(R.id.setting);
        help_support_menu = findViewById(R.id.help_menu);
        help_support_textview = findViewById(R.id.help_support);
        logout_button = findViewById(R.id.logout_button);
        userphoto = findViewById(R.id.user_photo);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.setIsLogin(ProfileActivity.this,"");
                startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                finish();
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));
                finish();
            }
        });

        setting_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,UserSettingActivity.class));
                finish();
            }
        });

        help_support_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,HelpSupportActivity.class));
                finish();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,NotificationActivity.class));
                finish();
            }
        });

        showDetail();






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
                        Intent iii = new Intent(getApplicationContext(),SellActivity.class);
                        startActivity(iii);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.myads:
                        Intent iv= new Intent(getApplicationContext(),MyAdsActivity.class);
                        startActivity(iv);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.profile:
                        onBackPressed();
                        return true;
                }
                return false;
            }
        });
    }

    private void showDetail() {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference reference = FirebaseFirestore.getInstance().collection("User").document(userid);


        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String fullname = documentSnapshot.getString("Full Name");
                    String yourtype = documentSnapshot.getString("User-Type");

                    username.setText(fullname);
                    usertype.setText(yourtype);
                }
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