package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jaykisaan.Data.AddProduct_RecycleAdapter;
import com.example.jaykisaan.Model.AddProduct;
import com.example.jaykisaan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
public class MyAdsActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Context context;
    RelativeLayout recycler_relative;
    private RecyclerView recyclerView_allProduct;

    private ArrayList<AddProduct> products;
    private AddProduct_RecycleAdapter product_recycleAdapter;

    DatabaseReference mdatabaseref;
    StorageReference mstorageref;
    FirebaseUser muser;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);

        // Bottom Navigation Manage here..
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.myads);

        recyclerView_allProduct = findViewById(R.id.recycle_view_for_my_product);
        recycler_relative = findViewById(R.id.relative_for_adds);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_allProduct.setLayoutManager(layoutManager);
        recyclerView_allProduct.setHasFixedSize(true);

        mdatabaseref = FirebaseDatabase.getInstance().getReference();
        mstorageref = FirebaseStorage.getInstance().getReference();
        mauth = FirebaseAuth.getInstance();
        muser = mauth.getCurrentUser();

        products = new ArrayList<>();
        product();
        recycler_relative.setVisibility(View.VISIBLE);



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
                        onBackPressed();
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

    private void product() {
        String User_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference ref = FirebaseFirestore.getInstance().collection("User").document(User_ID);

            ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    final Query query = mdatabaseref.child("All_Product_Detail");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                AddProduct product = new AddProduct();
                                product.equals(snapshot.child("User_ID").getValue().toString());

                                product.setProduct_image(dataSnapshot.child("Product_images").getValue().toString());
                                product.setProduct_name(dataSnapshot.child("Product_name").getValue().toString());
                                product.setProduct_quantity(dataSnapshot.child("Product_Quantity").getValue().toString());
                                product.setProduct_price(dataSnapshot.child("Product_price").getValue().toString());
                                product.setHometown(dataSnapshot.child("Hometown").getValue().toString());

                                products.add(product);
                            }
                            GridLayoutManager manager = new GridLayoutManager(context, 2);
                            recyclerView_allProduct.setLayoutManager(manager);
                            product_recycleAdapter = new AddProduct_RecycleAdapter(context, products);
                            recyclerView_allProduct.setAdapter(product_recycleAdapter);
                            product_recycleAdapter.notifyDataSetChanged();
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
        Intent in = new Intent(getApplicationContext(),FarSeedWholeActivity.class);
        startActivity(in);
        overridePendingTransition(0,0);
        finish();
    }
}