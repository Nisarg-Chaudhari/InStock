package com.example.instock;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

public class NoteAddFragment extends Fragment {

    DatabaseReference mNotesref,mOldNoteref;
    TextInputEditText title_input,body_input;
    String title,count;
    NoteModel note_old;

    boolean isEdit;

    public NoteAddFragment() { }

    public NoteAddFragment(DatabaseReference mNotesref, boolean isEdit) {
        this.mNotesref = mNotesref;
        this.isEdit = isEdit;
    }

    public NoteAddFragment(DatabaseReference mNotesref, NoteModel note,DatabaseReference mOldNoteref, boolean isEdit) {
        this.mNotesref = mNotesref;
        this.mOldNoteref = mOldNoteref;
        this.note_old = note;
        this.isEdit = isEdit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        body_input = (TextInputEditText) view.findViewById(R.id.add_note_body_input);
        title_input = (TextInputEditText) view.findViewById(R.id.add_note_title_input);


        MaterialButton doneButton = (MaterialButton) view.findViewById(R.id.note_button_done);
        MaterialButton addButton = (MaterialButton) getActivity().findViewById(R.id.note_add_button);
        addButton.setEnabled(false);

        if(isEdit){
            body_input.setText(note_old.getBody());
            title_input.setText(note_old.getTitle());
        }

        doneButton.setOnClickListener(v -> {
            boolean isEmpty = (body_input.getText() == null || body_input.getText().toString().trim().isEmpty()
                    || title_input.getText() == null || title_input.getText().toString().trim().isEmpty());

            if(!isEmpty) {
                count = title_input.getText().toString();
                title = title_input.getText().toString();
                ItemModel item_new = new ItemModel(count, title);
                mNotesref.child("nt-" + title).setValue(item_new);
            }

            if (isEdit)
                mOldNoteref.removeValue();

            getActivity().getSupportFragmentManager().popBackStack();
            addButton.setEnabled(true);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        MaterialButton addButton = (MaterialButton) getActivity().findViewById(R.id.item_add_button);
        addButton.setEnabled(true);

    }
}