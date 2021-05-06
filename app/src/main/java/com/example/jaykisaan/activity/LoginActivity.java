package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaykisaan.R;
import com.example.jaykisaan.util.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class LoginActivity extends AppCompatActivity {

    EditText emailet,passwordet;
    TextView forgottv,signuplink;
    Button loginbutton;
    private ProgressBar progress_bar;
    private ProgressDialog mprogress;
    int counter = 0;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailet = (EditText) findViewById(R.id.emailet);
        passwordet = (EditText) findViewById(R.id.passwordet);
        forgottv = (TextView) findViewById(R.id.forgottv);
        signuplink = (TextView) findViewById(R.id.signuplink);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        progress_bar = findViewById(R.id.progress_bar);
        mprogress = new ProgressDialog(this);



        mAuth = FirebaseAuth.getInstance();

        forgottv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_bar.setVisibility(View.VISIBLE);
                Intent i = new Intent(LoginActivity.this,ForgottenPassword.class);
                startActivity(i);
                finish();

            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("email","===>"+emailet.getText());
                Log.e("Passsword","===>"+passwordet.getText());



                validate(emailet.getText().toString(), passwordet.getText().toString());
                /*progress_bar.setVisibility(View.VISIBLE);*/
                mprogress.setMessage("Loading...");
                mprogress.show();


            }
        });

        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_bar.setVisibility(View.VISIBLE);

                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void validate(final String UserEmail, String UserPassword) {
        String email = UserEmail;
        String password = UserPassword;

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            final FirebaseFirestore database = FirebaseFirestore.getInstance();

                            DocumentReference noteRef = database.collection("User").document(userid);

                            noteRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        Utility.setIsLogin(getApplicationContext(),"isLogin");
                                        Intent i = new Intent(LoginActivity.this, FarSeedWholeActivity.class);
                                        startActivity(i);
                                        finish();

                                        // if user-type wise login this methord
                                        /*String user_type = documentSnapshot.getString("User-Type");

                                        if (user_type.equals("Farmer")){
                                            finish();
                                            Intent i = new Intent(LoginActivity.this, FarSeedWholeActivity.class);
                                            startActivity(i);
                                        }
                                        else if (user_type.equals("Whole-Seller")){
                                            finish();
                                            Intent i = new Intent(LoginActivity.this, FarSeedWholeActivity.class);
                                            startActivity(i);
                                        }
                                        else if (user_type.equals("Seed-Seller")){
                                            finish();
                                            Intent i = new Intent(LoginActivity.this, FarSeedWholeActivity.class);
                                            startActivity(i);
                                        }*/

                                    } else {
                                        mprogress.dismiss();
                                        Toast.makeText(getApplicationContext(), "Such a not valid user", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            mprogress.dismiss();
                            Toast.makeText(getApplicationContext(), "Such a not valid user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        counter++;
        if (counter == 1){
            Toast.makeText(this,"double backpress exit the apps",Toast.LENGTH_LONG).show();
        }
        else if (counter == 3){
            super.onBackPressed();
        }
    }

}
