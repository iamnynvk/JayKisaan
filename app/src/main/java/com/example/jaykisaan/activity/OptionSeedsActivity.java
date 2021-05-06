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

public class OptionSeedsActivity extends AppCompatActivity {

    ListView a_listView;
    ImageView a_back;
    ImageView a_bellicon;

        String a_title[] = {"Rice Seeds | ચોખાના બીજ","Cumin Seeds | જીરું બીજ","Wheat Seeds | ઘઉં બીજ","Barley Seeds | જવ બીજદાણા",
                "Sorghum Seeds | જુવારના દાણા", "Cashew Seeds | કાજુના દાણા","Cotton Seeds | કપાસિયા બીજ","Bean Seeds | બીન બીજ",
                "Corn Seeds | મકાઈ બીજ","Parsley Seeds | અજમા બીજ","Sunflower Seeds | સૂર્યમુખી બીજ"};

    String a_description[] = {"Rice is the seed of the grass species Oryza glaberrima",
            "Cumin is a spice made from the seeds of the Cuminum cyminum plant",
            "Wheat (species of Triticum) is a cereal grain",
            "Grown in a variety of environments, barley is the fourth largest grain crop globally",
            "A line graph is a type of chart used to show information that changes over time",
            "Cashew is used for stomach and intestinal ailments",
            "Cotton is a natural fibre that grows on a plant",
            "A bean is the seed of one of several genera",
            "Corn seeds are called kernels. One ear of corn averages 800 kernels in 16 rows",
            "Parsley is a leafy herb that is entirely edible from root",
            "A sunflower turns its face to follow the sun throughout the day"
    };

    int a_image[] = {R.drawable.bhat_bij,R.drawable.cuminseeds, R.drawable.ghau_bij,R.drawable.jav_bij,
            R.drawable.juvar_bij,R.drawable.kaju_bij,R.drawable.kapasiya_bij,R.drawable.kathol_bij,
            R.drawable.makai_bij,R.drawable.parsley, R.drawable.suryamukhi_bij};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_seeds);

        a_listView = findViewById(R.id.list_view_seeds);
        a_back = findViewById(R.id.exit);
        a_bellicon = findViewById(R.id.notification);

        ProductSeedsAdapter seedsAdapter = new ProductSeedsAdapter(this,a_title,a_description,a_image);
        a_listView.setAdapter(seedsAdapter);

        a_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AddProductDetails.class);
                startActivity(intent);
                finish();
            }
        });

        a_bellicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NotificationActivity.class));
                finish();
            }
        });

        a_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionSeedsActivity.this,FarSeedWholeActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    class ProductSeedsAdapter extends ArrayAdapter<String> {

        Context context;
        String b_title[];
        String b_description[];
        int b_image[];

        ProductSeedsAdapter (Context c,String title[],String description[],int image[]){
            super(c, R.layout.crops_list,R.id.main_title,title);
            this.context = c;
            this.b_title = title;
            this.b_description = description;
            this.b_image = image;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View seeds_detail = inflater.inflate(R.layout.crops_list,parent,false);
            ImageView imageView = seeds_detail.findViewById(R.id.image);
            TextView title = seeds_detail.findViewById(R.id.main_title);
            TextView description = seeds_detail.findViewById(R.id.description_title);

            imageView.setImageResource(b_image[position]);
            title.setText(b_title[position]);
            description.setText(b_description[position]);

            return seeds_detail;
        }
    }
}