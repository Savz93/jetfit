package com.example.jetfit.data.favoritemeal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteMealDao {

    @Query("SELECT * FROM favorite_meal")
    fun getAllFavoriteMeal(): LiveData<List<FavoriteMeal>>

    @Query("SELECT * FROM favorite_meal WHERE idMeal = :idMeal")
    fun getMealById(idMeal: String): FavoriteMeal?

    @Insert
    suspend fun addFavoriteMeal(meal: FavoriteMeal)

    @Update
    suspend fun updateFavoriteMeal(meal: FavoriteMeal)

    @Delete
    suspend fun deleteFavoriteMeal(meal: FavoriteMeal)

    @Query("DELETE FROM favorite_meal")
    suspend fun deleteAllFavoriteMeal()

}