package com.example.sitharatrad.Model;

public class Order {
    String oid,pname,pcost,pquantity,odate,pimage,ostatus,uid;

    public String getUid() {
        return uid;
    }

    public Order(String oid, String pname, String pcost, String pquantity, String odate, String pimage, String ostatus, String uid) {
        this.oid = oid;
        this.pname = pname;
        this.pcost = pcost;
        this.pquantity = pquantity;
        this.odate = odate;
        this.pimage = pimage;
        this.ostatus = ostatus;
        this.uid = uid;
    }

    public String getOstatus() {
        return ostatus;
    }



    public String getOid() {
        return oid;
    }

    public String getPname() {
        return pname;
    }

    public String getPcost() {
        return pcost;
    }

    public String getPquantity() {
        return pquantity;
    }

    public String getOdate() {
        return odate;
    }

    public String getPimage() {
        return pimage;
    }

  public Order(){  }
}
