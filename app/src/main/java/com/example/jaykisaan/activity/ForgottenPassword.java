package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jaykisaan.R;
import com.google.common.base.MoreObjects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.regex.Pattern;

public class ForgottenPassword extends AppCompatActivity {

    private EditText forgottenmobilenoet;
    private ProgressBar progress_bar;
    ProgressDialog mprogress;
    FirebaseAuth mAuth;
/*    FirebaseDatabase rootNode;
    DatabaseReference reference;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

        forgottenmobilenoet = (EditText) findViewById(R.id.forgottenmobilenoet);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();
        mprogress = new ProgressDialog(this);



        findViewById(R.id.optsendbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Check Valid Mobile Number
                final String mobile = forgottenmobilenoet.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10 || mobile.startsWith("1") || mobile.startsWith("2") ||  mobile.startsWith("3") ||
                        mobile.startsWith("4") ||  mobile.startsWith("5") || mobile.startsWith("0")){
                    forgottenmobilenoet.setError("Enter a valid Number");
                    forgottenmobilenoet.requestFocus();
                    return;
                }
                /*progress_bar.setVisibility(View.VISIBLE);*/
                mprogress.setMessage("Please wait check in Server");
                mprogress.show();

                // Check Weather user exist or not in database
                Query checkUser = FirebaseDatabase.getInstance().getReference("User").child(mobile);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            forgottenmobilenoet.setError(null);

                            Intent i = new Intent(getApplicationContext(),OtpChecker.class);
                            i.putExtra("mobile",forgottenmobilenoet.getText());
                            startActivity(i);
                            finish();

                            /*progress_bar.setVisibility(View.GONE);*/
                            mprogress.dismiss();
                        }
                        else {
                           /* progress_bar.setVisibility(View.GONE);*/
                            mprogress.dismiss();
                            forgottenmobilenoet.setError("No such user exist!");
                            forgottenmobilenoet.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}