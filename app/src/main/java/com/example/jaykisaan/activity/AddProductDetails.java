package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jaykisaan.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;


public class AddProductDetails extends AppCompatActivity {

    Button galary,submit;
    ImageView images;
    ImageView close, notification;
    Uri resultUri;
    EditText productname, productquantity, productprice, addressline1, addressline2, hometown, mobileno, state;
    ProgressDialog mprogress;

    // Using Realtime Database and Storage
    DatabaseReference mdatabaseref;
    StorageReference mstorageref;
    FirebaseUser muser;
    FirebaseAuth mauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        images = findViewById(R.id.imageshow);
        galary = findViewById(R.id.galaryButton);
        close = findViewById(R.id.cancel);
        notification = findViewById(R.id.notification);

        productname = findViewById(R.id.product_name_et);
        productquantity = findViewById(R.id.product_quantity_et);
        productprice = findViewById(R.id.price_et);
        addressline1 = findViewById(R.id.address_line_1);
        addressline2 = findViewById(R.id.address_line_2);
        hometown = findViewById(R.id.hometown_et);
        state = findViewById(R.id.state_et);
        mobileno = findViewById(R.id.mobileno_et);
        mprogress = new ProgressDialog(this);


        submit = findViewById(R.id.submit);

        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("All_Product_Detail");
        mstorageref = FirebaseStorage.getInstance().getReference();
        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddProductDetails.this, NotificationActivity.class));
                finish();
            }
        });

        galary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(AddProductDetails.this);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                images.setImageURI(resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void UploadImage() {



        final String pro_name = productname.getText().toString().trim();
        final String pro_quatity = productquantity.getText().toString().trim();
        final String pro_price = productprice.getText().toString().trim();
        final String address1 = addressline1.getText().toString().trim();
        final String address2 = addressline2.getText().toString().trim();
        final String town = hometown.getText().toString().trim();
        final String states = state.getText().toString().trim();
        final String mobile = mobileno.getText().toString().trim();

        if (validate()) {
            mprogress.setMessage("Data Uploading...");
            mprogress.show();
            if (resultUri != null) {

            final StorageReference FilePath = mstorageref.child("Product_Images").child(resultUri.getLastPathSegment());

            FilePath.putFile(resultUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            FilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    DatabaseReference newpost = mdatabaseref.push();

                                    Map<String, String> dataupload = new HashMap<>();
                                    dataupload.put("Product_name", pro_name);
                                    dataupload.put("Product_Quantity", pro_quatity);
                                    dataupload.put("Product_price", pro_price);
                                    dataupload.put("Address1", address1);
                                    dataupload.put("Address2", address2);
                                    dataupload.put("Hometown", town);
                                    dataupload.put("State", states);
                                    dataupload.put("Mobile_no", mobile);
                                    dataupload.put("Product_images", uri.toString());
                                    dataupload.put("User_ID", muser.getUid());

                                    newpost.setValue(dataupload);
                                    mprogress.dismiss();
                                    startActivity(new Intent(AddProductDetails.this, UplaodDetailActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddProductDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
            } else{
                mprogress.dismiss();
                Toast.makeText(this, "Please Select Image", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean validate() {
        Boolean result = false;

        String ProductName = productname.getText().toString().trim();
        String ProductQuantity = productquantity.getText().toString().trim();
        String ProductPrice = productprice.getText().toString().trim();
        String Address1 = addressline1.getText().toString().trim();
        String Address2 = addressline2.getText().toString().trim();
        String HomeTown = hometown.getText().toString().trim();
        String State = state.getText().toString().trim();
        String MobileNo = mobileno.getText().toString().trim();

        if (ProductName.length() == 0){
            Toast.makeText(this,"Please Enter Product Name", Toast.LENGTH_SHORT).show();
        }
        else if (ProductQuantity.length() == 0 ){
            Toast.makeText(this,"Please Enter Product Quantity", Toast.LENGTH_SHORT).show();
        }
        else if (ProductPrice.length() == 0 ){
            Toast.makeText(this,"Please Enter Product Price", Toast.LENGTH_SHORT).show();
        }
        else if (Address1.length() == 0 ){
            Toast.makeText(this,"Please Fill the Home No", Toast.LENGTH_SHORT).show();
        }
        else if (Address2.length() == 0 ){
            Toast.makeText(this,"Please Enter Near By Address", Toast.LENGTH_SHORT).show();
        }
        else if (HomeTown.length() == 0 ){
            Toast.makeText(this,"Please Enter HomeTown Address", Toast.LENGTH_SHORT).show();
        }
        else if (State.length() == 0 ){
            Toast.makeText(this,"Please Enter State", Toast.LENGTH_SHORT).show();
        }
        else if (MobileNo.length() == 0){
            Toast.makeText(this,"Please Enter Mobile No",Toast.LENGTH_LONG).show();
        }
        else{
            result = true;
        }
        return result;

    }


}

