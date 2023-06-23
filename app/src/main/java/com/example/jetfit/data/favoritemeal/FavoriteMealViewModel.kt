package com.example.jetfit.data.favoritemeal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteMealViewModel(application: Application): AndroidViewModel(application) {

    var getAllFavoriteMeal: LiveData<List<FavoriteMeal>>
    private var repository: FavoriteMealRepository

    init {
        val favoriteMealDao = FavoriteMealDatabase.getInstance(application).favoriteMealDao()
        repository = FavoriteMealRepository(favoriteMealDao)
        getAllFavoriteMeal = repository.getAllFavoriteMeal
    }

    fun getMealById(id: String): FavoriteMeal? {
        return repository.getFavoriteMealById(id)
    }

    fun addFavoriteMeal(favoriteMeal: FavoriteMeal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavoriteMeal(favoriteMeal)
        }
    }

    fun updateFavoriteMeal(favoriteMeal: FavoriteMeal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteMeal(favoriteMeal)
        }
    }

    fun deleteFavoriteMeal(favoriteMeal: FavoriteMeal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteMeal(favoriteMeal)
        }
    }

    fun deleteAllFavoriteMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavoriteMeal()
        }
    }
}