package com.example.sitharatrad.Model;

public class Cart {
    String product_name,product_discount,product_cost,product_image;

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public String getProduct_cost() {
        return product_cost;
    }

    public String getProduct_image() {
        return product_image;
    }

    public Cart(String product_name, String product_discount, String product_cost, String product_image) {
        this.product_name = product_name;
        this.product_discount = product_discount;
        this.product_cost = product_cost;
        this.product_image = product_image;
    }

    public Cart(){

    }
}
