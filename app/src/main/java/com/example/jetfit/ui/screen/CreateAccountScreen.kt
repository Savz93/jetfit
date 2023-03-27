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
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.ui.Colors
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CreateAccountScreen(
    navController: NavController
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val focusManager = LocalFocusManager.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(Colors.middleBlue, Colors.darkBlue)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {

        Spacer(modifier = Modifier.height(100.dp))

        // email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
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


        // password field
        OutlinedTextField(
            modifier = Modifier.onFocusChanged {
                if (it.isFocused) {
                    passwordFocused = !passwordFocused
                }
            },
            value = password,
            onValueChange = { password = it },
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

        // password rules
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 60.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = "Rounded circle icon",
                modifier = Modifier.padding(top = 3f.dp).size(14.dp),
                tint = if (password.length > 7) Colors.green else Color.White
                )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                modifier = Modifier,
                text = "8 or more characters",
                color = if (password.length > 7) Colors.green else Color.White,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
                )
        }


        Spacer(modifier = Modifier.height(20.dp))

        // Confirm password field
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

        // passwords match
        if (confirmPassword.trim() == password.trim() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 60.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "Rounded circle icon",
                    modifier = Modifier.padding(top = 3f.dp).size(14.dp),
                    tint = Colors.green
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    modifier = Modifier,
                    text = "passwords match",
                    color = Colors.green,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Create Account button
        OutlinedButton(
            onClick = {
                val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

                if (password.trim().length > 7 && password.trim() == confirmPassword.trim() && emailRegex.toRegex().matches(email.trim())) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate(Screen.HomeScreen.route)
                                Log.d("TAG", "createUserWithEmail:success")
                            } else {
                                Log.w("TAG", task.exception.toString())
                            }
                        }
                } else {
                    if (password.trim().length <= 7) {
                        Toast.makeText(context, "Password not long enough", Toast.LENGTH_SHORT).show()
                    } else if (password.trim() != confirmPassword.trim()) {
                        Toast.makeText(context, "Passwords are not the same", Toast.LENGTH_SHORT).show()
                    } else if (!emailRegex.toRegex().matches(email.trim())) {
                        Toast.makeText(context, "Email is not formatted correctly", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Unknown Error", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 60.dp, end = 60.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.middleBlue)
        ) {
            Text(text = "Create Account", color = Color.White, fontSize = 24.sp)
        }

    }
}

fun passwordRules(email: String, password: String, confirmPassword: String, auth: FirebaseAuth) {

}

@Preview(showBackground = true)
@Composable
fun PreviewCreateAccountScreen() {
    CreateAccountScreen(rememberNavController())
}