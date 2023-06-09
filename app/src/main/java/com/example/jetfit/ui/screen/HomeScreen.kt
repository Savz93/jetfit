package com.example.jetfit.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.R
import com.example.jetfit.ui.Colors
import com.example.jetfit.data.userdata.UserDataFireStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController
) {

    val scaffoldState = rememberScaffoldState(
    rememberDrawerState(
        DrawerValue.Closed
    ))
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(
            modifier = Modifier,
            title = { HomeScreenTitle() },
            backgroundColor = Colors.middleBlue,
            contentColor = Color.White,
            elevation = 16.dp,
            navigationIcon = {
                IconButton(onClick = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu Icon",
                        tint = Color.White
                    )
                }
            }
        )},
        content = { HomeScreenContent(navController = navController) },
        bottomBar = { BottomAppBar(backgroundColor = Colors.middleBlue) {

        } },
        drawerContent = { DrawerContentHomeScreen(navController) },
        drawerShape = customShape(),
        drawerBackgroundColor = Color.LightGray,
        drawerElevation = 8.dp,
        drawerContentColor = Colors.darkBlue
        )
}

@Composable
fun DrawerContentHomeScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier.padding(start = 12.dp)
    ) {

        Row(
            modifier = Modifier.padding(start = 32.dp),
        ) {
            Text(
                text = "Menu",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings Icon")

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Settings",
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "About Icon")

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "About",
                fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.clickable {
                auth.signOut()
                navController.navigate(Screen.LoginScreen.route)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = "About Icon")

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Logout",
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun HomeScreenTitle() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 90.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Home",
            fontSize = 30.sp
        )
    }
}
@Composable
fun HomeScreenContent(
    navController: NavController
) {

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val auth = Firebase.auth.currentUser
    var firstName by remember { mutableStateOf("") }

    if (auth != null) {
        db.collection("Users").get().addOnSuccessListener { result ->
            for (document in result) {
                val users = document.toObject(UserDataFireStore::class.java)
                if (users.uid == auth.uid) {
                    firstName = users.firstName.toString()
                }
            }
        }.addOnFailureListener { e ->
            Log.w("TAG", "Error getting documents: $e")
        }
    }

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
            text = "Hello, $firstName",
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
                        .clickable { navController.navigate(Screen.NutritionScreen.route) }
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
                        .clickable { navController.navigate(Screen.ExerciseScreen.route) }
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
                        .clickable { navController.navigate(Screen.SleepScreen.route) }
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

fun customShape() =  object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(Rect(0f,0f,500f, 600f))
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen( rememberNavController())
}