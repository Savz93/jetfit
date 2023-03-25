package com.example.jetfit.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.jetfit.ui.Colors

@Composable
fun HomeScreen(user: String?) {
    
    
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
            brush = Brush.linearGradient(
                listOf(Colors.lightBlue, Colors.middleBlue))
        )
    ) {
        val (uid, card) = createRefs()
        
        Text(
            modifier = Modifier.constrainAs(uid) {
                top.linkTo(parent.top, 100.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = "Hello, $user",
            fontSize = 24.sp,
            color = Color.Black
            )
        
        Card(
            modifier = Modifier.constrainAs(card) {
                top.linkTo(uid.bottom, 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
                .width(220.dp)
                .height(150.dp),
            backgroundColor = Color.LightGray,
            shape = RoundedCornerShape(8.dp),
            contentColor = Color.Black
            ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Email")
                Text(text = "********")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(user = "Adam")
}