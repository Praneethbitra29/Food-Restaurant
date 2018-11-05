package com.example.apiiit_rkv.foodrestuarant;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Panzanella extends AppCompatActivity {

    private TextView plus,minus,count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panzanella);

        final DatabaseHelper mydb = new DatabaseHelper(this);
        plus = (TextView)findViewById(R.id.panzanella_plus);
        count = (TextView)findViewById(R.id.panzanella_count);
        minus = (TextView)findViewById(R.id.panzanella_minus);
        Cursor result = mydb.getSingleData("Panzanella");
        result.moveToNext();
        int quantity = Integer.parseInt(result.getString(1));
        count.setText(String.valueOf(quantity));

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = mydb.getSingleData("Panzanella");
                result.moveToNext();
                int quantity = Integer.parseInt(result.getString(1))+1;
                int totalprice = Integer.parseInt(result.getString(2))*quantity;
                mydb.updateData("Panzanella",quantity,200,totalprice);
                count.setText(String.valueOf(quantity));
                invalidateOptionsMenu();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = mydb.getSingleData("Panzanella");
                result.moveToNext();
                int quantity = Integer.parseInt(result.getString(1))-1;
                int totalprice = Integer.parseInt(result.getString(2))*quantity;
                if(quantity < 0){
                    mydb.updateData("Panzanella",0,200,0);
                    count.setText("0");
                }else {
                    mydb.updateData("Panzanella",quantity,200,totalprice);
                    count.setText(String.valueOf(quantity));
                }
                invalidateOptionsMenu();
            }
        });
        mydb.close();
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
        Intent intent = new Intent(Panzanella.this,CartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Panzanella.this,MainActivity.class);
        intent.putExtra("tab","3");
        intent.putExtra("main","main");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
