package com.example.instock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ItemAdapter extends FirebaseRecyclerAdapter<ItemModel,ItemAdapter.ItemViewHolder> {

    public ItemAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ItemModel itemModel) {

        holder.title.setText(itemModel.getTitle());
        holder.count.setText(itemModel.getCount().toString());

    }

//    @NonNull
//    @Override
//    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.card_item,parent,false);
//        return new ItemViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
//
//        holder.title.setText(data.get(position).getTitle());
//        holder.count.setText(data.get(position).getCount());
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, count;
        MaterialButton increaseButton, decreaseButton;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_title);
            count = (TextView) itemView.findViewById(R.id.item_count_number);

            increaseButton = (MaterialButton) itemView.findViewById(R.id.button_increase);
            decreaseButton = (MaterialButton) itemView.findViewById(R.id.button_decrease);
        }
    }
}
