package com.jatinkabra.crudnotes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.jatinkabra.crudnotes.dataClasses.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note ORDER BY id ASC")
    fun getAllNotes() : LiveData<List<Note>>

    @Upsert
    fun upsertNote(note : Note)

    @Query("DELETE FROM Note WHERE id = :id")
    fun deleteNote(id : Int)
}