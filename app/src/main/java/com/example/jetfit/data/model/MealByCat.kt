package com.example.jetfit.data.model

data class MealByCat(
    var meals: List<MealByCategory>
)

data class MealByCategory(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
)
