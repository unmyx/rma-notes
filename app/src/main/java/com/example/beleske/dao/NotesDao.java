package com.example.beleske.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.beleske.model.NotesModel;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM Notes_Table")
    LiveData<List<NotesModel>> getallNotes();

    @Query("SELECT * FROM Notes_Table ORDER BY notes_priority ASC")
    LiveData<List<NotesModel>> lowToHigh();

    @Query("SELECT * FROM Notes_Table ORDER BY notes_priority DESC")
    LiveData<List<NotesModel>> highToLow();

    @Insert
    void insertNotes(NotesModel... notesModels);

    @Query("DELETE FROM Notes_Table WHERE id=:id;")
    void deleteNotes(int id);

    @Update
    void updateNotes(NotesModel notesModels);
}
