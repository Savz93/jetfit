package com.example.jetfit.data.mealapi

import com.example.jetfit.data.model.MealByCat
import com.example.jetfit.data.model.MealCategory
import com.example.jetfit.data.model.MealsById
import retrofit2.http.GET
import retrofit2.http.Url

interface MealApi {

    @GET(MealApiConstants.LIST_OF_CATEGORIES_ENDPOINT)
    suspend fun getMealCategories(): MealCategory

    @GET
    suspend fun getMealById(@Url fullUrl: String): MealsById

    @GET
    suspend fun getMealByCategory(@Url fullUrl: String): MealByCat
}