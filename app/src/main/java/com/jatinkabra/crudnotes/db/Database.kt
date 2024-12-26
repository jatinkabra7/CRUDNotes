package com.jatinkabra.crudnotes.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.jatinkabra.crudnotes.dataClasses.Note

@Database(entities = [Note::class], version = 1)
abstract class Database : RoomDatabase() {

    companion object {
        const val NAME ="NoteDB"
    }

    abstract fun getDao() : NoteDao
}