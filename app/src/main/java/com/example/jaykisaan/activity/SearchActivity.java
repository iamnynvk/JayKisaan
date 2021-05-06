package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jaykisaan.Data.AddProduct_RecycleAdapter;
import com.example.jaykisaan.Model.AddProduct;
import com.example.jaykisaan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    EditText searchText;
    ImageView searchbutton;
    ImageView cancel;
    ImageView bellicon;

    ArrayList<AddProduct> addProducts;
    Context context;

    RecyclerView recyclerView;
    private AddProduct_RecycleAdapter cropsProduct_recycleAdapter;

    private DatabaseReference mUserDatabase;
    RecyclerView search_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Bottom Navigation Manage here..
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);


        mUserDatabase = FirebaseDatabase.getInstance().getReference("All_Product_Detail");

        searchText = (EditText) findViewById(R.id.search_et);
        searchbutton = findViewById(R.id.search_button);
        cancel = findViewById(R.id.cancel);
        bellicon = findViewById(R.id.notification);

        search_user = findViewById(R.id.recyclerView_search);
        search_user.setHasFixedSize(true);
        search_user.setLayoutManager(new LinearLayoutManager(this));


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this,FarSeedWholeActivity.class));
                finish();
            }
        });

        bellicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchActivity.this,NotificationActivity.class));
                finish();
            }
        });

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_Text = searchText.getText().toString();

                FirebaseSearchData(search_Text);

            }
        });

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
                        onBackPressed();
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
    }

    private void FirebaseSearchData(String search_Text) {
        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query query = mUserDatabase.orderByChild("Product_name").startAt(search_Text).endAt(search_Text + "\uf8ff");

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

    @Override
    public void onBackPressed() {
        Intent in = new Intent(getApplicationContext(),FarSeedWholeActivity.class);
        startActivity(in);
        overridePendingTransition(0,0);
        finish();
    }
}