package com.example.jetfit.userdata

import androidx.room.PrimaryKey

data class UserWeightDataFireStore(
    val uid: String = "",
    val weight: String = "",
    val date: String = ""
)
