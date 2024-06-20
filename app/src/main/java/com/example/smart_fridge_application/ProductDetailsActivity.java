package com.example.smart_fridge_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProductDetailsActivity extends AppCompatActivity {
    private String productDetails;
    private String productName;
    private String productBrand;
    private String productQuantity;
    private String imageUrl;
    private MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        myDB = new MyDatabaseHelper(this);

        productDetails = getIntent().getStringExtra("product_details");
        productName = getIntent().getStringExtra("product_name");
        productBrand = getIntent().getStringExtra("product_brand");
        productQuantity = getIntent().getStringExtra("product_quantity");
        imageUrl = getIntent().getStringExtra("image_url");

        Log.d("ProductDetails", "Received details: " + productDetails);
        Log.d("ProductDetails", "Received image URL: " + imageUrl);

        TextView textView = findViewById(R.id.details_text_view);
        ImageView imageView = findViewById(R.id.product_image_view);
        Button yesButton = findViewById(R.id.yes_button);
        Button noButton = findViewById(R.id.no_button);

        if (productDetails != null) {
            textView.setText(productDetails);
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

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndAddProduct();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMainActivity();
            }
        });
    }

    private void checkAndAddProduct() {
        if (myDB.isProductExists(productName, productBrand)) {
            new AlertDialog.Builder(this)
                    .setTitle("Product Exists")
                    .setMessage("This product already exists. Do you want to add it again?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addProductToDatabase();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            goBackToMainActivity();
                        }
                    })
                    .show();
        } else {
            addProductToDatabase();
        }
    }

    private void addProductToDatabase() {
        long expiredDate = System.currentTimeMillis(); // Use current timestamp as expired date
        myDB.addProduct(productName, productBrand, expiredDate);

        // Navigate to UpdateActivity with the warning message
        Intent intent = new Intent(ProductDetailsActivity.this, UpdateActivity.class);
        intent.putExtra("id", String.valueOf(myDB.getLastInsertedId())); // Assuming getLastInsertedId method exists in MyDatabaseHelper
        intent.putExtra("title", productName);
        intent.putExtra("brand", productBrand);
        intent.putExtra("expDate", String.valueOf(expiredDate));
        intent.putExtra("image_url", imageUrl);
        intent.putExtra("warning", "Please change the expiration date."); // Pass the warning message
        startActivity(intent);

        finish();
    }

    private void goBackToMainActivity() {
        Intent intent = new Intent(ProductDetailsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
