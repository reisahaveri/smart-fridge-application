package com.example.smart_fridge_application;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.content.Intent;
import android.content.Context;



public class FetchData extends AsyncTask<String,Void,String> {
    private TextView textView;
    private Context context;

    public FetchData(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }
//    @Override
//    protected String doInBackground(String... params) {
//        String data = "";
//        String dataParsed = "";
//        String singleParsed = "";
//        try {
//            URL url = new URL("https://world.openfoodfacts.org");
////            URL url = new URL(params[0]);
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = httpURLConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line = "";
//            while ((line = bufferedReader.readLine()) != null) {
//                data = data + line;
//            }
//
//            JSONObject JO = new JSONObject(data);
//            singleParsed = "Name: " + JO.get("name") + "\n" +
//                    "Quantity: " + JO.get("quantity") + "\n" +
//                    "Brand: " + JO.get("brand") + "\n";
//
//            dataParsed = dataParsed + singleParsed;
//
//        } catch (MalformedURLException | JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//// } catch (MalformedURLException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
//        return dataParsed;
//    }

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


//    @Override
//    protected void onPostExecute(String result) {
////        textView.setText(result);
//        try{
//
//            JSONArray jsonArray = new JSONArray(result);
//
//            JSONObject jsonObject= jsonArray.getJSONObject(jsonArray);
//            int ID = jsonObject.getInt("stadID");
//            String stadNaam = jsonObject.getString("stadNaam");
//            String postcode = jsonObject.getString("postcode");
//            textView.setText(textView.getText() + "Name: " + stadNaam + " postcode: " + postcode);
//
//
//        } catch (JSONException e) {
//           e.printStackTrace();
//        }
//
//
//    }

    @Override
    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("product_details", result);
        context.startActivity(intent);
    }

}