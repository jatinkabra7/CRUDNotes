package com.jatinkabra.crudnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.jatinkabra.crudnotes.navigation.Navigation
import com.jatinkabra.crudnotes.screens.NotesScreen
import com.jatinkabra.crudnotes.screens.UpsertNotesScreen
import com.jatinkabra.crudnotes.ui.theme.CRUDNotesTheme
import kotlinx.serialization.Contextual

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            val navHostController = rememberNavController()

            Navigation(navHostController = navHostController)

        }
    }
}
