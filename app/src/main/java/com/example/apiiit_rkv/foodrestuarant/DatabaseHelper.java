package com.example.apiiit_rkv.foodrestuarant;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.PublicKey;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "order.db";
    public String query = "CREATE TABLE FOODITEMS(NAME TEXT PRIMARY KEY,QUANTITY INTEGER,PRICE INTEGER,TOTALPRICE INTEGER);";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS FOODITEMS;");
        onCreate(db);

    }

    public boolean insertData(String name,int quantity,int price,int totalprice){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("QUANTITY",quantity);
        contentValues.put("PRICE",price);
        contentValues.put("TOTALPRICE",totalprice);
        long result = db.insert("FOODITEMS",null ,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Cursor getSingleData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM FOODITEMS WHERE NAME = '"+name+"'",null);
        return result;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM FOODITEMS",null);
        return result;
    }

    public boolean updateData(String name,int quantity,int price,int totalprice){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("QUANTITY",quantity);
        contentValues.put("PRICE",price);
        contentValues.put("TOTALPRICE",totalprice);
        db.update("FOODITEMS",contentValues,"NAME = ?",new String[] {name});
        return true;
    }

    public void deleteTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("FOODITEMS",null,null);
    }

}
