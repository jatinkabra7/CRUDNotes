package com.jatinkabra.crudnotes.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathSegment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jatinkabra.crudnotes.navigation.Routes
import com.jatinkabra.crudnotes.viewmodel.AuthState
import com.jatinkabra.crudnotes.viewmodel.AuthViewModel

@Composable
fun LoginScreen(modifier: Modifier = Modifier, authViewModel: AuthViewModel, navHostController: NavHostController) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value) {
            AuthState.Authenticated -> {
                navHostController.navigate(Routes.NotesScreen) {
                    popUpTo(Routes.LoginScreen) {inclusive = true}
                }
            }

            is AuthState.Error -> {
                Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "CRUD Notes - Login", fontWeight = FontWeight.Bold, fontSize = 30.sp, color = Color.White)

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White.copy(0.7f),
                focusedBorderColor = Color.White.copy(0.7f),
                unfocusedLabelColor = Color.White.copy(0.7f),
                focusedLabelColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White

            ),
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            )

        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White.copy(0.7f),
                focusedBorderColor = Color.White.copy(0.7f),
                unfocusedLabelColor = Color.White.copy(0.7f),
                focusedLabelColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White

            ),
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password (Atleast 6 length)") },
            keyboardOptions = KeyboardOptions(
                imeAction = androidx.compose.ui.text.input.ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {focusManager.clearFocus()}
            ),
            visualTransformation = if(passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if(passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector = image, contentDescription = null, tint = Color.White.copy(0.7f))
                }
            }
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                authViewModel.login(email,password)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
            )
        ) {
            Text(text = "Login", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        TextButton(onClick = {
            navHostController.navigate(Routes.SignupScreen)
        }) {
            Text(text = "Don't have an account? SignUp", color = Color.White)
        }

    }

}