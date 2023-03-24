package com.example.jetfit.ui.screen

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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.ui.Colors
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val focusManager = LocalFocusManager.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(Colors.lightBlue, Colors.middleBlue)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(140.dp))

        Text(
            text = "JetFit",
            fontSize = 64.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.height(100.dp))

        OutlinedTextField(
            modifier = Modifier
                .onFocusEvent {
                if (it.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
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


        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                }
                .padding(bottom = 16.dp),
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

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedButton(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            onClick = {
                Toast
                    .makeText(context, "You Clicked Login Button!", Toast.LENGTH_SHORT)
                    .show()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ) {
            Text(text = "Login", color = Color.LightGray, fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.CreateAccountScreen.route)
                },
            text ="Create Account",
            fontSize = 20.sp,
            color = Color.LightGray
        )

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}

