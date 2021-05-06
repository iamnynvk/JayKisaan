package com.example.jaykisaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaykisaan.R;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    String ProductImage;
    String ProductName,ProductQuantity,ProductPrice;
    String Address1,Address2,Hometown,state,Mobileno;

    ImageView Product_IMG;
    TextView Product_NAME,Heading_product_name,Product_QUANT,Product_PRICE;
    TextView Address_1,Address_2,HomeTown,STATE,Mobile_NO;
    Button callbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent i = getIntent();
        ProductImage = i.getStringExtra("ProductImage");
        ProductName = i.getStringExtra("ProductName");
        ProductQuantity = i.getStringExtra("ProductQuantity");
        ProductPrice = i.getStringExtra("ProductPrice");
        Address1 = i.getStringExtra("Address1");
        Address2 = i.getStringExtra("Address2");
        Hometown = i.getStringExtra("City");
        state = i.getStringExtra("State");
        Mobileno = i.getStringExtra("Mobileno");

        setContentView(R.layout.activity_product_detail);


        Product_IMG = (ImageView) findViewById(R.id.product_images);
        Heading_product_name = (TextView) findViewById(R.id.product_name_heading);
        Product_NAME = (TextView) findViewById(R.id.product_name_);
        Product_QUANT = (TextView) findViewById(R.id.product_quant);
        Product_PRICE = (TextView) findViewById(R.id.product_price_new);
        Address_1 = (TextView) findViewById(R.id.addressline_1_new);
        Address_2 = (TextView) findViewById(R.id.addressline_2_new);
        HomeTown = (TextView) findViewById(R.id.hometown);
        STATE = (TextView) findViewById(R.id.states);
        Mobile_NO = (TextView) findViewById(R.id.mobileno);
        callbutton = (Button) findViewById(R.id.call_button);


        Picasso.get().load(ProductImage).into(Product_IMG);
        Heading_product_name.setText(ProductName);
        Product_NAME.setText(ProductName);
        Product_QUANT.setText(ProductQuantity);
        Product_PRICE.setText(ProductPrice);
        Address_1.setText(Address1);
        Address_2.setText(Address2);
        HomeTown.setText(Hometown);
        STATE.setText(state);
        Mobile_NO.setText(Mobileno);
    }
}