package com.example.jaykisaan.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaykisaan.Model.AddProduct;
import com.example.jaykisaan.R;
import com.example.jaykisaan.activity.ProductDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddProduct_RecycleAdapter extends RecyclerView.Adapter<AddProduct_RecycleAdapter.ViewHolder> {
    private static final String TAG = "AddProduct_RecycleAdapter";

    Context mcontext;
    List<AddProduct> addProducts;

    public AddProduct_RecycleAdapter(Context mcontext, List<AddProduct> addProducts) {
        this.mcontext = mcontext;
        this.addProducts = addProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_cardview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ProductName.setText(addProducts.get(position).getProduct_name());
        holder.ProductQuantity.setText(addProducts.get(position).getProduct_quantity());
        holder.ProductPrice.setText(addProducts.get(position).getProduct_price());
        holder.Address1.setText(addProducts.get(position).getAddress1());
        holder.Address2.setText(addProducts.get(position).getAddress2());
        holder.city.setText(addProducts.get(position).getHometown());
        holder.state.setText(addProducts.get(position).getState());
        holder.mobileno.setText(addProducts.get(position).getMobileno());

        Picasso.get().load(addProducts.get(position).getProduct_image())
                .into(holder.ProductImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progress_ho.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progress_ho.setVisibility(View.VISIBLE);
                    }
                });

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ProductDetail.class);

                i.putExtra("ProductImage",addProducts.get(position).getProduct_image());
                i.putExtra("ProductName",addProducts.get(position).getProduct_name());
                i.putExtra("ProductQuantity",addProducts.get(position).getProduct_quantity());
                i.putExtra("ProductPrice",addProducts.get(position).getProduct_price());
                i.putExtra("Address1",addProducts.get(position).getAddress1());
                i.putExtra("Address2",addProducts.get(position).getAddress2());
                i.putExtra("City",addProducts.get(position).getHometown());
                i.putExtra("State",addProducts.get(position).getState());
                i.putExtra("Mobileno",addProducts.get(position).getMobileno());

                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        ImageView ProductImage;
        TextView ProductName;
        TextView ProductQuantity;
        TextView ProductPrice;
        TextView Address1;
        TextView Address2;
        TextView city;
        TextView mobileno;
        TextView state;

        ProgressBar progress_ho;
        RelativeLayout mainrelative;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardview = (CardView) itemView.findViewById(R.id.product_cardView);
            ProductImage = (ImageView) itemView.findViewById(R.id.product_image);
            ProductName = (TextView) itemView.findViewById(R.id.product_name);
            ProductQuantity = (TextView) itemView.findViewById(R.id.product_quantity);
            ProductPrice = (TextView) itemView.findViewById(R.id.product_price);
            Address1 = (TextView) itemView.findViewById(R.id.address1);
            Address2 = (TextView) itemView.findViewById(R.id.address2);
            city = (TextView) itemView.findViewById(R.id.city);
            mobileno = (TextView) itemView.findViewById(R.id.mobileNO);
            state = (TextView) itemView.findViewById(R.id.statesho);
            progress_ho = (ProgressBar) itemView.findViewById(R.id.progressBarofcard);
            mainrelative = (RelativeLayout) itemView.findViewById(R.id.main_relative_);
        }
    }
}
