package com.example.smart_fridge_application;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, brand_input;
    TextView expDate_input, warning_text_view;
    ImageButton calendar_button2;
    Button update_button;

    String id, title, brand, imageUrl;
    long expDate;
    Calendar calendar;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        brand_input = findViewById(R.id.brand_input2);
        expDate_input = findViewById(R.id.expDate_input2);
        warning_text_view = findViewById(R.id.warning_text_view);
        calendar_button2 = findViewById(R.id.calendar_button2);
        update_button = findViewById(R.id.update_button);

        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        calendar = Calendar.getInstance();

        // First we call this
        getAndSetIntentData();

        // Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
        }

        calendar_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UpdateActivity.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString().trim();
                brand = brand_input.getText().toString().trim();
                myDB.updateData(id, title, brand, expDate, imageUrl);

                // Redirect to MainActivity after updating
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("brand") && getIntent().hasExtra("expDate") && getIntent().hasExtra("image_url")) {
            // Getting Data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            brand = getIntent().getStringExtra("brand");
            String expDateString = getIntent().getStringExtra("expDate");

            // Convert the expiration date string to long
            try {
                expDate = Long.parseLong(expDateString);
                calendar.setTimeInMillis(expDate); // Set calendar time to expDate
                expDate_input.setText(sdf.format(calendar.getTime())); // Format and set the expiration date
            } catch (NumberFormatException e) {
                Log.e("UpdateActivity", "Invalid date format", e);
            }

            // Setting Intent Data
            title_input.setText(title);
            brand_input.setText(brand);
            Log.d("UpdateActivity", title + " " + brand + " " + expDate);

            // Show warning message if it exists
            if (getIntent().hasExtra("warning")) {
                String warning = getIntent().getStringExtra("warning");
                warning_text_view.setText(warning);
                warning_text_view.setVisibility(View.VISIBLE);
            } else {
                warning_text_view.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        expDate_input.setText(sdf.format(calendar.getTime()));
        expDate = calendar.getTimeInMillis();
    }
}
