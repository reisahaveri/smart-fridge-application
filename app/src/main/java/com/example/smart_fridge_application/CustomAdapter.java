package com.example.smart_fridge_application;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> product_id, product_title, product_brand, expDate;

    public CustomAdapter(Context context, ArrayList<String> product_id, ArrayList<String> product_title,
                         ArrayList<String> product_brand, ArrayList<String> expDate) {
        this.context = context;
        this.product_id = product_id;
        this.product_title = product_title;
        this.product_brand = product_brand;
        this.expDate = expDate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.product_id_txt.setText(String.valueOf(product_id.get(position)));
        holder.product_title_txt.setText(String.valueOf(product_title.get(position)));
        holder.product_brand_txt.setText(String.valueOf(product_brand.get(position)));
        holder.product_expDate_txt.setText(String.valueOf(expDate.get(position)));

        holder.update_button.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(product_id.get(position)));
            intent.putExtra("title", String.valueOf(product_title.get(position)));
            intent.putExtra("brand", String.valueOf(product_brand.get(position)));
            intent.putExtra("expDate", String.valueOf(expDate.get(position)));
            context.startActivity(intent);
        });

        holder.delete_button.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Product")
                    .setMessage("Are you sure you want to delete this product?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MyDatabaseHelper myDB = new MyDatabaseHelper(context);
                            myDB.deleteOneRow(product_id.get(position));
                            // Refresh the activity
                            ((MainActivity)context).storeDataInArrays();
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return product_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_id_txt, product_title_txt, product_brand_txt, product_expDate_txt;
        ImageButton update_button, delete_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_id_txt = itemView.findViewById(R.id.product_id_txt);
            product_title_txt = itemView.findViewById(R.id.product_title_txt);
            product_brand_txt = itemView.findViewById(R.id.product_brand_txt);
            product_expDate_txt = itemView.findViewById(R.id.product_expDate_txt);
            update_button = itemView.findViewById(R.id.update_button);
            delete_button = itemView.findViewById(R.id.delete_button);
        }
    }
}
