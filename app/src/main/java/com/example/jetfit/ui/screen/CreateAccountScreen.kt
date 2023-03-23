package com.example.jetfit.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun CreateAccountScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    val auth: FirebaseAuth = Firebase.auth


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (emailField, passwordField, confirmPasswordField, createAccountButton) = createRefs()


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.constrainAs(emailField) {
                top.linkTo(parent.top, 24.dp)
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

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier.constrainAs(confirmPasswordField) {
                top.linkTo(passwordField.bottom, 24.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
            },
            placeholder = { Text("Confirm Password") },
            label = { Text(text = "Confirm Password") }
        )

        OutlinedButton(
            onClick = {
                if (password.length > 7 && password == confirmPassword) {
                    auth.createUserWithEmailAndPassword(email, password)
                    Toast.makeText(
                        context,
                        "Success, you have made an account!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Password is either too short or does not match", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .constrainAs(createAccountButton) {
                    top.linkTo(confirmPasswordField.bottom, 40.dp)
                    start.linkTo(parent.start, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .width(250.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) {
            Text(text = "Create Account", color = Color.White, fontSize = 24.sp)
        }


    }
}

