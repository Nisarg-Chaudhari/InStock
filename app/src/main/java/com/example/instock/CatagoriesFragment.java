package com.example.instock;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class CatagoriesFragment extends Fragment {

    RecyclerView recyclerView;

    List<String> categoryList;

    View view_root;
    CategoryAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        categoryList = new ArrayList<>();

        categoryList.add("category 1");
        categoryList.add("category 2");
        categoryList.add("category 3");
        categoryList.add("category 4");
        categoryList.add("category 5");
        categoryList.add("category 6");
        categoryList.add("category 7");
        categoryList.add("category 8");
        categoryList.add("category 9");
        categoryList.add("category 10");
        categoryList.add("category 11");
        categoryList.add("category 12");
        categoryList.add("category 13");
        categoryList.add("category 14");
        categoryList.add("category 15");

        adapter = new CategoryAdapter(categoryList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catagories, container, false);

        return view_root = view;
    }

    @Override
    public void onStart() {
        super.onStart();

        setUpRecycle();
    }


    @Override
    public void onResume() {
        super.onResume();

        setUpRecycle();
    }

    private void setUpRecycle() {
        recyclerView = view_root.findViewById(R.id.category_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}