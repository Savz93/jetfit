package com.example.jetfit.data.usersleep

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_sleep")
data class UserSleep(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String = "",
    val hoursOfSleep: String = "",
    val date: String = ""

)
