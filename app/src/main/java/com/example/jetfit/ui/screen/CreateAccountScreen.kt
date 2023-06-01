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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.ui.Colors
import com.example.jetfit.data.userdata.UserDataFireStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun CreateAccountScreen(
    navController: NavController
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var passwordFocused by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val userDB: CollectionReference = db.collection("Users")

    val focusManager = LocalFocusManager.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(Colors.middleBlue, Colors.middleBlue)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Spacer(modifier = Modifier.height(80.dp))

        // First Name
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
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
            placeholder = { Text(text = "first name") },
            singleLine = true,
            label = { Text(text = "first name") }
        )

        // Last Name
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
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
            placeholder = { Text(text = "last name") },
            singleLine = true,
            label = { Text(text = "last name") }
        )

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
            label = { Text(text = "email") }
        )

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
                .padding(start = 60.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = "Rounded circle icon",
                modifier = Modifier
                    .padding(top = 3f.dp)
                    .size(14.dp),
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
                    .padding(start = 60.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "Rounded circle icon",
                    modifier = Modifier
                        .padding(top = 3f.dp)
                        .size(14.dp),
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

//        Spacer(modifier = Modifier.height(40.dp))

        // Create Account button
        OutlinedButton(
            onClick = {
                val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

                if (password.trim().length > 7 && password.trim() == confirmPassword.trim() && emailRegex.toRegex().matches(email.trim())) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                val currentUser = UserDataFireStore (
                                    firstName = firstName,
                                    lastName = lastName,
                                    uid = auth.uid!!,
                                    email = email
                                )

                                userDB.add(currentUser).addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "User has been added to firestore",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.addOnFailureListener { e ->
                                    Log.i("TAG", "Failed to add user to firestore \n$e")
                                    Toast.makeText(
                                        context,
                                        "Fail to add user \n$e", Toast.LENGTH_SHORT
                                    ).show()
                                }

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
                .padding(start = 60.dp, end = 60.dp, top = 32.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.darkBlue)
        ) {
            Text(text = "Create Account", color = Color.White, fontSize = 24.sp)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateAccountScreen() {
    CreateAccountScreen(rememberNavController())
}