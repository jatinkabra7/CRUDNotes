package com.jatinkabra.crudnotes.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jatinkabra.crudnotes.R
import com.jatinkabra.crudnotes.dataClasses.Note
import com.jatinkabra.crudnotes.navigation.Routes
import com.jatinkabra.crudnotes.ui.theme.Color1
import com.jatinkabra.crudnotes.ui.theme.Color2
import com.jatinkabra.crudnotes.ui.theme.Color3
import com.jatinkabra.crudnotes.ui.theme.Color4
import com.jatinkabra.crudnotes.ui.theme.Color5
import com.jatinkabra.crudnotes.ui.theme.Color6
import com.jatinkabra.crudnotes.ui.theme.ColorGrey
import com.jatinkabra.crudnotes.ui.theme.ColorRed
import com.jatinkabra.crudnotes.ui.theme.LightBlack
import com.jatinkabra.crudnotes.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(modifier: Modifier = Modifier, navHostController: NavHostController, authViewModel: AuthViewModel) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    var searchQuery by remember {
        mutableStateOf("")
    }
    
    // firebase firestore

    val user = FirebaseAuth.getInstance().currentUser
    if(user != null) {
        val userId = user.uid
        val db = FirebaseFirestore.getInstance()
        val notesDBRef = db.collection("users").document(userId).collection("notes")

        val noteList = remember {
            mutableStateListOf<Note>()
        }

        var loading by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(key1 = Unit) {
            notesDBRef.addSnapshotListener{value, error ->
                if(error == null) {
                    val data = value?.toObjects(Note::class.java)
                    noteList.clear()
                    noteList.addAll((data!!).sortedBy { it.id })

                    loading = false
                }
                else {
                    Log.d("data_error", error.message!!)
                    loading = true
                }
            }
        }

        val filteredNotes = noteList.filter {
            (it.title.contains(searchQuery, ignoreCase = true) || it.description.contains(searchQuery, ignoreCase = true))
        }



        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                ModalDrawerSheet {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight()
                            .background(LightBlack)
                            .padding(horizontal = 20.dp)
                    ) {

                        Text(
                            modifier = Modifier.padding(top = 20.dp),
                            text = "CRUD Notes",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = Color.White.copy(0.7f),
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = "App Info",
                                    fontSize = 24.sp,
                                    color = Color.White.copy(0.7f)
                                )
                            },
                            selected = false,
                            onClick = {
                                navHostController.navigate(Routes.AppInfo)
                            }
                        )

                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Block,
                                    contentDescription = null,
                                    tint = Color.Red.copy(0.7f),
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = "Sign Out",
                                    fontSize = 24.sp,
                                    color = Color.Red.copy(0.7f)
                                )
                            },
                            selected = false,
                            onClick = {
                                authViewModel.sigout()
                                navHostController.navigate(Routes.LoginScreen) {
                                    popUpTo(Routes.NotesScreen) {inclusive = true}
                                }
                            }
                        )
                    }
                }
            }
        ) {

            Scaffold(
                modifier = modifier,
                topBar = {


                    Column(
                        modifier = Modifier.systemBarsPadding(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            IconButton(
                                modifier = Modifier.align(Alignment.CenterStart),
                                onClick = {
                                    scope.launch { drawerState.open() }
                                },
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(start = 10.dp),
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = null,
                                    tint = Color.White.copy(0.7f)

                                )
                            }

                            Text(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .align(Alignment.Center),
                                text = "CRUD Notes",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,

                                )
                        }

                        TopBar(searchQuery, {searchQuery = it})
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            navHostController.navigate(Routes.UpsertNotesScreen + "/0")
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

                if(loading) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                    ) {
                        CircularProgressIndicator(modifier = Modifier
                            .align(Alignment.Center)
                            .size(30.dp))
                    }
                }
                else {

                    LazyColumn(
                        modifier = modifier
                            .padding(innerpadding)
                            .padding(10.dp)
                    ) {

                        if(filteredNotes != null) {

                            items(filteredNotes) {
                                SingleNote(note = it,notesDBRef, navHostController)
                                Spacer(modifier = Modifier.padding(10.dp))
                            }
                        }
                    }
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
fun SingleNote(note: Note, notesDBRef : CollectionReference, navHostController: NavHostController) {

    var isPinned = note.priority == 1
    
    var showDialog by remember {
        mutableStateOf(false)
    }

    var id = ""
    
    if(showDialog) {
        AlertDialog(
            containerColor = Color.DarkGray,
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Do you want to delete this note?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            confirmButton = { 
                TextButton(
                    onClick = {

                        notesDBRef.whereEqualTo("id", note.id).get().addOnSuccessListener { query ->
                            id = query.documents[0].id
                            notesDBRef.document(id).delete()
                                .addOnSuccessListener {
                                    Log.d("DeleteNote", "Note successfully deleted with ID: ${id}")
                                }
                                .addOnFailureListener { exception ->
                                    Log.e("DeleteNote", "Error deleting note: ${exception.message}", exception)
                                }
                        }

                        showDialog = false
                    }
                ) {
                    Text(text = "Delete", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "Cancel", color = Color.White)
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(10.dp)

    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Menu",
                tint = Color.White.copy(0.7f),
                modifier = Modifier
                    .clickable {
                        navHostController.navigate(Routes.UpsertNotesScreen + "/${note.id}")
                    }
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White.copy(0.7f),
                modifier = Modifier
                    .clickable {
                        showDialog = true
                    }
            )

            Spacer(modifier = Modifier.padding(10.dp))

//            Icon(
//                imageVector =
//                    if(!isPinned) Icons.Default.PushPin
//                    else ImageVector.vectorResource(R.drawable.noun_unpin_1713712)
//                ,
//                contentDescription = null,
//                tint = Color.White.copy(0.7f),
//                modifier = Modifier
//                    .clickable {
//                        onPin(note.id, isPinned)
//                    }
//            )
        }

    }

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                when (note.randomInt) {
                    1 -> Color1
                    2 -> Color2
                    3 -> Color3
                    4 -> Color4
                    5 -> Color5
                    6 -> Color6
                    else -> ColorGrey
                }
            )
            .padding(10.dp)
            .clickable { navHostController.navigate(Routes.UpsertNotesScreen + "/${note.id}") }
    ) {



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