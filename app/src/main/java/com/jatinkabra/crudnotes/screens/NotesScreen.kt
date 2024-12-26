package com.jatinkabra.crudnotes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.nestedscroll.nestedScrollModifierNode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jatinkabra.crudnotes.dataClasses.Note
import com.jatinkabra.crudnotes.ui.theme.ColorGrey
import com.jatinkabra.crudnotes.ui.theme.ColorRed
import com.jatinkabra.crudnotes.viewmodel.NoteViewModel
import androidx.compose.ui.Modifier.Companion as Modifier1
import androidx.compose.runtime.livedata.observeAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(modifier: Modifier = Modifier, navHostController: NavHostController, viewModel: NoteViewModel) {

    val noteList by viewModel.singleitem.observeAsState()

    var searchQuery by remember {
        mutableStateOf("")
    }

    val filteredNotes = noteList?.filter {
        (it.title.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true))
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(searchQuery, {searchQuery = it})
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate("UpsertNotesScreen")
                },
                shape = CircleShape,
                containerColor = ColorRed.copy(0.9f),
                contentColor = Color.White.copy(0.7f)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        },

        containerColor = Color.Black
    ) {innerpadding ->

        LazyColumn(
            modifier = modifier
                .padding(innerpadding)
                .padding(10.dp)
        ) {

            if(filteredNotes != null) {

                items(filteredNotes) {
                    SingleNote(note = it)
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    searchQuery : String,
    onSearchQueryChange : (String) -> Unit,
    modifier: Modifier = Modifier
) {

    TopAppBar(
        modifier = Modifier
            .systemBarsPadding()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(100.dp))
            .border(2.dp, Color.White.copy(0.7f), RoundedCornerShape(100.dp))
            .height(55.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorGrey
        ),
        title = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {

                TextField(
                    modifier = Modifier.fillMaxSize(),
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    placeholder = { Text(text = "Search Notes", fontSize = 16.sp, color = Color.White.copy(0.7f))},
                    value = searchQuery,
                    onValueChange = onSearchQueryChange
                )
            }
        }
    )
}

@Composable
fun SingleNote(note: Note) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(ColorGrey)
            .padding(10.dp)
    ) {


        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Menu",
            tint = Color.White.copy(0.7f),
            modifier = Modifier.align(Alignment.TopEnd)
        )


        Column {
            Text(
                text = note.title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = note.description,
                color = Color.White.copy(0.8f),
                fontSize = 20.sp

            )
        }
    }

}