package com.example.jetfit.data.favoritemeal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meal")
data class FavoriteMeal(
    @PrimaryKey
    val idMeal: String = "",
    val strMeal: String = "",
    val strMealThumb: String =  "",
    val favorite: Boolean = false
)
