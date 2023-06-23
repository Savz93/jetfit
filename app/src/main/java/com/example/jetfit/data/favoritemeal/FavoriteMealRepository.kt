package com.example.jetfit.data.favoritemeal

import androidx.lifecycle.LiveData
import com.example.jetfit.data.userexercise.UserExercise

class FavoriteMealRepository(private val favoriteMealDao: FavoriteMealDao) {
    val getAllFavoriteMeal: LiveData<List<FavoriteMeal>> = favoriteMealDao.getAllFavoriteMeal()


    fun getFavoriteMealById(id: String) = favoriteMealDao.getMealById(id)
    suspend fun addFavoriteMeal(favoriteMeal: FavoriteMeal) = favoriteMealDao.addFavoriteMeal(favoriteMeal)
    suspend fun updateFavoriteMeal(favoriteMeal: FavoriteMeal) = favoriteMealDao.updateFavoriteMeal(favoriteMeal)
    suspend fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) = favoriteMealDao.deleteFavoriteMeal(favoriteMeal)
    suspend fun deleteAllFavoriteMeal() = favoriteMealDao.deleteAllFavoriteMeal()

}