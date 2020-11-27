package com.example.instock;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment {

    FirebaseAuth mAuth;
    MaterialButton logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logout = view.findViewById(R.id.logout_btn);
        mAuth = FirebaseAuth.getInstance();

        logout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(),LoginActivity.class));
            getActivity().finish();

        });
    }

}