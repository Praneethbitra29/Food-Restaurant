package com.example.apiiit_rkv.foodrestuarant;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private ArrayList<FoodItem> mfoodlist;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onPlusClick(int position);
        void onMinusClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        private ImageView foodimage;
        private TextView foodcount,foodname,foodplus,foodminus,foodprice;

        public FoodViewHolder(@NonNull View itemView , final OnItemClickListener listener) {
            super(itemView);
            foodimage = itemView.findViewById(R.id.item_image);
            foodname = itemView.findViewById(R.id.item_foodname);
            foodminus = itemView.findViewById(R.id.food_minus);
            foodcount = itemView.findViewById(R.id.food_count);
            foodplus = itemView.findViewById(R.id.food_plus);
            foodprice = itemView.findViewById(R.id.food_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }

                }
            });
            foodplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onPlusClick(position);
                        }
                    }

                }
            });
            foodminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onMinusClick(position);
                        }
                    }
                }
            });
        }
    }

    public FoodAdapter (ArrayList<FoodItem> foodlist){
        mfoodlist = foodlist;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_fooditem,parent,false);
        FoodViewHolder fvh = new FoodViewHolder(v,mListener);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {

        FoodItem currentItem = mfoodlist.get(position);
        holder.foodimage.setImageResource(currentItem.getMimageresource());
        holder.foodname.setText(currentItem.getMfoodname());
        holder.foodprice.setText(currentItem.getMfoodprice());
        holder.foodminus.setText(currentItem.getMfoodminus());
        holder.foodcount.setText(currentItem.getMfoodcount());
        holder.foodplus.setText(currentItem.getMfoodplus());

    }

    @Override
    public int getItemCount() {
        return mfoodlist.size();
    }
}
