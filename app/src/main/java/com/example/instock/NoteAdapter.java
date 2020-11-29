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
import com.google.firebase.database.DatabaseReference;

public class NoteAdapter extends FirebaseRecyclerAdapter<NoteModel,NoteAdapter.NoteViewHolder> {

    DatabaseReference mNotesref;

    public NoteAdapter(@NonNull FirebaseRecyclerOptions<NoteModel> options,DatabaseReference mNotesref) {
        super(options);
        this.mNotesref= mNotesref;
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull NoteModel noteModel) {
        holder.title.setText(noteModel.getTitle());
        holder.body.setText(noteModel.getBody());

        holder.editButton.setOnClickListener(view -> {

            NoteAddFragment noteAddFragment = new NoteAddFragment(mNotesref,noteModel,mNotesref.child(getRef(position).getKey()),true);

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager().beginTransaction().add(R.id.items_container,noteAddFragment).addToBackStack(null).commit();

        });

        holder.deleteButton.setOnClickListener(view -> {

            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(view.getContext());
            builder.setTitle("Delete Alert");
            builder.setMessage("Do you want to delete the item?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseReference item =mNotesref.child(getRef(position).getKey());
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

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_note,parent,false);
        return new NoteViewHolder(view);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title,body;
        MaterialButton editButton,deleteButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.note_title);
            body = (TextView) itemView.findViewById(R.id.note_body);

            editButton = (MaterialButton) itemView.findViewById(R.id.note_button_edit);
            deleteButton = (MaterialButton) itemView.findViewById(R.id.note_button_delete);
        }
    }
}
