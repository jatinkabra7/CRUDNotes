package com.jatinkabra.crudnotes.screens

import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jatinkabra.crudnotes.navigation.Routes
import com.jatinkabra.crudnotes.viewmodel.AuthState
import com.jatinkabra.crudnotes.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navHostController: NavHostController, authViewModel: AuthViewModel) {

    val authState = authViewModel.authState.observeAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Text(text = "CRUD Notes", fontSize = 50.sp, fontWeight = FontWeight.Bold, color = Color.White)

        LaunchedEffect(key1 = authState.value) {


            when(authState.value) {
                is AuthState.Authenticated -> {
                    delay(2500)
                    navHostController.navigate(Routes.NotesScreen) {
                        popUpTo(Routes.SplashScreen) {inclusive = true}
                    }
                }
                else -> {
                    delay(2500)
                    navHostController.navigate(Routes.LoginScreen) {
                        popUpTo(Routes.SplashScreen) {inclusive = true}
                    }
                }
            }
        }
    }
}