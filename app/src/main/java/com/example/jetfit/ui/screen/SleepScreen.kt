package com.example.jetfit.ui.screen

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.jetfit.data.usersleep.UserSleep
import com.example.jetfit.data.usersleep.UserSleepViewModel
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
fun SleepScreen(userSleepViewModel: UserSleepViewModel = viewModel()) {
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
        var getAllUserSleep by rememberSaveable { mutableStateOf(listOf<UserSleep>()) }
        val auth = Firebase.auth.currentUser

        LaunchedEffect(Unit) {
            withContext(Dispatchers.Main) {
                userSleepViewModel.getAllUserSleep.observeForever { userSleep ->
                    getAllUserSleep = userSleep.filter { it.uid == auth!!.uid }
                }
            }
        }

        Box(modifier = Modifier
            .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            LazyColumn(modifier = Modifier.padding(bottom = 80.dp)) {
                items(
                    items = getAllUserSleep,
                    key = { item: UserSleep -> item.id }
                ) { userSleep ->
                    SleepCard(
                        userSleep = userSleep,
                        deleteUserSleep = { userSleepViewModel.deleteUserSleep(userSleep) }
                    )
                }
            }
            AddSleepAndDateDialog(
                showDialog = showDialog,
                onExitDialog = { showDialog = !showDialog },
                userSleepViewModel = userSleepViewModel
            )
        }

    }
}

@Composable
fun AddSleepAndDateDialog(
    showDialog: Boolean,
    onExitDialog: () -> Unit,
    userSleepViewModel: UserSleepViewModel
) {

    var sleep by remember { mutableStateOf("") }
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
                sleep = ""
                mDate.value = ""
                onExitDialog.invoke()
            },
            confirmButton = {
                TextButton(onClick = {
                    if (mDate.value == "" || sleep == "") {
                        Toast.makeText(
                            mContext,
                            "One or more field were left empty",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        coroutineScope.launch(Dispatchers.IO) {
                            if (auth != null) {
                                userSleepViewModel.addUserSleep(
                                    userSleep = UserSleep(
                                        uid = auth.uid,
                                        date = mDate.value,
                                        hoursOfSleep = sleep
                                    )
                                )
                            }

                            sleep = ""
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
                        sleep = ""
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
                        value = sleep,
                        onValueChange = { sleep = it },
                        singleLine = true,
                        label = { Text(text = "sleep") },
                        placeholder = { Text(text = "sleep") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                }
            }
        )
    }
}

@Composable
fun SleepCard(
    modifier: Modifier = Modifier,
    userSleep: UserSleep,
    deleteUserSleep: () -> Unit
) {
    Card (
        modifier = modifier
            .padding(12.dp)
            .height(80.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        SleepCardContent(
            date = userSleep.date,
            sleep = userSleep.hoursOfSleep,
            deleteUserSleep = deleteUserSleep
        )
    }
}

@Composable
fun SleepCardContent(
    date: String,
    sleep: String,
    deleteUserSleep: () -> Unit
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
                text = "Hours of Sleep: $sleep",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Icon",
            modifier = Modifier
                .clickable { deleteUserSleep.invoke() }
                .size(30.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}