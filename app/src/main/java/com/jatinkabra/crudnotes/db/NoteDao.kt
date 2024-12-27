package com.jatinkabra.crudnotes.db

import androidx.compose.runtime.State
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

    @Query("SELECT * FROM Note WHERE id = :id")
    fun getNoteWithId(id : Int) : Note

    @Query("UPDATE Note SET title = :title, description = :description WHERE id = :id")
    fun update(id: Int, title: String, description: String)
}