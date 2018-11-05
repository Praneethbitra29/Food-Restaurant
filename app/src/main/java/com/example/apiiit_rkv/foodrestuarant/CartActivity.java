package com.example.apiiit_rkv.foodrestuarant;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {


    private Button apply;
    private TextView items,grandtotal,notes;
    private EditText promocode;

    LinearLayout cartLayout;
    TableLayout cartpromocode;
    LinearLayout discount;

    StringBuffer buffer;
    int count=0;
    double grandtotalprice;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        final DatabaseHelper mydb = new DatabaseHelper(this);
        buffer = new StringBuffer();

        grandtotal = (TextView)findViewById(R.id.grandtotal);
        promocode = (EditText)findViewById(R.id.promocode);
        apply = (Button) findViewById(R.id.cart_apply);
        items = (TextView)findViewById(R.id.values);
        notes = (TextView)findViewById(R.id.notes);

        cartLayout = (LinearLayout)findViewById(R.id.cart_grandtotal);
        cartpromocode = (TableLayout)findViewById(R.id.cart_promocode);
        discount = (LinearLayout)findViewById(R.id.discount);



        Cursor result = mydb.getAllData();
        while(result.moveToNext()){
            if(!(result.getString(1).equals("0"))){
                if(result.getString(0).equals("Spinach\nNoodles")){
                    name="Spinach Noodles";
                }else if(result.getString(0).equals("Fried\nMashi")){
                    name="Fried Mashi";
                }else if(result.getString(0).equals("Margherita\nPizza")){
                    name="Fried Mashi";
                } else{
                    name=result.getString(0);
                }
                buffer.append("     Name               -    "+name+"\n");
                buffer.append("     Price                 -    "+result.getString(2)+"\n");
                buffer.append("     Quantity           -    "+result.getString(1)+"\n");
                buffer.append("     Total Price       -    "+result.getString(3)+"\n\n");
                grandtotalprice = grandtotalprice + Double.valueOf(result.getString(3));
            }else{
                count++;
            }
        }
        if(count == 9){
            items.setText("No Items in the Cart");
            cartLayout.setVisibility(View.INVISIBLE);
            cartpromocode.setVisibility(View.INVISIBLE);
            discount.setVisibility((View.INVISIBLE));
        }else {
            items.setText(buffer.toString());
            grandtotal.setText(String.valueOf(grandtotalprice));
        }

        promocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promocode.setInputType(InputType.TYPE_CLASS_TEXT);
                promocode.requestFocus();

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notes.setText("Coupon : F22LABS");
                grandtotal.setText(String.valueOf(grandtotalprice));

                if(promocode.getText().toString().equals("F22LABS")){
                    if(grandtotalprice > 400){
                        double discount_price = grandtotalprice - (grandtotalprice*0.2);
                        grandtotal.setText(String.valueOf(discount_price));
                        notes.setText("20% Discount Coupon Applied.");
                        discount.setVisibility(View.VISIBLE);

                        promocode.setInputType(InputType.TYPE_NULL);

                    }
                    else {
                        promocode.setError("Grand Total is not greater than 400.");
                        promocode.requestFocus();
                    }
                }else {
                    promocode.setError("Invalid Promocode.");
                    promocode.requestFocus();
                }
            }
        });

        mydb.close();
    }
}
