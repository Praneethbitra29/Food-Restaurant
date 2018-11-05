package com.example.apiiit_rkv.foodrestuarant;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class IndianFood extends Fragment {

    private RecyclerView recyclerView;
    private FoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutmanager;


    String biryani_quantity="0";
    String dosa_quantity="0";
    String chapati_quantity="0";

    DatabaseHelper mydb;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_indianfood, container, false);


        setHasOptionsMenu(true);


        mydb = new DatabaseHelper(getActivity());

        Cursor result_biryani = mydb.getSingleData("Biryani");
        if(result_biryani.getCount() != 0) {
            result_biryani.moveToNext();
            if (!(result_biryani.getString(1).equals(null))) {
                biryani_quantity = result_biryani.getString(1);
            }
        }


        Cursor result_dosa = mydb.getSingleData("Dosa");
        if(result_biryani.getCount() != 0) {
            result_dosa.moveToNext();
            if (!(result_dosa.getString(1).equals(null))) {
                dosa_quantity = result_dosa.getString(1);
            }
        }

        Cursor result_chapati = mydb.getSingleData("Chapati");
        if(result_biryani.getCount() != 0) {
            result_chapati.moveToNext();
            if (!(result_chapati.getString(1).equals(null))) {
                chapati_quantity = result_chapati.getString(1);
            }
        }

        final ArrayList<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem(R.drawable.biryani,"Biryani","200",biryani_quantity,"+","-"));
        foodItems.add(new FoodItem(R.drawable.dosa,"Dosa","100",dosa_quantity,"+","-"));
        foodItems.add(new FoodItem(R.drawable.chapati,"Chapati","100",chapati_quantity,"+","-"));

        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        mLayoutmanager = new LinearLayoutManager(getActivity());
        mAdapter = new FoodAdapter(foodItems);
        recyclerView.setLayoutManager(mLayoutmanager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                description(foodItems.get(position).getMfoodname());
            }

            @Override
            public void onPlusClick(int position) {

                String name = foodItems.get(position).getMfoodname();
                Cursor result = mydb.getSingleData(name);
                result.moveToNext();
                int quantity = Integer.parseInt(result.getString(1))+1;
                int totalprice = Integer.parseInt(result.getString(2))*quantity;
                mydb.updateData(name,quantity,200,totalprice);
                foodItems.get(position).changecount(String.valueOf(quantity));
                mAdapter.notifyItemChanged(position);
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onMinusClick(int position) {

                String name = foodItems.get(position).getMfoodname();
                Cursor result = mydb.getSingleData(name);
                result.moveToNext();
                int quantity = Integer.parseInt(result.getString(1))-1;
                int totalprice = Integer.parseInt(result.getString(2))*quantity;
                if(quantity < 0){
                    mydb.updateData(name,0,200,0);
                    foodItems.get(position).changecount("0");
                }else {
                    mydb.updateData(name,quantity,200,totalprice);
                    foodItems.get(position).changecount(String.valueOf(quantity));
                }
                mAdapter.notifyItemChanged(position);
                getActivity().invalidateOptionsMenu();

            }
        });


        mydb.close();

        return root;
    }

    private void description(String name) {

        if(name.equals("Biryani")){
            Intent intent = new Intent(getActivity(),Biryani.class);
            startActivity(intent);
        }
        else if(name.equals("Dosa")){
            Intent intent = new Intent(getActivity(),Dosa.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getActivity(),Chapati.class);
            startActivity(intent);
        }


    }

    @Override
    public void onResume() {

        super.onResume();
        getActivity().invalidateOptionsMenu();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        DatabaseHelper mydb = new DatabaseHelper(getActivity());
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
        super.onCreateOptionsMenu(menu, inflater);
    }

}
