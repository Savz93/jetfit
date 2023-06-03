package com.example.jetfit.data

import com.example.jetfit.data.mealapi.MealApi
import com.example.jetfit.data.model.MealByCat
import com.example.jetfit.data.model.MealByCategory
import com.example.jetfit.data.model.MealCategory
import retrofit2.Call
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val mealApi: MealApi
) {
    suspend fun getMealCategories(): MealCategory {
        return mealApi.getMealCategories()
    }

    suspend fun getMealByCategory(mealCategory: String): MealByCat {
        return mealApi.getMealByCategory(mealCategory)
    }
}