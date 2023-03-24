package com.example.jetfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.navigation.MyAppNavHost
import com.example.jetfit.ui.screen.LoginScreen
import com.example.jetfit.ui.theme.JetFitTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetFitTheme {
                navController = rememberNavController()
                MyApp(navController)
            }
        }

//        WindowCompat.setDecorFitsSystemWindows(window, false)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
//            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
//            view.updatePadding(bottom = bottom)
//            insets
//        }

    }
}

@Composable
fun MyApp(navHostController: NavHostController) {
    MyAppNavHost(navHostController)
}