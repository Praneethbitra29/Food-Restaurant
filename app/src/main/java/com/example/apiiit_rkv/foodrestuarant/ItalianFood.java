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

public class ItalianFood extends Fragment {


    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    String panzanella_quantity="0";
    String pasta_quantity="0";
    String margheritapizza_quantity="0";

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_italianfood, container, false);

        setHasOptionsMenu(true);


        final DatabaseHelper mydb = new DatabaseHelper(getActivity());

        Cursor result_panzanella = mydb.getSingleData("Panzanella");
        result_panzanella.moveToNext();
        if(!(result_panzanella.getString(1).equals(null))){
            panzanella_quantity = result_panzanella.getString(1);
        }


        Cursor result_pasta = mydb.getSingleData("Pasta");
        result_pasta.moveToNext();
        if(!(result_pasta.getString(1).equals(null))){
            pasta_quantity = result_pasta.getString(1);
        }

        Cursor result_margheritapizza = mydb.getSingleData("Margherita\nPizza");
        result_margheritapizza.moveToNext();
        if(!(result_margheritapizza.getString(1).equals(null))){
            margheritapizza_quantity = result_margheritapizza.getString(1);
        }

        final ArrayList<FoodItem> foodItems = new ArrayList<>();
        foodItems.add(new FoodItem(R.drawable.panzanella,"Panzanella","200",panzanella_quantity,"+","-"));
        foodItems.add(new FoodItem(R.drawable.pasta,"Pasta","100",pasta_quantity,"+","-"));
        foodItems.add(new FoodItem(R.drawable.margheritapizza,"Margherita\nPizza","200",margheritapizza_quantity,"+","-"));

        recyclerView = (RecyclerView)root.findViewById(R.id.recyclerview_italian);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new FoodAdapter(foodItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String name = foodItems.get(position).getMfoodname();
                if(name.equals("Panzanella")){
                    Intent intent = new Intent(getActivity(),Panzanella.class);
                    startActivity(intent);
                }
                else if(name.equals("Pasta")){
                    Intent intent = new Intent(getActivity(),Pasta.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(),MarghertiaPiza.class);
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
        mydb.close();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }
}
