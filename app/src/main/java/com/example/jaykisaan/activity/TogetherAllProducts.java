package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.jaykisaan.Data.AddProduct_RecycleAdapter;
import com.example.jaykisaan.Model.AddProduct;
import com.example.jaykisaan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class TogetherAllProducts extends AppCompatActivity {
    Context context;
    RelativeLayout recycler_relative;
    private RecyclerView recyclerView_allProduct;

    private ArrayList<AddProduct> products;
    private AddProduct_RecycleAdapter product_recycleAdapter;

    DatabaseReference mdatabaseref;
    StorageReference mstorageref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_all_products);

        recyclerView_allProduct = findViewById(R.id.recycle_view_for_all_product);
        recycler_relative = findViewById(R.id.recycler_relative);

        // Product Set Layout
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_allProduct.setLayoutManager(layoutManager);
        recyclerView_allProduct.setHasFixedSize(true);

        mdatabaseref = FirebaseDatabase.getInstance().getReference();
        mstorageref = FirebaseStorage.getInstance().getReference();

        products = new ArrayList<>();
        product();
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

                    products.add(product);
                }
                GridLayoutManager manager = new GridLayoutManager(context,2);
                recyclerView_allProduct.setLayoutManager(manager);
                product_recycleAdapter = new AddProduct_RecycleAdapter(context,products);
                recyclerView_allProduct.setAdapter(product_recycleAdapter);
                product_recycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(TogetherAllProducts.this,FarSeedWholeActivity.class));
        finish();
    }
}