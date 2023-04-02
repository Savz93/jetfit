package com.example.jetfit.ui.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.MainViewModel
import com.example.jetfit.User
import com.example.jetfit.ui.Colors
import com.example.jetfit.userdata.UserViewModel
import com.example.jetfit.userweightdata.UserWeight
import com.example.jetfit.userweightdata.UserWeightViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeightScreen(
    userViewModel: UserViewModel,
    userWeightViewModel: UserWeightViewModel,
    mainViewModel: MainViewModel,
    navController: NavController
    ) {

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold (
        scaffoldState = scaffoldState,
        backgroundColor = Color.White,
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    mainViewModel.onOpenDialogClicked()
                    navController.navigate(Screen.AlertDialogAddWeight.route)
                },
                shape = CircleShape,
                modifier = Modifier.size(40.dp)) {
                Icon(
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = Icons.Default.Add,
                    tint = Colors.middleBlue,
                    contentDescription = "Add Weight")
            }

        },
        content = { WeightScreenContent(userWeightViewModel) }
    )

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun addWeightAndDate(
    mainViewModel: MainViewModel,
    userWeightViewModel: UserWeightViewModel,
    navController: NavController
) {

    val auth = Firebase.auth.currentUser
    var date by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { navController.navigate(Screen.WeightScreen.route) },
        dismissButton = {
            TextButton(onClick = { navController.navigate(Screen.WeightScreen.route) }) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                userWeightViewModel
                    .insertUserWeight(
                        UserWeight(
                            uid = auth?.uid ?: "123",
                            date = date,
                            weight = weight
                        )
                )

                Toast.makeText(context, "${userWeightViewModel.allWeights.value}", Toast.LENGTH_SHORT).show()

                navController.navigate(Screen.WeightScreen.route)
            }) {
                Text(
                    text = "Add",
                    modifier = Modifier)
            }
        },
        backgroundColor = Color.DarkGray,
        shape = RoundedCornerShape(8.dp),
        title = {
            Text(
                text = "Add Date and Weight",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        },
        text = {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    value = date,
                    onValueChange = { date = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color.White,
                        textColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    ),
                    singleLine = true,
                    placeholder = { Text(text = "date") },
                    label = { Text(text = "date") }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    value = weight,
                    onValueChange = { weight = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color.White,
                        textColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    ),
                    singleLine = true,
                    placeholder = { Text(text = "weight") },
                    label = { Text(text = "weight") }
                    )
            }
        }
    )

}

@Composable
fun WeightScreenContent(
    userWeightViewModel: UserWeightViewModel
) {

    val context = LocalContext.current
    val userWeights = userWeightViewModel.allWeights.value
    val userWeightsDummyData = listOf(
        UserWeight (
            uid = "1",
            date = "02/24/2023",
            weight = "165"
        ),
        UserWeight (
            uid = "2",
            date = "02/25/2023",
            weight = "166"
        ),
        UserWeight (
            uid = "3",
            date = "02/28/2023",
            weight = "170"
        ),
        UserWeight (
            uid = "4",
            date = "03/02/2023",
            weight = "165"
        ),
        UserWeight (
            uid = "5",
            date = "03/05/2023",
            weight = "171"
        ),
        UserWeight (
            uid = "6",
            date = "03/15/2023",
            weight = "168"
        ),
        UserWeight (
            uid = "7",
            date = "03/24/2023",
            weight = "162"
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Card (
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.LightGray,
            elevation = 8.dp
                ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Weight Graph",
                    fontSize = 40.sp
                )
            }
        }

        LazyColumn (
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize()
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Toast.makeText(context, "$userWeights", Toast.LENGTH_SHORT).show()

            items(userWeights ?: userWeightsDummyData) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    backgroundColor = Color.LightGray,
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = it.date,
                            fontSize = 24.sp,
                        )

                        Text(
                            modifier = Modifier.padding(end = 16.dp),
                            text = it.weight,
                            fontSize = 20.sp
                        )
                    }
                }
            }


        }
    }

}

@Preview(showBackground = true)
@Composable
fun WeightScreenPreview() {
    WeightScreen(viewModel(), viewModel(), viewModel(), rememberNavController())
}