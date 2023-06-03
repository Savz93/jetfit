package com.example.jetfit.data.model

import com.squareup.moshi.Json

data class MealCategory(
    @Json(name = "meals")
    val meals: List<MealName>
)

data class MealName(
    @Json(name = "strCategory")
    val strCategory: String
)