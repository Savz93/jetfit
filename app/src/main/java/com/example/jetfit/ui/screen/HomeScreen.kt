package com.example.jetfit.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello, user", fontSize = 100.sp)
    }
}