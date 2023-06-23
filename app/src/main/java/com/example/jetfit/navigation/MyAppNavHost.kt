package com.example.jetfit.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetfit.MainViewModel
import com.example.jetfit.data.userweight.UserWeightViewModel
import com.example.jetfit.ui.screen.*

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
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.WeightScreen.route) { WeightScreen() }
        composable(Screen.SleepScreen.route) { SleepScreen()}
        composable(Screen.ExerciseScreen.route) { ExerciseScreen() }
        composable(Screen.NutritionScreen.route) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            NutritionScreen(mainViewModel = mainViewModel, navController = navController)
        }
        composable("${Screen.NutritionDetailScreen.route}/{id}") {
            val mainViewModel = hiltViewModel<MainViewModel>()
            NutritionDetailScreen(id = it.arguments?.getString("id"), mainViewModel =  mainViewModel)
        }
    }

}

