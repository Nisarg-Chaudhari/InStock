package com.example.instock;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class ItemAdapter extends FirebaseRecyclerAdapter<ItemModel,ItemAdapter.ItemViewHolder> {

    DatabaseReference mCategoryref,mItemsref;

    public ItemAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options,DatabaseReference mCategoryref) {
        super(options);
        this.mCategoryref = mCategoryref;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position, @NonNull final ItemModel itemModel) {

        holder.title.setText(itemModel.getTitle());
        holder.count.setText(itemModel.getCount());

        final ItemModel item_new = new ItemModel();

        mItemsref = mCategoryref.child("Items");


        holder.increaseButton.setOnClickListener(view -> {
            item_new.setTitle(itemModel.getTitle());
            item_new.setCount(Integer.toString(Integer.parseInt(itemModel.getCount().toString())+1));

            mItemsref.child(getRef(position).getKey()).removeValue();
            mItemsref.child("itm-" + item_new.getTitle()).setValue(item_new);
        });

        holder.decreaseButton.setOnClickListener(view -> {
            item_new.setTitle(itemModel.getTitle());
            item_new.setCount(Integer.toString(Integer.parseInt(itemModel.getCount().toString())-1));

            mItemsref.child(getRef(position).getKey()).removeValue();
            mItemsref.child("itm-" + item_new.getTitle()).setValue(item_new);
        });

        holder.editButton.setOnClickListener(view -> {

            ItemAddFragment itemAddFragment = new ItemAddFragment(mCategoryref,itemModel,mItemsref.child(getRef(position).getKey()),true);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().add(R.id.items_container,itemAddFragment).addToBackStack(null).commit();

        });

        holder.deleteButton.setOnClickListener(view -> {

            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext());
            builder.setTitle("Delete Alert");
            builder.setMessage("Do you want to delete the item?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseReference item = mCategoryref.child("Items")
                            .child(getRef(position).getKey());
                    item.removeValue();
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        });

    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, count,category;
        MaterialButton increaseButton, decreaseButton,editButton,deleteButton;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.item_title);
            count = (TextView) itemView.findViewById(R.id.item_count_number);
            category = (TextView) itemView.findViewById(R.id.category_name);

            increaseButton = (MaterialButton) itemView.findViewById(R.id.button_increase);
            decreaseButton = (MaterialButton) itemView.findViewById(R.id.button_decrease);
            editButton = (MaterialButton) itemView.findViewById(R.id.button_edit);
            deleteButton = (MaterialButton) itemView.findViewById(R.id.button_delete);
        }
    }
}
