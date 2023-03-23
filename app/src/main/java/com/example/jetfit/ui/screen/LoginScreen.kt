package com.example.jetfit.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.ui.Colors

@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(Colors.lightBlue, Colors.middleBlue, Colors.darkBlue))
            )) {
        val (appNameText, emailField, passwordField, loginButton, createAccount) = createRefs()

        Text(
            modifier = Modifier.constrainAs(appNameText) {
                top.linkTo(parent.top, margin = 160.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = "JetFit",
            fontSize = 64.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.constrainAs(emailField) {
                top.linkTo(appNameText.bottom, 200.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
            },
            placeholder = { Text(text = "Email") },
            label = { Text(text = "Email") })

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.constrainAs(passwordField) {
                top.linkTo(emailField.bottom, 24.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
            },
            placeholder = { Text("Password") },
            label = { Text(text = "Password") }
        )

        OutlinedButton(
            onClick = {
                Toast
                    .makeText(context, "You Clicked Login Button!", Toast.LENGTH_SHORT)
                    .show()
            },
            modifier = Modifier
                .constrainAs(loginButton) {
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(createAccount.top, 120.dp)
                }
                .width(250.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
            ) {
            Text(text = "Login", color = Color.White, fontSize = 24.sp)
        }

        Text(
            modifier = Modifier
                .constrainAs(createAccount) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 16.dp)
                }
                .clickable {
                    navController.navigate(Screen.CreateAccountScreen.route)
                },
            text ="Create Account",
            fontSize = 20.sp,
            color = Color.Blue
        )

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}

