package com.example.jetfit.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.ui.Colors
import com.example.jetfit.userdata.UserViewModel
import com.google.android.gms.common.api.internal.LifecycleActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.ViewModelLifecycle
import kotlinx.coroutines.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UserViewModel
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val auth = Firebase.auth
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val focusManager = LocalFocusManager.current



    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(Colors.lightBlue, Colors.middleBlue)
                )
            )) {
        val (appText, emailField, passwordField, loginButton, createAccountText) = createRefs()

        // App Name Text
        Text(
            modifier = Modifier.constrainAs(appText) {
                top.linkTo(parent.top, 140.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = "JetFit",
            fontSize = 64.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        // Email Login
        OutlinedTextField(
            modifier = Modifier
                .onFocusEvent { event ->
                    if (event.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                }
                .constrainAs(emailField) {
                    top.linkTo(appText.bottom, 140.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Down
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
            placeholder = { Text(text = "email") },
            label = { Text(text = "email") }
        )

        // Password Login
        OutlinedTextField(
            modifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent { event ->
                    if (event.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                }
                .constrainAs(passwordField) {
                    top.linkTo(emailField.bottom, 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            value = password,
            onValueChange = { password = it },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.White,
                textColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.LightGray,
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

        // Button Login
        OutlinedButton(
            modifier = Modifier
                .constrainAs(loginButton) {
                    top.linkTo(passwordField.bottom, 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 80.dp, end = 80.dp),
            onClick = {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val currentUser = auth.currentUser

                        viewModel.findUserByUid(currentUser!!.uid)

                        viewModel.findUser.observeForever { user ->
                            navController.navigate(Screen.HomeScreen.withArgs(user.firstName))
                        }

                    } else {
                        Log.w("TAG", it.exception.toString())
                        Toast.makeText(context, "This email and password do not exist", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) {
            Text(text = "Login", color = Color.LightGray, fontSize = 24 .sp)
        }

        // Create Account Text
        Text(
            modifier = Modifier
                .constrainAs(createAccountText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 40.dp)
                }
                .clickable {
                    navController.navigate(Screen.CreateAccountScreen.route)
                },
            text = "Create Account",
            fontSize = 20.sp,
            color = Color.LightGray
        )


    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController(), viewModel = viewModel())
}

