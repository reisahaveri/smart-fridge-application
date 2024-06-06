package com.example.smart_fridge_application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddActivity extends AppCompatActivity {

    ImageView uploadImage;

    EditText title_input, brand_input, expDate_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        uploadImage = findViewById(R.id.uploadImage);
        title_input = findViewById(R.id.title_input);
        brand_input = findViewById(R.id.brand_input);
        expDate_input = findViewById(R.id.expDate_input);
        add_button = findViewById(R.id.add_button);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addProduct(    title_input.getText().toString().trim(),
                        brand_input.getText().toString().trim(),
                        Integer.valueOf(expDate_input.getText().toString().trim()));
            }
        });
    }
}