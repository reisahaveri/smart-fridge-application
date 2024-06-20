package com.example.smart_fridge_application;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProductDetailsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        String details = getIntent().getStringExtra("product_details");
        String imageUrl = getIntent().getStringExtra("image_url");

        Log.d("ProductDetails", "Received details: " + details);
        Log.d("ProductDetails", "Received image URL: " + imageUrl);

        TextView textView = findViewById(R.id.details_text_view);
        ImageView imageView = findViewById(R.id.product_image_view);

        if (details != null) {
            textView.setText(details);
        } else {
            textView.setText("No details available.");
        }

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                    .error(R.drawable.ic_launcher_background) // Error image
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_background); // Default image if no URL
        }
    }
}
