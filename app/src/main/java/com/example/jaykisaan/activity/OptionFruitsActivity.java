package com.example.jaykisaan.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jaykisaan.R;

import java.util.ArrayList;

public class OptionFruitsActivity extends AppCompatActivity {

    ListView listView;
    ImageView back;
    ImageView bellicon;

    String a_title[] = {"Apple | સફરજન", "Banana | કેળા", "Blueberry | બ્લુબેરી", "Cherrie | ચેરી", "Coconuts | નાળિયેર", "Graps | ગ્રેપ્સ", "Orange | નારંગી", "Pear | પિઅર", "Pineapple | અનેનાસ", "Strawberry | સ્ટ્રોબેરી", "Watermalon | તરબૂચ"};

    String a_description[] = {"Apple is one of the most famous fruits in the world",
            "The leaves of the banana tree are very long. Banana grows in different sizes",
            "Blueberries have a sweet taste, with a little acidic hint",
            "Cherry is a fruit that grows on a tree or a bush",
            "Coconut palm trees grow in tropical areas",
            "A line graph is a type of chart used to show information that changes over time.",
            "Oranges are round orange-coloured fruit that grow on a tree 10 metres high",
            "A pear is an edible fruit. Pears do not ripen well on trees",
            "sweet, juicy fruits. They grow on a plant with the scientific",
            "red, heart-shaped fruits. They are eaten fresh, often with cream.",
            "Watermelon is a large fruit spherical in shape"};

    int a_image[] = {R.drawable.apple, R.drawable.banana, R.drawable.blueberry, R.drawable.cherries, R.drawable.coconut,
            R.drawable.grapes, R.drawable.orange, R.drawable.pear, R.drawable.pineapple, R.drawable.strawberry, R.drawable.watermelon};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_fruits);

        listView = findViewById(R.id.list_view_fruits);
        back = findViewById(R.id.close);
        bellicon = findViewById(R.id.notification);

        ProductFruitsAdapter detailAdapter = new ProductFruitsAdapter(this, a_title, a_description, a_image);

        listView.setAdapter(detailAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AddProductDetails.class);
                startActivity(intent);
                finish();
            }
        });

        bellicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OptionFruitsActivity.this, NotificationActivity.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionFruitsActivity.this,FarSeedWholeActivity.class));
                finish();
            }
        });
    }

    public void onBackPressed() {
            super.onBackPressed();
        finish();
    }

    class ProductFruitsAdapter extends ArrayAdapter<String> {

        Context context;
        String b_title[];
        String b_description[];
        int b_image[];

        ProductFruitsAdapter(Context c, String title[], String description[], int image[]) {
            super(c, R.layout.crops_list, R.id.main_title, title);
            this.context = c;
            this.b_title = title;
            this.b_description = description;
            this.b_image = image;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View fruits_detail = inflater.inflate(R.layout.crops_list, parent, false);
            ImageView image = fruits_detail.findViewById(R.id.image);
            TextView title = fruits_detail.findViewById(R.id.main_title);
            TextView description = fruits_detail.findViewById(R.id.description_title);

            image.setImageResource(b_image[position]);
            title.setText(b_title[position]);
            description.setText(b_description[position]);

            return fruits_detail;
        }
    }
}