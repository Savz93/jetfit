package com.example.jetfit.ui.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.MainViewModel
import com.example.jetfit.ui.Colors
import com.example.jetfit.userdata.UserWeightDataFireStore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.random.Random


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeightScreen(
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
        content = { WeightScreenContent() }
    )

}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun addWeightAndDate(
    mainViewModel: MainViewModel,
    navController: NavController
) {

    val auth = Firebase.auth.currentUser
    var weight by remember { mutableStateOf("") }
    val mContext = LocalContext.current

    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val userWeightDB: CollectionReference = db.collection("UserWeight")

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYearVal: Int, mMonthVal: Int, mDayOfMonth: Int ->
            mDate.value = "${mMonthVal + 1}/$mDayOfMonth/$mYearVal"
        }, mYear, mMonth, mDay
    )

    AlertDialog(
        onDismissRequest = { navController.navigate(Screen.WeightScreen.route) },
        dismissButton = {
            TextButton(
                onClick = { navController.navigate(Screen.WeightScreen.route) }) {
                Text(
                    text = "Cancel",
                    color = Colors.lightBlue
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {

                    val currentUserWeight = UserWeightDataFireStore (
                        uid = auth!!.uid,
                        weight = weight,
                        date = mDate.value
                    )

                    userWeightDB.add(currentUserWeight).addOnSuccessListener {
                        Toast.makeText(
                            mContext,
                            "UserWeight has been added to firestore",
                            Toast.LENGTH_SHORT
                        ).show()
                    }.addOnFailureListener { e ->
                        Log.i("TAG", "Failed to add userWeight to firestore \n$e")
                        Toast.makeText(
                            mContext,
                            "Fail to add userWeight \n$e", Toast.LENGTH_SHORT
                        ).show()
                    }

                navController.navigate(Screen.WeightScreen.route)
            }) {
                Text(
                    text = "Add",
                    modifier = Modifier,
                    color = Colors.lightBlue
                )
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier,
                        onClick = {
                            mDatePickerDialog.show()
                    },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.lightBlue)
                    ) {
                        Icon(
                            modifier = Modifier,
                            imageVector = Icons.Default.EditCalendar,
                            contentDescription = "Calendar Icon",
                            tint = Colors.darkBlue
                        )

                    }

                    Text(
                        text = "Date: ${mDate.value}",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    value = weight,
                    onValueChange = { weight = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
fun Graph(
    modifier : Modifier,
    xValues: List<Int>,
    yValues: List<Int>,
    points: List<Float>,
    paddingSpace: Dp,
    verticalStep: Int
) {

    val controlPoints1 = mutableListOf<PointF>()
    val controlPoints2 = mutableListOf<PointF>()
    val coordinates = mutableListOf<PointF>()
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 8.dp, 12.dp),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
            val yAxisSpace = size.height / yValues.size

            for (i in xValues.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${xValues[i]}",
                    xAxisSpace * (i + 1),
                    size.height - 30,
                    textPaint
                )
            }

            for (i in yValues.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${yValues[i]}",
                    paddingSpace.toPx() / 2f,
                    size.height - yAxisSpace * (i + 1),
                    textPaint
                )
            }

            for (i in points.indices) {
                val x1 = xAxisSpace * xValues[i]
                val y1 = size.height - (yAxisSpace * (points[i] / verticalStep.toFloat()))
                coordinates.add(PointF(x1, y1))

                drawCircle(
                    color = Colors.middleBlue,
                    radius = 10f,
                    center = Offset(x1, y1)
                )
            }

            for (i in 1 until coordinates.size) {
                controlPoints1.add(PointF((coordinates[i].x + coordinates[i- 1].x) / 2, coordinates[i -1].y))
                controlPoints2.add(PointF((coordinates[i].x + coordinates[i- 1].x) / 2, coordinates[i].y))
            }

            val stroke = Path().apply {
                reset()
                moveTo(coordinates.first().x, coordinates.first().y)
                for (i in 0 until coordinates.size - 1) {
                    cubicTo(
                        controlPoints1[i].x, controlPoints1[i].y,
                        controlPoints2[i].x, controlPoints2[i].y,
                        coordinates[i + 1].x, coordinates[i + 1].y
                    )
                }
            }

            val fillPath = android.graphics.Path(stroke.asAndroidPath())
                .asComposePath()
                .apply {
                    lineTo(xAxisSpace * xValues.last(), size.height - yAxisSpace)
                    lineTo(xAxisSpace, size.height - yAxisSpace)
                    close()
                }

            drawPath(
                fillPath,
                brush = Brush.verticalGradient(
                    listOf(
                        Colors.lightBlue,
                        Color.Transparent
                    ),
                    endY = size.height - yAxisSpace
                )
            )

            drawPath(
                stroke,
                color = Color.Black,
                style = Stroke(
                    width = 5f,
                    cap = StrokeCap.Round
                )
            )

        }
    }
}

@Composable
fun WeightScreenContent() {

    val dates = remember { mutableStateListOf("") }
    val weights = remember { mutableStateListOf("") }
    val datesAndWeights = remember { mutableMapOf<String, String>() }

    val context = LocalContext.current
    val auth = Firebase.auth.currentUser
    val db = FirebaseFirestore.getInstance()

    if (auth != null) {
        db.collection("UserWeight")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    val userWeight = document.toObject(UserWeightDataFireStore::class.java)
                    if (userWeight.uid == auth.uid) {
                        dates.add(userWeight.date)
                        weights.add(userWeight.weight)

                        datesAndWeights[userWeight.date] = userWeight.weight
                    }
                }

                dates.removeAt(0)
                weights.removeAt(0)

            }.addOnFailureListener { e ->
                Log.w("TAG", "Error getting documents: $e")
            }
    }


    val xValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
    val yValues = listOf(100, 120, 140, 160, 180, 200, 220, 240, 260)
    //    val points = listOf(150f,100f,250f,200f,330f,300f,90f,120f,285f,199f)
    val points = (0..11).map {
        var num = Random.nextInt(350)
        if (num <= 100)
            num += 50
        num.toFloat()
    }

    Log.i("TAG", "Points list: $points")

    if (points.isNotEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Card(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxSize(),
                shape = RoundedCornerShape(16.dp),
                backgroundColor = Color.LightGray,
                elevation = 8.dp
            ) {

                Graph(
                    modifier = Modifier,
                    xValues = xValues,
                    yValues = yValues,
                    points = points,
                    paddingSpace = 24.dp,
                    verticalStep = 50
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxSize()
                    .padding(bottom = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(dates.size) {
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
                                text = dates[it],
                                fontSize = 24.sp,
                            )

                            Text(
                                modifier = Modifier.padding(end = 16.dp),
                                text = weights[it],
                                fontSize = 20.sp
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
    WeightScreen(viewModel(), rememberNavController())
}