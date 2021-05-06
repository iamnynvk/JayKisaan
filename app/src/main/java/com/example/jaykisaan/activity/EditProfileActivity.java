package com.example.jaykisaan.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaykisaan.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    //Button Action
    Button change_photo;
    Button remove_photo;
    Uri resultUri_dp;
    ImageView images;
    Button SaveDetail;
    ProgressDialog mprogress;

    //EditText Action
    TextView personname;
    TextView mobile_no;
    TextView email,yourType;

    // database
    DatabaseReference mdatabaseref;
    StorageReference mstorageref;
    FirebaseUser muser;
    FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //EditText action
        personname = findViewById(R.id.edit_person_name);
        mobile_no = findViewById(R.id.edit_mobileno);
        email = findViewById(R.id.edit_emailid);
        yourType = findViewById(R.id.edit_type);

        //Button action
        change_photo = findViewById(R.id.change_photo_button);
        remove_photo = findViewById(R.id.remove_photo_button);
        images = findViewById(R.id.user_photo);
        SaveDetail = findViewById(R.id.saveDetail);

        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("All_Product_Detail");
        mstorageref = FirebaseStorage.getInstance().getReference();
        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();

        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(EditProfileActivity.this);
            }
        });

        remove_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView image = new ImageView(getApplicationContext());
                image.setImageBitmap(null);
                image.setBackgroundResource(R.drawable.man);
            }
        });

        showDetail();

        SaveDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri_dp = result.getUri();
                images.setImageURI(resultUri_dp);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void submit() {
        mprogress.setMessage("Upload Profile Photo");
        mprogress.show();

        if (resultUri_dp != null){
            final StorageReference FilePath = mstorageref.child("User_photo").child(resultUri_dp.getLastPathSegment());

            FilePath.putFile(resultUri_dp)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            FilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    DatabaseReference addphoto = mdatabaseref.push();

                                    Map<String, String> upload = new HashMap<>();
                                    upload.put("User_ID",muser.getUid());
                                    upload.put("Product_images", uri.toString());

                                    addphoto.setValue(upload);
                                    mprogress.dismiss();
                                    startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                                    finish();
                                }
                            });
                        }
                    });
        }


    }

    private void showDetail() {
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference reference = FirebaseFirestore.getInstance().collection("User").document(userid);


        reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String fullname = documentSnapshot.getString("Full Name");
                    String mobileno = documentSnapshot.getString("Mobile Number");
                    String emailid = documentSnapshot.getString("Email ID");
                    String yourtype = documentSnapshot.getString("User-Type");

                    personname.setText(fullname);
                    mobile_no.setText(mobileno);
                    email.setText(emailid);
                    yourType.setText(yourtype);
                }
            }
        });
    }


}