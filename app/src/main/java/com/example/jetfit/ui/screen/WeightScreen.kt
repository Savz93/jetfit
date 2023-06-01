package com.example.jetfit.ui.screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.jetfit.data.userweight.UserWeightViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.jetfit.data.userweight.UserWeight
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightScreen(userWeightViewModel: UserWeightViewModel = viewModel()) {
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
        var getAllUserWeights by rememberSaveable { mutableStateOf(listOf<UserWeight>()) }
        val auth = Firebase.auth.currentUser

        LaunchedEffect(Unit) {
            withContext(Dispatchers.Main) {
                userWeightViewModel.getAllUserWeight.observeForever { userWeights ->
                    getAllUserWeights = userWeights.filter { it.uid == auth!!.uid }
                }
            }
        }

        Box(modifier = Modifier
            .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            LazyColumn {
                items(
                    items = getAllUserWeights,
                    key = { item: UserWeight -> item.id }
                ) {userWeight ->
                    WeightCard(
                        userWeight = userWeight,
                        deleteUserWeight = { userWeightViewModel.deleteUserWeight(userWeight) }
                    )
                }
            }
            AddWeightAndDateDialog(
                showDialog = showDialog,
                onExitDialog = { showDialog = !showDialog },
                userWeightViewModel = userWeightViewModel
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWeightAndDateDialog(
    showDialog: Boolean,
    onExitDialog: () -> Unit,
    userWeightViewModel: UserWeightViewModel
) {

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
            mDate.value = "${mMonth+1}/$mDayOfMonth/$mYear"
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
                                userWeightViewModel.addUserWeight(
                                    userWeight = UserWeight(
                                        uid = auth.uid,
                                        date = mDate.value,
                                        weight = weight
                                    )
                                )
                            }

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
                        value = weight,
                        onValueChange = { weight = it },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                }
            }
        )
    }
}

@Composable
fun WeightCard(
    modifier: Modifier = Modifier,
    userWeight: UserWeight,
    deleteUserWeight: () -> Unit
) {
    Card (
        modifier = modifier
            .padding(12.dp)
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        WeightCardContent(
            date = userWeight.date,
            weight = userWeight.weight,
            deleteUserWeight = deleteUserWeight
        )
    }
}

@Composable
fun WeightCardContent(
    date: String,
    weight: String,
    deleteUserWeight: () -> Unit
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
                text = "Date: $date",
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
                .clickable { deleteUserWeight.invoke() }
                .size(30.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeightScreenPreview() {
    WeightScreen(viewModel())
}