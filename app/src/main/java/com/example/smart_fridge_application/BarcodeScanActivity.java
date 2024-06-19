package com.example.smart_fridge_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class BarcodeScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);

        IntentIntegrator intentIntegrator = new IntentIntegrator(BarcodeScanActivity.this);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setPrompt("Scan a barcode");
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contents = intentResult.getContents();
            if (contents != null) {
                // Handle the scanned barcode
                // Possibly start a new activity or fetch product info
                // Example:
                Toast.makeText(this, "Scanned: " + contents, Toast.LENGTH_LONG).show();
                // You can send the result back to MainActivity or handle it here
                // For now, we'll just finish the activity
                finish();
            } else {
                Toast.makeText(this, "No barcode detected, please try again.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
