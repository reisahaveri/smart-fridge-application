package com.example.smart_fridge_application;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    ImageView uploadImage;
    EditText title_input, brand_input;
    TextView expDate_text;
    Button add_button;
    Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        uploadImage = findViewById(R.id.uploadImage);
        title_input = findViewById(R.id.title_input);
        brand_input = findViewById(R.id.brand_input);
        expDate_text = findViewById(R.id.expDate_text);
        add_button = findViewById(R.id.add_button);

        expDate_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                String title = title_input.getText().toString().trim();
                String brand = brand_input.getText().toString().trim();

                if (title.isEmpty() || brand.isEmpty() || selectedDate == null) {
                    Toast.makeText(AddActivity.this, "Please fill all fields and select a valid expiration date", Toast.LENGTH_SHORT).show();
                    return;
                }

                myDB.addProduct(title, brand, selectedDate.getTimeInMillis());
                Toast.makeText(AddActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity after adding the product
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selected = Calendar.getInstance();
                selected.set(year, month, dayOfMonth);
                if (selected.before(currentDate)) {
                    Toast.makeText(AddActivity.this, "Expiration date cannot be earlier than today", Toast.LENGTH_SHORT).show();
                } else {
                    selectedDate = selected;
                    expDate_text.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}
