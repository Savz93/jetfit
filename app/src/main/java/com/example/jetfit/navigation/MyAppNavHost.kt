package com.example.jetfit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetfit.ui.screen.Screen
import com.example.jetfit.ui.screen.CreateAccountScreen
import com.example.jetfit.ui.screen.HomeScreen
import com.example.jetfit.ui.screen.LoginScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MyAppNavHost(
    navController: NavHostController
    ) {

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route) { LoginScreen(navController) }
        composable(Screen.CreateAccountScreen.route) { CreateAccountScreen(navController) }
        composable(
            route = Screen.HomeScreen.route + "/{user}",
            arguments = listOf(
                navArgument("user") {
                type = NavType.StringType
                nullable = true
                }
            )
        ) { user ->
            HomeScreen(user = user.arguments?.getString("user")) }
    }

}

