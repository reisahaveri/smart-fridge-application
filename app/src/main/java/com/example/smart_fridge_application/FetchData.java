package com.example.smart_fridge_application;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchData extends AsyncTask<String, Void, String> {
    private Context context;

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

            JSONObject jsonObject = new JSONObject(result.toString());
            JSONObject product = jsonObject.getJSONObject("product");
            String name = product.getString("product_name");
            String quantity = product.getString("quantity");
            String brand = product.getString("brands");
            return "Name: " + name + "\nBrand: " + brand + "\nQuantity: " + quantity;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch data: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("product_details", result);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if (context instanceof BarcodeScanActivity) {
            ((BarcodeScanActivity) context).finish();
        }
    }

}
