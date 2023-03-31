package com.example.jetfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.jetfit.navigation.MyAppNavHost
import com.example.jetfit.ui.theme.JetFitTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetFitTheme {
                navController = rememberAnimatedNavController()

                MyApp(navController)
            }
        }
    }

}

@Composable
fun MyApp(navHostController: NavHostController) {
    MyAppNavHost(navHostController)
}

