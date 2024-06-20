package com.example.smart_fridge_application;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchData extends AsyncTask<String, Void, String> {
    private Context context;
    private static final String TAG = "FetchData";
    private String productName;
    private String productBrand;
    private String productQuantity;
    private String imageUrl = "";

    public FetchData(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            // Print the raw JSON response
            Log.d(TAG, "JSON Response: " + result.toString());

            // Parse the JSON response
            JSONObject jsonObject = new JSONObject(result.toString());
            if (jsonObject.has("product")) {
                JSONObject product = jsonObject.getJSONObject("product");
                productName = product.optString("product_name", "N/A");
                productQuantity = product.optString("quantity", "N/A");
                productBrand = product.optString("brands", "N/A");
                imageUrl = product.optString("image_url", ""); // Adjust the key as per the JSON response
                return "Name: " + productName + "\nBrand: " + productBrand + "\nQuantity: " + productQuantity;
            } else {
                return "No product data found for the given barcode.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch data: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("product_details", result);
        intent.putExtra("product_name", productName);
        intent.putExtra("product_brand", productBrand);
        intent.putExtra("product_quantity", productQuantity);
        intent.putExtra("image_url", imageUrl); // Pass the image URL to the next activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if (context instanceof BarcodeScanActivity) {
            ((BarcodeScanActivity) context).finish();
        }
    }
}
