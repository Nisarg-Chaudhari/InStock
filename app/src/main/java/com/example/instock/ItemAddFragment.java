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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class ItemAddFragment extends Fragment {

    DatabaseReference mCategoryref, mItemsref,mOldItemref;
    TextInputEditText title_input,count_input;
    String title,count;
    ItemModel item_old;

    boolean isEdit;


    public ItemAddFragment() { }

    public ItemAddFragment(DatabaseReference mCategoryref, boolean isEdit) {
        this.mCategoryref = mCategoryref;
        this.mItemsref = mCategoryref.child("Items");
        this.isEdit = isEdit;
    }

    public ItemAddFragment(DatabaseReference mCategoryref, ItemModel item,DatabaseReference mOldItemref, boolean isEdit) {
        this.mCategoryref = mCategoryref;
        this.mItemsref = mCategoryref.child("Items");
        this.mOldItemref = mOldItemref;
        this.item_old = item;
        this.isEdit = isEdit;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        count_input = (TextInputEditText) view.findViewById(R.id.add_item_count_number_input);
        title_input = (TextInputEditText) view.findViewById(R.id.add_item_title_input);


        MaterialButton doneButton = (MaterialButton) view.findViewById(R.id.button_done);
        MaterialButton addButton = (MaterialButton) getActivity().findViewById(R.id.item_add_button);
        addButton.setEnabled(false);

        if(isEdit){
            count_input.setText(item_old.getCount());
            title_input.setText(item_old.getTitle());
        }

        doneButton.setOnClickListener(v -> {
            boolean isEmpty = (count_input.getText() == null || count_input.getText().toString().trim().isEmpty()
                    || count_input.getText() == null || count_input.getText().toString().trim().isEmpty());

            if(!isEmpty) {
                count = count_input.getText().toString();
                title = title_input.getText().toString();
                ItemModel item_new = new ItemModel(count, title);
                mItemsref.child("itm-" + title).setValue(item_new);
            }

            if (isEdit)
                mOldItemref.removeValue();

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