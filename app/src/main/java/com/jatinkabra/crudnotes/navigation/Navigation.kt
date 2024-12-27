package com.jatinkabra.crudnotes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.jatinkabra.crudnotes.screens.NotesScreen
import com.jatinkabra.crudnotes.screens.UpsertNotesScreen
import com.jatinkabra.crudnotes.viewmodel.NoteViewModel
import kotlinx.serialization.Contextual

@Composable
fun Navigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "InsertNotesScreen"
    ) {
        composable("InsertNotesScreen") {
            NotesScreen(navHostController = navHostController, viewModel = NoteViewModel())
        }

        composable("UpsertNotesScreen"+"/{id}") {
            val id = it.arguments?.getString("id")
            if(id != null) {

                UpsertNotesScreen(id = id.toInt(), viewModel = NoteViewModel(), navHostController = navHostController)
            }
        }
    }
}