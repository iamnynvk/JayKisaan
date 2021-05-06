package com.example.jaykisaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.jaykisaan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPasswordActivity extends AppCompatActivity {

    private EditText newpasswordet;

    // Cloude Data store Initialize
    FirebaseAuth mAuth;

    // Realtime data Store Initialize
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    String mobilenumber = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Intent intent = getIntent();
        String mobileno = intent.getStringExtra("mobilenumber");
        mobilenumber = mobileno;
        Log.e("NewPassword_num","===>"+ mobilenumber);

        mAuth = FirebaseAuth.getInstance();

        newpasswordet = findViewById(R.id.newpasswordet);



        /*findViewById(R.id.changepassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getData from fied
                String _newPassword = newpasswordet.getText().toString().trim();
                String _phonenumber = getIntent().getStringExtra(mobilenumber);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
                reference.child(_phonenumber).child("password").setValue(_newPassword);

                startActivity(new Intent(getApplicationContext(),ForgottenPasswordSuccessfullActivity.class));
                finish();
            }
        });*/
    }
}