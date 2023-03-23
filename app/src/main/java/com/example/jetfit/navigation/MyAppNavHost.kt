package com.example.jetfit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetfit.ui.screen.Screen
import com.example.jetfit.ui.screen.CreateAccountScreen
import com.example.jetfit.ui.screen.LoginScreen

@Composable
fun MyAppNavHost(
    navController: NavHostController
    ) {

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.LoginScreen.route) { LoginScreen(navController) }
        composable(Screen.CreateAccountScreen.route) { CreateAccountScreen() }
    }

}

