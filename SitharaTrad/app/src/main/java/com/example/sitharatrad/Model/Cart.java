package com.example.sitharatrad.Model;

public class Cart {
    String productcat, productname,producttype,productquantity,productoffer, productcost, productimage;

    public String getProductcat() {
        return productcat;
    }

    public String getProductname() {
        return productname;
    }

    public String getProducttype() {
        return producttype;
    }

    public String getProductquantity() {
        return productquantity;
    }

    public String getProductoffer() {
        return productoffer;
    }

    public String getProductcost() {
        return productcost;
    }

    public String getProductimage() {
        return productimage;
    }

    public Cart(String productcat, String productname, String producttype, String productquantity, String productoffer, String productcost, String productimage) {
        this.productcat = productcat;
        this.productname = productname;
        this.producttype = producttype;
        this.productquantity = productquantity;
        this.productoffer = productoffer;
        this.productcost = productcost;
        this.productimage = productimage;
    }







    public Cart(){

    }
}
