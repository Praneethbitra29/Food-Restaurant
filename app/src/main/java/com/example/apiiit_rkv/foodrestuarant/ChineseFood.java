package com.example.apiiit_rkv.foodrestuarant;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ChineseFood extends Fragment {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    String spinachnoodles_quantity="0";
    String friedmashi_quantity="0";
    String dumplings_quantity="0";


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_chinesefood, container, false);

        setHasOptionsMenu(true);

        final DatabaseHelper mydb = new DatabaseHelper(getActivity());

        Cursor result_spinachnoodles = mydb.getSingleData("Spinach\nNoodles");
        result_spinachnoodles.moveToNext();
        if(!(result_spinachnoodles.getString(1).equals(null))){
            spinachnoodles_quantity = result_spinachnoodles.getString(1);
        }


        Cursor result_friedmashi = mydb.getSingleData("Fried\nMashi");
        result_friedmashi.moveToNext();
        if(!(result_friedmashi.getString(1).equals(null))){
            friedmashi_quantity = result_friedmashi.getString(1);
        }

        Cursor result_dumplings = mydb.getSingleData("Dumplings");
        result_dumplings.moveToNext();
        if(!(result_dumplings.getString(1).equals(null))){
            dumplings_quantity = result_dumplings.getString(1);
        }

        final ArrayList<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem(R.drawable.spinachnoodles,"Spinach\nNoodles","150",spinachnoodles_quantity,"+","-"));
        foodItems.add(new FoodItem(R.drawable.friedmashi,"Fried\nMashi","90",friedmashi_quantity,"+","-"));
        foodItems.add(new FoodItem(R.drawable.dumplings,"Dumplings","60",dumplings_quantity,"+","-"));


        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerview_chinese);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new FoodAdapter(foodItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = foodItems.get(position).getMfoodname();
                if(name.equals("Dumplings")){
                    Intent intent = new Intent(getActivity(),Dumplings.class);
                    startActivity(intent);
                }
                else if(name.equals("Fried\nMashi")){
                    Intent intent = new Intent(getActivity(),FriedMashi.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(),SpinachNoodles.class);
                    startActivity(intent);
                }
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
                adapter.notifyItemChanged(position);
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
                adapter.notifyItemChanged(position);
                getActivity().invalidateOptionsMenu();

            }
        });
        mydb.close();
        return root;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        final DatabaseHelper mydb = new DatabaseHelper(getActivity());
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }
}

