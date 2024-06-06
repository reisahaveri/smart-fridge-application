package com.example.smart_fridge_application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList product_id, product_title, product_brand,product_expDate;

    CustomAdapter(Activity activity, Context context, ArrayList product_id, ArrayList product_title, ArrayList product_brand,
                  ArrayList product_expDate){
        this.activity = activity;
        this.context = context;
        this.product_id = product_id;
        this.product_title = product_title;
        this.product_brand = product_brand;
        this.product_expDate = product_expDate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.product_id_txt.setText(String.valueOf(product_id.get(position)));
        holder.product_title_txt.setText(String.valueOf(product_title.get(position)));
        holder.product_brand_txt.setText(String.valueOf(product_brand.get(position)));
        holder.product_expDate_txt.setText(String.valueOf(product_expDate.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(product_id.get(position)));
                intent.putExtra("title", String.valueOf(product_title.get(position)));
                intent.putExtra("brand", String.valueOf(product_brand.get(position)));
                intent.putExtra("expDate", String.valueOf(product_expDate.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView product_id_txt, product_title_txt, product_brand_txt, product_expDate_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id_txt = itemView.findViewById(R.id.product_id_txt);
            product_title_txt = itemView.findViewById(R.id.product_title_txt);
            product_brand_txt = itemView.findViewById(R.id.product_brand_txt);
            product_expDate_txt = itemView.findViewById(R.id.product_expDate_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}