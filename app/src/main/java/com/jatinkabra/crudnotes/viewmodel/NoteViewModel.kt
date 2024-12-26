package com.jatinkabra.crudnotes.viewmodel

import android.content.ClipDescription
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatinkabra.crudnotes.dataClasses.Note
import com.jatinkabra.crudnotes.db.MainApplication
import com.jatinkabra.crudnotes.db.NoteDao
import kotlinx.coroutines.launch
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable

class NoteViewModel : ViewModel() {

    val noteDao = MainApplication.noteDB.getDao()

    @Contextual
    val singleitem : LiveData<List<Note>> = noteDao.getAllNotes()

    fun upsertNote(title : String, description : String) {
        viewModelScope.launch {
            noteDao.upsertNote(Note(title = title, description = description))
        }
    }

    fun deleteNote(id : Int) {
        viewModelScope.launch {
            noteDao.deleteNote(id = id)
        }
    }
}