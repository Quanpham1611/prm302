package com.example.pmg302_project.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String imageLink;
    private String type;
    private int purchaseCount;

    private double rate;
    // Constructor
    public Product(int id, String name, String description, double price, String imageLink, String type, double rate, int purchaseCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageLink = imageLink;
        this.type = type;
        this.rate = rate;
        this.purchaseCount = purchaseCount;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageLink() { return imageLink; }
    public double getRate() { return rate; }
    public int getPurchaseCount() { return purchaseCount; }
    public String getType() { return type; }
}
