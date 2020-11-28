package com.example.instock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ItemModel> data_holder;
    String parent_category_name;

    public ItemsFragment(String parent_category_name) {
        this.parent_category_name = parent_category_name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        recyclerView = view.findViewById(R.id.items_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        data_holder = new ArrayList<>();

        ItemModel itm1 = new ItemModel("Item-1","5");
        data_holder.add(itm1);

        ItemModel itm2 = new ItemModel("Item-2","5");
        data_holder.add(itm2);

        ItemModel itm3 = new ItemModel("Item-3","5");
        data_holder.add(itm3);

        ItemModel itm4 = new ItemModel("Item-4","10");
        data_holder.add(itm4);

        recyclerView.setAdapter(new ItemAdapter(data_holder));

        TextView parentCategory = (TextView) view.findViewById(R.id.category_name);
        parentCategory.setText(parent_category_name);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}