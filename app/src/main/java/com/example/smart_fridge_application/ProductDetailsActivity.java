package com.example.smart_fridge_application;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ProductDetailsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        String details = getIntent().getStringExtra("product_details");
        Log.d("ProductDetails", "Received details: " + details);

        TextView textView = findViewById(R.id.details_text_view);
        if (details != null) {
            textView.setText(details);
        } else {
            textView.setText("No details available.");
        }
    }
}
