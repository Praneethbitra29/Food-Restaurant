package com.example.apiiit_rkv.foodrestuarant;

import android.widget.ImageView;
import android.widget.TextView;

public class FoodItem {

    private int mimageresource;
    private String mfoodname,mfoodprice,mfoodcount,mfoodplus,mfoodminus;

    public FoodItem(int imageresource, String foodname, String foodprice, String foodcount, String foodplus, String foodminus){
        mimageresource=imageresource;
        mfoodname=foodname;
        mfoodprice=foodprice;
        mfoodcount=foodcount;
        mfoodplus=foodplus;
        mfoodminus=foodminus;
    }
    public void changecount(String text){
        mfoodcount=text;
    }
    public String getMfoodcount() {
        return mfoodcount;
    }

    public void setMfoodcount(String mfoodcount) {
        this.mfoodcount = mfoodcount;
    }

    public String getMfoodplus() {
        return mfoodplus;
    }

    public void setMfoodplus(String mfoodplus) {
        this.mfoodplus = mfoodplus;
    }

    public String getMfoodminus() {
        return mfoodminus;
    }

    public void setMfoodminus(String mfoodminus) {
        this.mfoodminus = mfoodminus;
    }

    public int getMimageresource() {
        return mimageresource;
    }

    public void setMimageresource(int mimageresource) {
        this.mimageresource = mimageresource;
    }

    public String getMfoodname() {
        return mfoodname;
    }

    public void setMfoodname(String mfoodname) {
        this.mfoodname = mfoodname;
    }

    public String getMfoodprice() {
        return mfoodprice;
    }

    public void setMfoodprice(String mfoodprice) {
        this.mfoodprice = mfoodprice;
    }
}
