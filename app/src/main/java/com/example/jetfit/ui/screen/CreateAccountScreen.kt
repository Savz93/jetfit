package com.example.jetfit.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetfit.ui.Colors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun CreateAccountScreen() {

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val focusManager = LocalFocusManager.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(Colors.lightBlue, Colors.middleBlue)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Spacer(modifier = Modifier.height(200.dp))

        OutlinedTextField(
            value = userEmail,
            onValueChange = { userEmail = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next
                    )
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                textColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White
            ),
            placeholder = { Text(text = "email") },
            singleLine = true,
            label = { Text(text = "email") })

        Spacer(modifier = Modifier.height(24.dp))


        OutlinedTextField(
            value = userPassword,
            onValueChange = { userPassword = it },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next
                    )
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                textColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White
            ),
            singleLine = true,
            placeholder = { Text("password") },
            label = { Text(text = "password") },
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
                textColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.White
            ),
            singleLine = true,
            placeholder = { Text("confirm password") },
            label = { Text(text = "confirm password") },
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedButton(
            onClick = {
                if (userPassword.length > 7 && userPassword == confirmPassword && userEmail.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("TAG", "createUserWithEmail:success")
                                Toast.makeText(context, "Success, you have made an account!", Toast.LENGTH_SHORT).show()
                            } else {
                                Log.w("TAG", "${task.exception.toString()}")
                                Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Password is either too short or does not match", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) {
            Text(text = "Create Account", color = Color.White, fontSize = 24.sp)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateAccountScreen() {
    CreateAccountScreen()
}