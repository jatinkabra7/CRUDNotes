package com.jatinkabra.crudnotes.db

import android.app.Application
import androidx.room.Room

class MainApplication : Application() {
    companion object {
        lateinit var noteDB : Database
    }

    override fun onCreate() {
        super.onCreate()

        noteDB = Room.databaseBuilder(
            context =applicationContext,
            Database::class.java,
            Database.NAME
        ).build()
    }
}