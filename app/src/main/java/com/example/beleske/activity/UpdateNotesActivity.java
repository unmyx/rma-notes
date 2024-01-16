package com.example.beleske.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.beleske.R;
import com.example.beleske.model.NotesModel;
import com.example.beleske.databinding.ActivityUpdateNotesBinding;
import com.example.beleske.model.view_model.NotesViewModel;
import java.util.Date;
import java.util.Objects;

public class UpdateNotesActivity extends AppCompatActivity {
    ActivityUpdateNotesBinding binding;
    NotesViewModel notesViewModel;
    String priority = "1";
    Integer iid;
    String stitle, snotes, spriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iid = getIntent().getIntExtra("id", 0);
        stitle = getIntent().getStringExtra("title");
        snotes = getIntent().getStringExtra("note");
        spriority = getIntent().getStringExtra("priority");

        binding.upNotesTitle.setText(stitle);
        binding.upNotesData.setText(snotes);

        switch (spriority) {
            case "1":
                binding.greenPrior.setImageResource(R.drawable.baseline_done_24);
                priority = "1";
                break;
            case "2":
                binding.yellowPrior.setImageResource(R.drawable.baseline_done_24);
                priority = "2";
                break;
            case "3":
                binding.redPrior.setImageResource(R.drawable.baseline_done_24);
                priority = "3";
                break;
        }

        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        binding.greenPrior.setOnClickListener(v -> {
            binding.greenPrior.setImageResource(R.drawable.baseline_done_24);
            binding.yellowPrior.setImageResource(0);
            binding.redPrior.setImageResource(0);

            priority = "1";
        });
        binding.yellowPrior.setOnClickListener(v -> {
            binding.greenPrior.setImageResource(0);
            binding.yellowPrior.setImageResource(R.drawable.baseline_done_24);
            binding.redPrior.setImageResource(0);

            priority = "2";
        });
        binding.redPrior.setOnClickListener(v -> {
            binding.greenPrior.setImageResource(0);
            binding.yellowPrior.setImageResource(0);
            binding.redPrior.setImageResource(R.drawable.baseline_done_24);

            priority = "3";
        });

        binding.upNotesBtn.setOnClickListener(v->{
            String title = binding.upNotesTitle.getText().toString();
            String notes = binding.upNotesData.getText().toString();

            UpdateNotes(title, notes);
        });
    }

    private void UpdateNotes(String title, String notes) {
        Date date = new Date();
        CharSequence sequence = DateFormat.format("MMMM d, yyyy", date.getTime());

        NotesModel updateNotes = new NotesModel();
        updateNotes.id = iid;
        updateNotes.notesTitle = title;
        if (updateNotes.notesTitle.equals("")){
            updateNotes.notesTitle = "Bez naslova";
        }
        updateNotes.notes = notes;
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNotes(updateNotes);

        if (!Objects.equals("Beleske su azurirane!", "")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Beleske su azurirane!", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
            finish();
        }else{
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.deletenavigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.navigation_delete){
            BottomSheetDialog sheetDialog = new BottomSheetDialog(UpdateNotesActivity.this,
                    R.style.BottomSheetStyle);

            View view = LayoutInflater.from(UpdateNotesActivity.this).
                    inflate(R.layout.delete_confirmation, (LinearLayout) findViewById(R.id.bottom_confirm_layout));
            sheetDialog.setContentView(view);

            TextView yestv, notv;

            yestv = view.findViewById(R.id.delYesBtn);
            notv = view.findViewById(R.id.delNoBtn);

            yestv.setOnClickListener(v -> {
                notesViewModel.deleteNotes(iid);

                Toast toast = Toast.makeText(getApplicationContext(), "Belezka Obrisana!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
                toast.show();
                finish();
            });
            notv.setOnClickListener(v -> {
                sheetDialog.dismiss();
            });
            sheetDialog.show();
        }
        return true;
    }
}
