package com.example.sitharatrad.Model;

public class User {
    String fname,lname,email,pnumber,address,pin,shopname,gstin;

    public String getShopname() {
        return shopname;
    }

    public String getGstin() {
        return gstin;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPnumber() {
        return pnumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPin() {
        return pin;
    }

    public User(String fname, String lname, String email, String pnumber, String address, String pin,String shopname,String gstin) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.pnumber = pnumber;
        this.address = address;
        this.pin = pin;
        this.shopname = shopname;
        this.gstin = gstin;
    }
    public User(){

    }
}
