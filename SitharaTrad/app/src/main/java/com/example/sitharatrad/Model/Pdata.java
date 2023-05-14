package com.example.sitharatrad.Model;

public class Pdata {
    String Product_type,Product_name,Product_cost,ImageLink,ExpiryDate,Availability;

    public String getProduct_type() {
        return Product_type;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public String getProduct_cost() {
        return Product_cost;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public String getAvailability() {
        return Availability;
    }

    public Pdata(String product_type, String product_name, String product_cost, String imageLink, String expiryDate, String availability) {
        Product_type = product_type;
        Product_name = product_name;
        Product_cost = product_cost;
        ImageLink = imageLink;
        ExpiryDate = expiryDate;
        Availability = availability;
    }

    public Pdata() {
    }
}
