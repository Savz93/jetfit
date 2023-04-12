package com.example.jetfit.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetfit.MainViewModel
import com.example.jetfit.ui.screen.*
import com.example.jetfit.userdata.UserViewModel
import com.example.jetfit.userweightdata.UserWeightViewModel
import com.google.accompanist.navigation.animation.AnimatedComposeNavigator
import com.google.accompanist.navigation.animation.AnimatedNavHost
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun MyAppNavHost(
    navController: NavHostController
    ) {

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route,
    ) {
        composable(route = Screen.LoginScreen.route) {
            val userViewModel = hiltViewModel<UserViewModel>()
            LoginScreen(navController, userViewModel) }
        composable(Screen.CreateAccountScreen.route) { backStackEntry ->
            val userViewModel = hiltViewModel<UserViewModel>()
            CreateAccountScreen(navController, userViewModel)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.WeightScreen.route) {
            val userViewModel = hiltViewModel<UserViewModel>()
            val userWeightViewModel = hiltViewModel<UserWeightViewModel>()
            val mainViewModel = hiltViewModel<MainViewModel>()
            WeightScreen(userViewModel, userWeightViewModel, mainViewModel, navController)
        }
        composable(Screen.AlertDialogAddWeight.route) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val userWeightViewModel = hiltViewModel<UserWeightViewModel>()
            addWeightAndDate(mainViewModel = mainViewModel, userWeightViewModel, navController)
        }
    }

}

