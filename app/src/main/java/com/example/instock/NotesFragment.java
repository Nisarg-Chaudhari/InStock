package com.example.instock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotesFragment extends Fragment {
    RecyclerView recyclerView;
    NoteAdapter adapter;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String uid = user.getUid();

    DatabaseReference mNotesref = FirebaseDatabase.getInstance().getReference()
            .child("users").child(uid).child("Notes");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        recyclerView = view.findViewById(R.id.notes_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<NoteModel> options =
                new FirebaseRecyclerOptions.Builder<NoteModel>()
                        .setQuery(mNotesref, NoteModel.class)
                        .build();

        adapter = new NoteAdapter(options,mNotesref);
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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