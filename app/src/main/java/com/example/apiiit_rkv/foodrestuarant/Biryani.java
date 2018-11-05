package com.example.apiiit_rkv.foodrestuarant;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyScanManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Biryani extends AppCompatActivity {


    private TextView plus,minus,count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biryani);


        final DatabaseHelper mydb = new DatabaseHelper(this);
        plus = (TextView)findViewById(R.id.biryani_plus);
        count = (TextView)findViewById(R.id.biryani_count);
        minus = (TextView)findViewById(R.id.biryani_minus);



        Cursor result = mydb.getSingleData("Biryani");
        result.moveToNext();
        int quantity = Integer.parseInt(result.getString(1));
        count.setText(String.valueOf(quantity));

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = mydb.getSingleData("Biryani");
                result.moveToNext();
                int quantity = Integer.parseInt(result.getString(1))+1;
                int totalprice = Integer.parseInt(result.getString(2))*quantity;
                mydb.updateData("Biryani",quantity,200,totalprice);
                count.setText(String.valueOf(quantity));
                invalidateOptionsMenu();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = mydb.getSingleData("Biryani");
                result.moveToNext();
                int quantity = Integer.parseInt(result.getString(1))-1;
                int totalprice = Integer.parseInt(result.getString(2))*quantity;
                if(quantity < 0){
                    mydb.updateData("Biryani",0,200,0);
                    count.setText("0");
                }else {
                    mydb.updateData("Biryani",quantity,200,totalprice);
                    count.setText(String.valueOf(quantity));
                }
                invalidateOptionsMenu();
            }
        });
        mydb.close();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Biryani.this,MainActivity.class);
        intent.putExtra("tab","1");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        final DatabaseHelper mydb = new DatabaseHelper(this);
        Cursor result = mydb.getAllData();
        int count=0;
        while(result.moveToNext()){
            count=count+Integer.parseInt(result.getString(1));
        }
        if(count>0){
            menu.findItem(R.id.notification).setTitle(String.valueOf(count));
        }else {
            menu.findItem(R.id.notification).setVisible(false);
        }
        mydb.close();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.cart_action:
                showOrder();
                break;
            case R.id.notification:
                showOrder();
                break;
        }
        return super.onOptionsItemSelected(item);

    }


    private void showOrder() {
        Intent intent = new Intent(Biryani.this,CartActivity.class);
        startActivity(intent);
    }


}
