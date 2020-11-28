package com.example.instock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CategoriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AppCompatActivity activity = (AppCompatActivity) view.getContext();

        cardOnClick(activity,R.id.card_grocery,"Groceries");
        cardOnClick(activity,R.id.card_grooming,"Grooming");
        cardOnClick(activity,R.id.card_stationary,"Stationary");
        cardOnClick(activity,R.id.card_tech,"Tech");
        cardOnClick(activity,R.id.card_others,"Others");

    }

    private void cardOnClick(AppCompatActivity activity,int id,String category_name) {

        MaterialCardView card = activity.findViewById(id);

        ItemsFragment itemsFragment = new ItemsFragment(category_name);
        card.setOnClickListener(v -> {
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_host_fragment,itemsFragment).addToBackStack(null).commit();
        });

    }


}