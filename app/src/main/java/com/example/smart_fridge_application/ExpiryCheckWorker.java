package com.example.smart_fridge_application;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;
import java.util.List;

public class ExpiryCheckWorker extends Worker {

    private static final String CHANNEL_ID = "expiry_notification_channel";
    private static final int NOTIFICATION_ID = 1;

    public ExpiryCheckWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        createNotificationChannel();
        checkForExpiringProducts();
        return Result.success();
    }

    private void checkForExpiringProducts() {
        MyDatabaseHelper myDB = new MyDatabaseHelper(getApplicationContext());
        List<Product> expiringProducts = myDB.getExpiringProducts();

        for (Product product : expiringProducts) {
            int daysLeft = calculateDaysLeft(product.getExpiryDate());
            if (daysLeft == 5 || daysLeft == 3 || daysLeft == 1) {
                sendNotification(product.getName(), daysLeft);
            }
        }
    }

    private int calculateDaysLeft(long expiryDateMillis) {
        long currentTimeMillis = System.currentTimeMillis();
        long diffInMillis = expiryDateMillis - currentTimeMillis;
        return (int) (diffInMillis / (1000 * 60 * 60 * 24));
    }

    private void sendNotification(String productName, int daysLeft) {
        Context context = getApplicationContext();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Product Expiry Alert")
                .setContentText(productName + " is expiring in " + daysLeft + " days.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Expiry Notification Channel";
            String description = "Channel for product expiry notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
