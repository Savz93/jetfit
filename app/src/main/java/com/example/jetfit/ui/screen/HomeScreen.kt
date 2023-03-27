package com.example.jetfit.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material.icons.filled.NoFood
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.R
import com.example.jetfit.ui.Colors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController) {

    val scaffoldState = rememberScaffoldState(
    rememberDrawerState(
        DrawerValue.Open
    ))

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(
            modifier = Modifier,
            title = { HomeScreenTitle() },
            backgroundColor = Colors.middleBlue,
            contentColor = Color.White,
            elevation = 16.dp
        )},
        content = { HomeScreenContent(user = "Adam", navController = navController) },
        bottomBar = { BottomAppBar(backgroundColor = Colors.middleBlue) {

        } }
        )
}

@Composable
fun HomeScreenTitle() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Home", fontSize = 30.sp)
    }
}
@Composable
fun HomeScreenContent(
    user: String?,
    navController: NavController
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(Colors.lightBlue, Colors.middleBlue)
                )
            )
    ) {
        val (uid, cardLayout) = createRefs()

        Text(
            modifier = Modifier.constrainAs(uid) {
                top.linkTo(parent.top, 140.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = "Hello, $user",
            fontSize = 24.sp,
            color = Color.Black
        )

        Column(
            modifier = Modifier.constrainAs(cardLayout) {
                top.linkTo(uid.bottom, 24.dp)
                start.linkTo(parent.start, 24.dp)
                end.linkTo(parent.end, 24.dp)
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .height(150.dp),
                    backgroundColor = Color.LightGray,
                    shape = RoundedCornerShape(8.dp),
                    contentColor = Color.Black,
                    elevation = 8.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Nutrition",
                            fontSize = 30.sp,
                            color = Colors.darkBlue,
                            fontWeight = FontWeight.Normal
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Icon(
                            painter = painterResource(id = R.drawable.nutrition_icon),
                            contentDescription = "Food Icon",
                            modifier = Modifier.size(60.dp),
                            tint = Colors.darkBlue
                            )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .height(150.dp),
                    backgroundColor = Color.LightGray,
                    shape = RoundedCornerShape(8.dp),
                    contentColor = Color.Black,
                    elevation = 8.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Exercise",
                            fontSize = 30.sp,
                            color = Colors.darkBlue,
                            fontWeight = FontWeight.Normal
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.exercise_icon),
                            contentDescription = "Exercise Icon",
                            modifier = Modifier.size(60.dp),
                            tint = Colors.darkBlue
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .height(150.dp),
                    backgroundColor = Color.LightGray,
                    shape = RoundedCornerShape(8.dp),
                    contentColor = Color.Black,
                    elevation = 8.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Sleep",
                            fontSize = 30.sp,
                            color = Colors.darkBlue,
                            fontWeight = FontWeight.Normal
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.sleep_icon),
                            contentDescription = "Sleep Icon",
                            modifier = Modifier.size(60.dp),
                            tint = Colors.darkBlue
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .height(150.dp),
                    backgroundColor = Color.LightGray,
                    shape = RoundedCornerShape(8.dp),
                    contentColor = Color.Black,
                    elevation = 8.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.WeightScreen.route)
                        }
                    ) {
                        Text(
                            text = "Weight",
                            fontSize = 30.sp,
                            color = Colors.darkBlue,
                            fontWeight = FontWeight.Normal
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.weight_icon),
                            contentDescription = "Weight Icon",
                            modifier = Modifier.size(60.dp),
                            tint = Colors.darkBlue
                        )
                    }
                }
            }
        }
    }




}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}