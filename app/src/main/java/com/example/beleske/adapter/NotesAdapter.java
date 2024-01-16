package com.example.beleske.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beleske.R;
import com.example.beleske.activity.MainActivity;
import com.example.beleske.activity.UpdateNotesActivity;
import com.example.beleske.model.NotesModel;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {

    MainActivity mainActivity;
    List<NotesModel> notesModels;
    List<NotesModel> allNotesItem;
    public NotesAdapter(MainActivity mainActivity, List<NotesModel> notesModels) {
        this.mainActivity = mainActivity;
        this.notesModels = notesModels;
        allNotesItem = new ArrayList<>(notesModels);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchNotes(List<NotesModel> filterredName){
        this.notesModels = filterredName;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public notesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull notesViewHolder holder, int position) {
        NotesModel note = notesModels.get(position);

        switch (note.notesPriority) {
            case "1":
                holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
                break;
            case "2":
                holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
                break;
            case "3":
                holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
                break;
        }

        holder.title.setText(note.notesTitle);
        holder.notesDate.setText(note.notesDate);

        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(mainActivity, UpdateNotesActivity.class);
            intent.putExtra("id", note.id);
            intent.putExtra("title", note.notesTitle);
            intent.putExtra("note", note.notes);
            intent.putExtra("priority", note.notesPriority);

            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notesModels.size();
    }
    public static class notesViewHolder extends RecyclerView.ViewHolder {
        TextView title, notesDate;
        View notesPriority;
        public notesViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notesTitle);
            notesPriority = itemView.findViewById(R.id.notesPriority);
            notesDate = itemView.findViewById(R.id.notesDate);
        }
    }
}
