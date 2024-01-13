package com.example.beleske.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.beleske.model.NotesModel;
import com.example.beleske.dao.NotesDao;
import com.example.beleske.database.NotesDatabase;
import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<NotesModel>> getallNotes;
    public LiveData<List<NotesModel>> hightolow;
    public LiveData<List<NotesModel>> lowtohigh;

    public NotesRepository(Application application){
        NotesDatabase database = NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getallNotes = notesDao.getallNotes();
        hightolow = notesDao.highToLow();
        lowtohigh = notesDao.lowToHigh();
    }

    public void insertNotes(NotesModel notesModel){
        notesDao.insertNotes(notesModel);
    }

    public void deleteNotes(int id){
        notesDao.deleteNotes(id);
    }

    public void updateNotes(NotesModel notesModel){
        notesDao.updateNotes(notesModel);
    }

}
