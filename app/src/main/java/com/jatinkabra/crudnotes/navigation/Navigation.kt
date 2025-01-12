package com.jatinkabra.crudnotes.navigation

import android.window.SplashScreen
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jatinkabra.crudnotes.screens.AppInfo
import com.jatinkabra.crudnotes.screens.LoginScreen
import com.jatinkabra.crudnotes.screens.NotesScreen
import com.jatinkabra.crudnotes.screens.SignupScreen
import com.jatinkabra.crudnotes.screens.SplashScreen
import com.jatinkabra.crudnotes.screens.UpsertNotesScreen
import com.jatinkabra.crudnotes.viewmodel.AuthViewModel

@Composable
fun Navigation(navHostController: NavHostController) {

    NavHost(
        enterTransition = {slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))},
        exitTransition = {slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(500))},
        popEnterTransition = {slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))},
        popExitTransition = {slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(500))},
        navController = navHostController,
        startDestination = Routes.SplashScreen
    ) {

        composable(Routes.SplashScreen) {
            SplashScreen(navHostController = navHostController, authViewModel = AuthViewModel())
        }

        composable(Routes.LoginScreen) {
            LoginScreen(authViewModel = AuthViewModel(), navHostController = navHostController)
        }

        composable(Routes.SignupScreen) {
            SignupScreen(authViewModel = AuthViewModel(), navHostController = navHostController)
        }

        composable(Routes.NotesScreen) {
            NotesScreen(navHostController = navHostController, authViewModel = AuthViewModel())
        }

        composable(Routes.UpsertNotesScreen+"/{id}") {
            val id = it.arguments?.getString("id")
            if(id != null) {

                UpsertNotesScreen(id = id, navHostController = navHostController)
            }
        }

        composable(Routes.AppInfo) {
            AppInfo()
        }
    }
}