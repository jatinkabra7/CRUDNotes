package com.jatinkabra.crudnotes.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jatinkabra.crudnotes.dataClasses.Note
import com.jatinkabra.crudnotes.ui.theme.ColorGrey
import com.jatinkabra.crudnotes.ui.theme.ColorRed
import com.jatinkabra.crudnotes.viewmodel.AuthViewModel
import kotlin.math.sin

@Composable
fun UpsertNotesScreen(
    id: String?,
    navHostController: NavHostController
) {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }



    var id_cnt = 0


    // val note = viewModel.getNoteWithId(id).observeAsState()  // Observe the LiveData
    val user = FirebaseAuth.getInstance().currentUser

    if(user != null) {
        val userId = user.uid
        val db = FirebaseFirestore.getInstance()
        val notesDBRef = db.collection("users").document(userId).collection("notes")

        notesDBRef.get().addOnSuccessListener { task->
            if(task != null) {
                id_cnt = task.size() + 1
            }
            else id_cnt = 0
        }

        var docId by remember {
            mutableStateOf(id)
        }

        LaunchedEffect(key1 = true) {

            if (id != "0") {
                if (id != null) {

                    notesDBRef.whereEqualTo("id", id).get().addOnSuccessListener { query->
                        if(!query.isEmpty) {
                            docId = query.documents[0].id
                            val note = query.documents[0].toObject(Note::class.java)
                            note?.let {
                                title = note.title
                                description = note.description
                            }
                        }
                    }

//                notesDBRef.document(id).get().addOnSuccessListener { document ->
//                    if (document.exists()) {
//
//                        val note = document.toObject(Note::class.java)
//                        note?.let {
//                            title = it.title
//                            description = it.description
//                        }
//                    }
//                }
                }
            }
        }


        Scaffold(
            containerColor = Color.Black,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {

                        if (title.isNotEmpty() || description.isNotEmpty()) {

                            if(id != null) {
                                notesDBRef.document(docId!!).get().addOnSuccessListener { document ->
                                    if(document.exists()) {
                                        notesDBRef.document(docId!!).update("title", title, "description", description)
                                    }
                                    else {
                                        notesDBRef.add(Note(id = id_cnt.toString(),title = title, description = description))
                                    }
                                }
                            }
                        }

                        navHostController.popBackStack()
                    },
                    shape = CircleShape,
                    containerColor = ColorRed.copy(0.9f),
                    contentColor = Color.White.copy(0.7f)
                ) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Done")
                }
            }
        ) { innerpadding ->
            Box(
                modifier = Modifier
                    .padding(innerpadding)
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())
                ) {

//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(end = 5.dp),
//                        text = "Insert Note",
//                        color = Color.White,
//                        fontSize = 40.sp,
//                        fontWeight = FontWeight.Bold,
//                        textAlign = TextAlign.Center
//                    )



                    TextField(
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier
                            .fillMaxWidth()
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
                        onValueChange = { title = it },
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .height(200.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = ColorGrey,
                            focusedContainerColor = ColorGrey,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White.copy(0.7f)
                        ),
                        value = description,
                        onValueChange = { description = it },
                        placeholder = {
                            Text(
                                text = "Description",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(0.7f)
                            )
                        }
                    )


                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End)
                            .padding(end = 5.dp),
                        text = "Make sure you are connected to the internet before adding a note",
                        color = Color.White.copy(0.7f),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.End)
                            .padding(end = 5.dp),
                        text = "You can update/delete the note without internet",
                        color = Color.White.copy(0.7f),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )

                }
            }
    }



    }


}