package com.example.jaykisaan.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jaykisaan.R;
import com.example.jaykisaan.activity.OptionCropsActivity;
import com.example.jaykisaan.activity.OptionFruitsActivity;
import com.example.jaykisaan.activity.OptionSeedsActivity;
import com.example.jaykisaan.activity.OptionVegetablesActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Categories_RecyclerViewAdapter extends RecyclerView.Adapter<Categories_RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "Categories_RecyclerViewAdapter";

    //vars
    private ArrayList<String> mName = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mcontext;
    ProgressBar progressBar;


    public Categories_RecyclerViewAdapter(Context context, ArrayList<String> name, ArrayList<String> imageUrl) {
        this.mName = name;
        this.mImageUrls = imageUrl;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.e(TAG,"onBindViewHolder: Called");
        Glide.with(mcontext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.IconView);

        holder.CropName.setText(mName.get(position));

        holder.IconView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick: Click on Image:"+ mName.get(position));
                /*Toast.makeText(mcontext,mName.get(position),Toast.LENGTH_SHORT).show();*/

                if (position == 0){
                    Intent i = new Intent(mcontext, OptionCropsActivity.class);
                    mcontext.startActivity(i);
                }
                else if (position == 1){
                    Intent i = new Intent(mcontext, OptionVegetablesActivity.class);
                    mcontext.startActivity(i);
                }
                else if (position == 2){
                    Intent i = new Intent(mcontext, OptionFruitsActivity.class);
                    mcontext.startActivity(i);
                }
                else if (position == 3){
                    Intent i = new Intent(mcontext, OptionSeedsActivity.class);
                    mcontext.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView IconView;
        TextView CropName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            IconView = itemView.findViewById(R.id.IconView);
            CropName = itemView.findViewById(R.id.CropName);
        }
    }

}
