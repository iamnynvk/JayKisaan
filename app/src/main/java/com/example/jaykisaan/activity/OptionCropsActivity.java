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

public class OptionCropsActivity extends AppCompatActivity {

    ListView listView;
    ImageView back;
    ImageView bellicon;

    String amain_title[] = {"Cotton | કપાસ","Cumin seeds | જીરું","Groundnut | મગફળી","Wheat | ઘઉં","Millet | બાજરી","Maize | મકાઈ","Parsley | અજમા","Sorghum | જુવાર"};

    String adescription_title[] = {"Cotton is a soft, fluffy staple fiber that grows in a boll","Cumin is the dried seed of the herb Cuminum cyminum",
    "Each plant produces between 25 and 50 peanuts","Wheat is winter product","Millet are a group of highly variable small-seeded grasses",
    "Maize is one of the most important cereals of the world","Keep the soil evenly moist until the seeds germinate",
    "Sorghum is a nutrient-packed grain that you can use in many ways"};

    int aimage[] = {R.drawable.cotton,R.drawable.cuminseeds,R.drawable.groundnut,R.drawable.wheat,R.drawable.millet,R.drawable.corn,R.drawable.parsley,R.drawable.sorghum};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_crops);

        listView = findViewById(R.id.list_view);
        back = findViewById(R.id.cancel);
        bellicon = findViewById(R.id.notification);

        ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter(this,amain_title,adescription_title,aimage);

        listView.setAdapter(productDetailAdapter);


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
                Intent i = new Intent(OptionCropsActivity.this,NotificationActivity.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionCropsActivity.this,FarSeedWholeActivity.class));
                finish();
            }
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    class ProductDetailAdapter extends ArrayAdapter<String> {

        Context context;
        String bmain_title[];
        String bdescription_title[];
        int bimage[];

        ProductDetailAdapter (Context c,String title[],String description[],int image[]){
            super(c, R.layout.crops_list,R.id.main_title,title);
            this.context = c;
            this.bmain_title = title;
            this.bdescription_title = description;
            this.bimage = image;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View crops_detail = inflater.inflate(R.layout.crops_list,parent,false);
            ImageView image = crops_detail.findViewById(R.id.image);
            TextView title = crops_detail.findViewById(R.id.main_title);
            TextView description = crops_detail.findViewById(R.id.description_title);

            image.setImageResource(aimage[position]);
            title.setText(amain_title[position]);
            description.setText(adescription_title[position]);

            return crops_detail;
        }
    }
}