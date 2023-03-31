package com.example.jetfit.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetfit.User
import com.example.jetfit.userdata.UserViewModel
import com.example.jetfit.ui.Colors
import com.example.jetfit.userweightdata.UserWeight
import com.example.jetfit.userweightdata.UserWeightViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeightScreen(
    userViewModel: UserViewModel,
    userWeightViewModel: UserWeightViewModel
    ) {

    val scaffoldState = rememberScaffoldState()

    Scaffold (
        scaffoldState = scaffoldState,
        backgroundColor = Color.White,
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            Icon(
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp),
                imageVector = Icons.Default.AddCircle,
                tint = Colors.middleBlue,
                contentDescription = "Add Weight")
        },
        content = { WeightScreenContent(userViewModel, userWeightViewModel) }
    )

}

@Composable
fun WeightScreenContent(
    userViewModel: UserViewModel,
    userWeightViewModel: UserWeightViewModel
) {

    val auth = Firebase.auth.currentUser

    userWeightViewModel
        .insertUserWeight(
            UserWeight(
                uid = auth!!.uid,
                weight = 16,
                date = "12/24/2022"
            )
        )

    userWeightViewModel
        .insertUserWeight(
            UserWeight(
                uid = auth!!.uid,
                weight = 12,
                date = "01/24/2023"
            )
        )

    userWeightViewModel
        .insertUserWeight(
            UserWeight(
                uid = auth!!.uid,
                weight = 14,
                date = "02/24/2023"
            )
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
            Text(
                text = "Hello",
                fontSize = 40.sp
            )
        }

        LazyColumn (
            modifier = Modifier
                .weight(0.5f)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            val userWeights = userWeightViewModel.allWeights

            if (userWeights.value != null) {
                items(userWeights.value!!) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp),
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
                                fontSize = 30.sp,
                            )

                            Text(
                                modifier = Modifier.padding(end = 16.dp),
                                text = it.weight.toString(),
                                fontSize = 24.sp
                            )
                        }
                    }
                }

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun WeightScreenPreview() {
    WeightScreen(viewModel(), viewModel())
}