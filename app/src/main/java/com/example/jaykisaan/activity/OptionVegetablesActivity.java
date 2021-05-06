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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jaykisaan.R;

public class OptionVegetablesActivity extends AppCompatActivity {

    ListView listView;
    ImageView back;
    ImageView bellicon;

    String main_title[] = {"Tomatoes | ટામેટા", "Radish | મૂળો", "Bit | બીટ", "Chili | મરચા", "Lemon | લીંબુ", "Garlic | લસણ",
            "Coriander | કોથમીર", "Cabbage | કોબી", "Bitter-gourd | કરેલા", "Carrots | ગાજર", "Onion | ડુંગળી", "Potatoes | બટાકા", "Ginger | આદુ"};

    String description_title[] = {"Tomato seeds are dispersed by being eaten by animals",
            "A radish is an edible root vegetable of the Brassicaceae family. People eat radishes all over the world",
            "Bit is wenderful items because of increase our body's bloods",
            "Chili's Grill & Bar is an American casual dining restaurant chain.",
            "The lemon is a small tree (Citrus limon) that is green even in the winter",
            "Garlic is rich in anti-inflammatory, antibiotic and anti-carcinogenic properties",
            "Coriander is an herb that's commonly used to flavor international dishes.",
            "Cabbage consumption varies widely around the world",
            "Bitter gourd is a cure for boils, rashes, fungal infections and ring-worm",
            "Carrot is a root vegetable. it is orange in colour.",
            "The onion is a plant bulb made up of crisp, fleshy layers.",
            "It is one of the most famous vegetables in the world",
            "Ginger originated from Maritime Southeast Asia. It is a true cultigen and does not exist in its wild state."};

    int image[] = {R.drawable.tomatos, R.drawable.mula, R.drawable.beat, R.drawable.marcha, R.drawable.limbu, R.drawable.lasan,
            R.drawable.kothamri, R.drawable.kobi, R.drawable.karela, R.drawable.gajar, R.drawable.dungli, R.drawable.batata, R.drawable.aadu};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_vegetables);

        listView = findViewById(R.id.Vegetable_list_view);
        back = findViewById(R.id.cancel);
        bellicon = findViewById(R.id.notification);

        ProductVegetableAdapter vegetableAdapter = new ProductVegetableAdapter(this,main_title,description_title,image);

        listView.setAdapter(vegetableAdapter);


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
                Intent i = new Intent(OptionVegetablesActivity.this, NotificationActivity.class);
                startActivity(i);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OptionVegetablesActivity.this,FarSeedWholeActivity.class));
                finish();
            }
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    class ProductVegetableAdapter extends ArrayAdapter<String> {

        Context context;
        String bmain_title[];
        String bdescription_title[];
        int bimage[];

        ProductVegetableAdapter (Context c,String title[],String description[],int image[]){
            super(c, R.layout.crops_list,R.id.main_title,title);
            this.context = c;
            this.bmain_title = title;
            this.bdescription_title = description;
            this.bimage = image;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

            LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View vegetable_detail = inflater.inflate(R.layout.crops_list,parent,false);
            ImageView image = vegetable_detail.findViewById(R.id.image);
            TextView title = vegetable_detail.findViewById(R.id.main_title);
            TextView description = vegetable_detail.findViewById(R.id.description_title);

            image.setImageResource(bimage[position]);
            title.setText(bmain_title[position]);
            description.setText(bdescription_title[position]);

            return vegetable_detail;
        }
    }
}