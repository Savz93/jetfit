package com.example.jetfit.data.userweight

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_weight")
data class UserWeight(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String = "",
    val date: String = "",
    val weight: String = ""
)
