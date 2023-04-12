package com.example.jetfit.userdata

import androidx.room.PrimaryKey

data class UserDataFireStore(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val uid: String? = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val email: String? = "",

)
