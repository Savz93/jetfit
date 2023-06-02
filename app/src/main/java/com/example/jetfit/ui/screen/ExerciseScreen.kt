package com.example.jetfit.ui.screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetfit.data.userexercise.UserExercise
import com.example.jetfit.data.userexercise.UserExerciseViewModel
import com.example.jetfit.data.userweight.UserWeight
import com.example.jetfit.data.userweight.UserWeightViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

@Composable
fun ExerciseScreen(userExerciseViewModel: UserExerciseViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = !showDialog }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon",
                    modifier = Modifier.clip(CircleShape),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { innerPadding ->
        var getAllUserExercise by rememberSaveable { mutableStateOf(listOf<UserExercise>()) }
        val auth = Firebase.auth.currentUser

        LaunchedEffect(Unit) {
            withContext(Dispatchers.Main) {
                userExerciseViewModel.getAllUserExercise.observeForever { userExercise ->
                    getAllUserExercise = userExercise.filter { it.uid == auth!!.uid }
                }
            }
        }

        Box(modifier = Modifier
            .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            LazyColumn(modifier = Modifier.padding(bottom = 80.dp)) {
                items(
                    items = getAllUserExercise,
                    key = { item: UserExercise -> item.id }
                ) {userExercise ->
                    UserExerciseCard(
                        userExercise = userExercise,
                        deleteUserExercise = { userExerciseViewModel.deleteUserExercise(userExercise) }
                    )
                }
            }
            AddExerciseDialog(
                showDialog = showDialog,
                onExitDialog = { showDialog = !showDialog },
                userExerciseViewModel = userExerciseViewModel
            )
        }

    }
}

@Composable
fun AddExerciseDialog(
    showDialog: Boolean,
    onExitDialog: () -> Unit,
    userExerciseViewModel: UserExerciseViewModel
) {

    var exercise by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var reps by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    val mContext = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val auth = Firebase.auth.currentUser

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "${mMonth + 1}/$mDayOfMonth/$mYear"
        }, mYear, mMonth, mDay
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                weight = ""
                mDate.value = ""
                onExitDialog.invoke()
            },
            confirmButton = {
                TextButton(onClick = {
                    if (mDate.value == "" || weight == "") {
                        Toast.makeText(
                            mContext,
                            "One or more field were left empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        coroutineScope.launch(Dispatchers.IO) {
                            if (auth != null) {
                                userExerciseViewModel.addUserExercise(
                                    userExercise = UserExercise(
                                        uid = auth.uid,
                                        exercise = exercise,
                                        date = mDate.value,
                                        reps = reps,
                                        sets = sets,
                                        weight = weight
                                    )
                                )
                            }

                            exercise = ""
                            sets = ""
                            reps = ""
                            weight = ""
                            mDate.value = ""
                        }

                        onExitDialog.invoke()
                    }

                }
                ) {
                    Text(text = "Add")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        exercise = ""
                        sets = ""
                        reps = ""
                        weight = ""
                        mDate.value = ""
                        onExitDialog.invoke()
                    }
                ) {
                    Text(text = "Cancel")
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {

                    OutlinedTextField(
                        value = exercise,
                        onValueChange = { exercise = it },
                        singleLine = true,
                        placeholder = { Text(text = "exercise") },
                        label = { Text(text = "exercise") }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { mDatePickerDialog.show() }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Calendar Icon"
                            )
                        }

                        Text(
                            text = mDate.value,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    OutlinedTextField(
                        value = sets,
                        onValueChange = { sets = it },
                        singleLine = true,
                        placeholder = { Text(text = "number of sets") },
                        label = { Text(text = "sets") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = reps,
                        onValueChange = { reps = it },
                        singleLine = true,
                        placeholder = { Text(text = "number of reps") },
                        label = { Text(text = "reps") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = weight,
                        onValueChange = { weight = it },
                        singleLine = true,
                        placeholder = { Text(text = "weight") },
                        label = { Text(text = "weight") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                }
            }
        )
    }
}

@Composable
fun UserExerciseCard(
    modifier: Modifier = Modifier,
    userExercise: UserExercise,
    deleteUserExercise: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(12.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        ExerciseCardContent(
            exercise = userExercise.exercise,
            date = userExercise.date,
            sets = userExercise.sets,
            reps = userExercise.reps,
            weight = userExercise.weight,
            deleteUserExercise = deleteUserExercise
        )

    }
}

@Composable
fun ExerciseCardContent(
    exercise: String,
    date: String,
    sets: String,
    reps: String,
    weight: String,
    deleteUserExercise: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(
                text = "Exercise: $exercise",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Date: $date",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Sets: $sets",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "reps: $reps",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Weight: $weight",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Icon",
            modifier = Modifier
                .clickable { deleteUserExercise.invoke() }
                .size(30.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
