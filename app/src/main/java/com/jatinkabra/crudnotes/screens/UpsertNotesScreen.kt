package com.jatinkabra.crudnotes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jatinkabra.crudnotes.dataClasses.Note
import com.jatinkabra.crudnotes.ui.theme.ColorGrey
import com.jatinkabra.crudnotes.ui.theme.ColorRed
import com.jatinkabra.crudnotes.viewmodel.NoteViewModel

@Composable
fun UpsertNotesScreen(viewModel: NoteViewModel, navHostController: NavHostController) {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

//    if(note.title.isNotEmpty()) {
//        title = note.title
//    }
//
//    if(note.description.isNotEmpty()) {
//        description = note.description
//    }

    Scaffold(
        containerColor = Color.Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

//                    if(note.title.isNotEmpty() || note.description.isNotEmpty()) {
//                        viewModel.deleteNote(note.id)
//                    }

                    viewModel.upsertNote(title = title, description = description)

                    navHostController.popBackStack()
                },
                shape = CircleShape,
                containerColor = ColorRed.copy(0.9f),
                contentColor = Color.White.copy(0.7f)
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
            }
        }
    ) {innerpadding ->
        Box(
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    text = "Insert Note",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                TextField(
                    textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = ColorGrey,
                        focusedContainerColor = ColorGrey,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White.copy(0.7f)
                    ),
                    value = title,
                    onValueChange = {title = it},
                    placeholder = {
                        Text(
                            text = "Title",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(0.7f)
                        )
                    }
                )

                TextField(
                    textStyle = TextStyle(fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = ColorGrey,
                        focusedContainerColor = ColorGrey,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White.copy(0.7f)
                    ),
                    value = description,
                    onValueChange = {description = it},
                    placeholder = { Text(text = "Description", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White.copy(0.7f))}
                )

            }
        }
    }
}