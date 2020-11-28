package com.example.instock;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

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
