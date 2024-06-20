package com.example.smart_fridge_application;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotificationsAdapter notificationsAdapter;
    ArrayList<NotificationItem> notificationsList;
    MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerView = findViewById(R.id.recyclerView);
        myDB = new MyDatabaseHelper(this);

        notificationsList = getNotifications();

        notificationsAdapter = new NotificationsAdapter(this, notificationsList);
        recyclerView.setAdapter(notificationsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private ArrayList<NotificationItem> getNotifications() {
        // Retrieve the notifications from the database
        ArrayList<NotificationItem> notifications = new ArrayList<>();
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String productName = cursor.getString(1);
                long expiryDateMillis = cursor.getLong(3);
                long currentTimeMillis = System.currentTimeMillis();
                long daysLeft = (expiryDateMillis - currentTimeMillis) / (1000 * 60 * 60 * 24);

                if (daysLeft <= 5) {
                    String color = "#FFA500"; // Default color (orange)
                    if (daysLeft <= 1) {
                        color = "#FF0000"; // Red for 1 day or less
                    } else if (daysLeft <= 3) {
                        color = "#FFA500"; // Orange for 3 days or less
                    }
                    notifications.add(new NotificationItem(productName + " is expiring in " + daysLeft + " days.", color));
                }
            }
        }
        return notifications;
    }
}
