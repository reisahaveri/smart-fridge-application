package com.example.smart_fridge_application;

public class Product {
    private int id;
    private String name;
    private String brand;
    private long expiryDate;
    private String imageUrl;

    public Product(int id, String name, String brand, long expiryDate) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.expiryDate = expiryDate;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
