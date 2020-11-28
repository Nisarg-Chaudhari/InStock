package com.example.instock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

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

        cardOnClick(activity,R.id.card_grocery);
        cardOnClick(activity,R.id.card_grooming);
        cardOnClick(activity,R.id.card_stationary);
        cardOnClick(activity,R.id.card_tech);
        cardOnClick(activity,R.id.card_others);

    }

    private void cardOnClick(AppCompatActivity activity,int id) {

    }


}