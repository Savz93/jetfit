package com.example.jetfit.data.userexercise

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_exercise")
data class UserExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uid: String = "",
    val exercise: String = "",
    val date: String = "0",
    val sets: String = "",
    val reps: String = "",
    val weight: String = ""
)
