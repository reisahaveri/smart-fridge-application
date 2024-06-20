package com.example.smart_fridge_application;

public class NotificationItem {
    private String text;
    private String color;

    public NotificationItem(String text, String color) {
        this.text = text;
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }
}
