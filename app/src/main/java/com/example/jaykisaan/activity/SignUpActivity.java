package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jaykisaan.R;
import com.example.jaykisaan.Model.UserHalper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private ImageView cancel;
    private EditText signupfullnameet, signupemailet, signupmobilenoet, signuppasswordet;
    private RadioGroup signupgenderbuttongroup, signupusertypebuttongroup;
    private RadioButton  genderbutton, usertypebutton;
    private Button signupjoinusbutton;
    ProgressBar progressBar;
    ProgressDialog mprogress;

    // Cloude Data store Initialize
    FirebaseAuth mAuth;
    FirebaseFirestore database;
    // Realtime data Store Initialize
    FirebaseDatabase rootNode;
    DatabaseReference reference;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        cancel = (ImageView) findViewById(R.id.cancel);

        signupfullnameet = (EditText) findViewById(R.id.signupfullnameet);
        signupemailet = (EditText) findViewById(R.id.signupemailet);
        signupmobilenoet = (EditText) findViewById(R.id.signupmobilenoet);
        signuppasswordet = (EditText) findViewById(R.id.signuppasswordet);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mprogress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        signupgenderbuttongroup = (RadioGroup) findViewById(R.id.signupgenderbuttongroup);
        signupusertypebuttongroup = (RadioGroup) findViewById(R.id.signupusertypebuttongroup);

        signupjoinusbutton = (Button) findViewById(R.id.signupjoinusbutton);

        // signup cancell process

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // When Join Us click Button

        signupjoinusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gender radio button selector

                int genderselecter = signupgenderbuttongroup.getCheckedRadioButtonId();
                genderbutton = (RadioButton) findViewById(genderselecter);


                // UserType radio button selector

                int usertypeselector = signupusertypebuttongroup.getCheckedRadioButtonId();
                usertypebutton = (RadioButton) findViewById(usertypeselector);


                if (validate()){
                    // cloud upload data into database

                    final String fullname = signupfullnameet.getText().toString().trim();
                    final String emailid = signupemailet.getText().toString().trim();
                    final String mobileno = signupmobilenoet.getText().toString().trim();
                    final String mfgender = genderbutton.getText().toString();
                    final String hpassword = signuppasswordet.getText().toString().trim();
                    final String usertype = usertypebutton.getText().toString();


                    mAuth.createUserWithEmailAndPassword(emailid,hpassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        // Send Verification Link

                                        final FirebaseUser emailverify = mAuth.getCurrentUser();
                                        emailverify.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(SignUpActivity.this, "Varification Email has been send", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("Email not send !", "===>" + e.getMessage());
                                            }

                                        });
                                        Toast.makeText(SignUpActivity.this, "Registered Succesfully", Toast.LENGTH_SHORT).show();

                                        // Store Realtime database storedata

                                        rootNode = FirebaseDatabase.getInstance();
                                        reference = rootNode.getReference("User");

                                        String user_id = null;
                                        UserHalper userHalper = new UserHalper(fullname, emailid, mobileno, mfgender, hpassword, usertype, user_id);
                                        reference.child(mobileno).setValue(userHalper);

                                        // Store in Cloude Database
                                        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                        Map<String, String> userinfo = new HashMap<>();
                                        userinfo.put("Email ID", emailid);
                                        userinfo.put("Full Name", fullname);
                                        userinfo.put("Mobile Number", mobileno);
                                        userinfo.put("Gender", mfgender);
                                        userinfo.put("User-Type", usertype);
                                        userinfo.put("Password", hpassword);
                                        userinfo.put("User_ID", user_id);

                                        database = FirebaseFirestore.getInstance();
                                        database.collection("User").document(user_id)
                                                .set(userinfo)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        finish();
                                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getApplicationContext(), "Something went's Wrong !", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            });
                    mprogress.setMessage("Please wait...");
                    mprogress.show();
                }
            }
        });
    }

    public void onBackPressed(){
        Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    private boolean validate() {
        Boolean result = false;

        String fullname = signupfullnameet.getText().toString().trim();
        String emailid = signupemailet.getText().toString().trim();
        String mobileno = signupmobilenoet.getText().toString().trim();
        String password = signuppasswordet.getText().toString();

        // check name
        if (fullname.length() == 0){
            Toast.makeText(this,"Please Enter Name", Toast.LENGTH_SHORT).show();
        }

        // check email
        else if (emailid.length() == 0 ){
            Toast.makeText(this,"Please Enter Email", Toast.LENGTH_SHORT).show();
        }

        // check mobileno
        else if (mobileno.length() == 0 || mobileno.equals(10)){
            Toast.makeText(this,"Enter Mobile No", Toast.LENGTH_SHORT).show();
        }

        // check gender
        else if (signupgenderbuttongroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
        }

        //check password
        else if (password.length() == 0){
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
        }

        // check usertype
        else if (signupusertypebuttongroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Select User-Type", Toast.LENGTH_SHORT).show();
        }

        else{
            result = true;
        }
        return result;
    }
}