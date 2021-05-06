package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.jaykisaan.Data.AddProduct_RecycleAdapter;
import com.example.jaykisaan.Model.AddProduct;
import com.example.jaykisaan.R;
import com.example.jaykisaan.Data.Categories_RecyclerViewAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FarSeedWholeActivity extends AppCompatActivity {

    private static final String TAG = "FarSeedActivity";

    private Button verify_email_button;
    private TextView verify_email_tv,seeall;
    RelativeLayout verifyemailrelative;
    FirebaseAuth mAuth;
    ImageView bell_icon;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar,progress_bar_image;
    ImageSlider imageSlider;
    Button videobutton1;
    Button videobutton2;

    Context context;
    RelativeLayout recycler_relative;
    private RecyclerView recyclerView;

    private ArrayList<AddProduct> addProducts;

    private AddProduct_RecycleAdapter cropsProduct_recycleAdapter;


    DatabaseReference mdatabaseref;
    StorageReference mstorageref;

    // Data Connect Using Array
    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farseedwhole);

        verify_email_tv = findViewById(R.id.verifyemailtv);
        verify_email_button = findViewById(R.id.verifyemailbutton);
        verifyemailrelative = findViewById(R.id.verifyemailrelative);
        bell_icon = findViewById(R.id.bell_icon);
        progressBar = findViewById(R.id.progress_bar);
        progress_bar_image = findViewById(R.id.progress_bar_image);
        imageSlider = findViewById(R.id.Image_Slider);
        seeall = findViewById(R.id.seeallproduct);

        // Video button
        videobutton1 = findViewById(R.id.technical);
        videobutton2 = findViewById(R.id.croping);


        // Recycler Seter
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recycler_relative = findViewById(R.id.recycler_relative);

        // Bottom Navigation Manage here..
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        // News and Adds for ImageSlider
        progress_bar_image.setVisibility(View.VISIBLE);
        AddNewsandAdds();

        videobutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarSeedWholeActivity.this,TechnicalVideoSlide.class));
                finish();
            }
        });

        videobutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarSeedWholeActivity.this,CropingVideoSlide.class));
                finish();
            }
        });

        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarSeedWholeActivity.this,TogetherAllProducts.class));
                finish();
            }
        });

        bell_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarSeedWholeActivity.this,NotificationActivity.class));
                finish();
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.search:
                        Intent i = new Intent(getApplicationContext(),SearchActivity.class);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.sell:
                        Intent ii = new Intent(getApplicationContext(),SellActivity.class);
                        startActivity(ii);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.myads:
                        Intent iii = new Intent(getApplicationContext(),MyAdsActivity.class);
                        startActivity(iii);
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.profile:
                        Intent iv= new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(iv);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });

        // Email Verification Send from Here...
        mAuth = FirebaseAuth.getInstance();
        getCategories();
        VerifyEmailID();

        // Product Set Layout
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        progressBar.setVisibility(View.VISIBLE);

        mdatabaseref = FirebaseDatabase.getInstance().getReference();
        mstorageref = FirebaseStorage.getInstance().getReference();

        addProducts = new ArrayList<>();

        product();
        progressBar.setVisibility(View.GONE);
        recycler_relative.setVisibility(View.VISIBLE);
    }


    private void product() {
        final Query query = mdatabaseref.child("All_Product_Detail");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AddProduct product = new AddProduct();
                    product.setProduct_image(dataSnapshot.child("Product_images").getValue().toString());
                    product.setProduct_name(dataSnapshot.child("Product_name").getValue().toString());
                    product.setProduct_quantity(dataSnapshot.child("Product_Quantity").getValue().toString());
                    product.setProduct_price(dataSnapshot.child("Product_price").getValue().toString());
                    product.setHometown(dataSnapshot.child("Hometown").getValue().toString());

                    addProducts.add(product);
                }
                GridLayoutManager manager = new GridLayoutManager(context,2);
                recyclerView.setLayoutManager(manager);
                cropsProduct_recycleAdapter = new AddProduct_RecycleAdapter(context,addProducts);
                recyclerView.setAdapter(cropsProduct_recycleAdapter);
                cropsProduct_recycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // News and Adds SliderModel set
    private void AddNewsandAdds() {
        final List<SlideModel> remoteimages = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("BannerNews")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()){
                            remoteimages.add(new SlideModel(data.child("url").getValue().toString(),data.child("title").getValue().toString(), ScaleTypes.FIT));
                        }
                        imageSlider.setImageList(remoteimages,ScaleTypes.FIT);

                        progress_bar_image.setVisibility(View.GONE);

                        imageSlider.setItemClickListener(new ItemClickListener() {
                            @Override
                            public void onItemSelected(int i) {
                                Intent intent = new Intent(FarSeedWholeActivity.this,NewsActivity.class);
                                intent.putExtra("imageurl",remoteimages.get(i).getImageUrl().toString());
                                intent.putExtra("title",remoteimages.get(i).getTitle().toString());
                                startActivity(intent);
                                finish();
                            }
                        });
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    // Email Verification and send
    private void VerifyEmailID() {
        final FirebaseUser verifybutton = mAuth.getCurrentUser();
        if (!verifybutton.isEmailVerified()){
            verify_email_tv.setVisibility(View.VISIBLE);
            verify_email_button.setVisibility(View.VISIBLE);

            verify_email_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    verifybutton.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(FarSeedWholeActivity.this,"Varification Email has been send",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Email not send !","===>"+ e.getMessage());
                        }
                    });
                }
            });
        }
    }



    // Set Categories
    private void getCategories(){
        Log.e(TAG,"initImageBitmaps: preparing bitmaps");
        progressBar.setVisibility(View.VISIBLE);

        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/jaykisaan-98133.appspot.com/o/Categories_List%2Fcropsicon.png?alt=media&token=b6cdd58d-21f1-44ca-a884-e276a4410c42");
        mName.add("Crops");

        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/jaykisaan-98133.appspot.com/o/Categories_List%2Fvegetableicon.png?alt=media&token=9bea89c1-c8dc-4c63-8581-b5d020711459");
        mName.add("Vegetables");

        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/jaykisaan-98133.appspot.com/o/Categories_List%2Ffruitsicon.png?alt=media&token=1b3a792b-5bd3-43c6-abae-9b747adc847e");
        mName.add("Fruits");

        mImageUrls.add("https://firebasestorage.googleapis.com/v0/b/jaykisaan-98133.appspot.com/o/Categories_List%2Fseedsicon.png?alt=media&token=f511351a-f38a-45d7-ae51-951882eff9a1");
        mName.add("Seeds");

        initRecyclerView();
        progressBar.setVisibility(View.GONE);
    }

    private void initRecyclerView(){
        Log.e(TAG,"initRecyclerView: init Recycler View");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView = findViewById(R.id.categories_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        Categories_RecyclerViewAdapter adapter = new Categories_RecyclerViewAdapter(this, mName, mImageUrls);
        recyclerView.setAdapter(adapter);
    }
}