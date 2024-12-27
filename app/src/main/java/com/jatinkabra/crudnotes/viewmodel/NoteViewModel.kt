package com.jatinkabra.crudnotes.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jatinkabra.crudnotes.dataClasses.Note
import com.jatinkabra.crudnotes.db.MainApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable

class NoteViewModel : ViewModel() {


    private val noteDao = MainApplication.noteDB.getDao()

    @Contextual
    val singleitem : LiveData<List<Note>> = noteDao.getAllNotes()

    fun upsertNote(title : String, description : String) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.upsertNote(Note(title = title, description = description))
        }
    }

    fun deleteNote(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.deleteNote(id = id)
        }
    }

    fun getNoteWithId(id: Int): LiveData<Note?> {
        val result = MutableLiveData<Note?>()
        viewModelScope.launch(Dispatchers.IO) {
            val note = noteDao.getNoteWithId(id)
            result.postValue(note) // Updating the LiveData object
        }
        return result
    }

    fun update(id: Int, title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.update(id,title,description)
        }
    }

}