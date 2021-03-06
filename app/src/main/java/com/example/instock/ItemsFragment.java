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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ItemsFragment extends Fragment {

    RecyclerView recyclerView;
    ItemAdapter adapter;
    String parent_category_name;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String uid = user.getUid();

    DatabaseReference mDashboardref = FirebaseDatabase.getInstance().getReference()
            .child("users").child(uid).child("Dashboard");
    DatabaseReference mCategoryref;

    public ItemsFragment() { this.parent_category_name = "";}

    public ItemsFragment(String parent_category_name) {
        this.parent_category_name = parent_category_name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        recyclerView = view.findViewById(R.id.items_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mCategoryref = mDashboardref.child(parent_category_name);
        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                        .setQuery(mCategoryref.child("Items"), ItemModel.class)
                        .build();

        adapter = new ItemAdapter(options,mCategoryref);
        recyclerView.setAdapter(adapter);

        TextView parentCategory = (TextView) view.findViewById(R.id.category_name);
        parentCategory.setText(getString(R.string.category_name,parent_category_name));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton addButton = (MaterialButton) view.findViewById(R.id.item_add_button);
        addButton.setEnabled(true);

        addButton.setOnClickListener(v -> {

            ItemAddFragment itemAddFragment = new ItemAddFragment(mCategoryref,false);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.items_container,itemAddFragment).addToBackStack(null).commit();
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();


    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}