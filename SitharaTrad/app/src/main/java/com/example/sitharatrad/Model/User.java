package com.example.sitharatrad.Model;

public class User {
    String fname,lname,email,pnumber,address,pin;

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

    public User(String fname, String lname, String email, String pnumber, String address, String pin) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.pnumber = pnumber;
        this.address = address;
        this.pin = pin;
    }
    public User(){

    }
}
