package com.example.jetfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfit.data.MealRepository
import com.example.jetfit.data.mealapi.MealApiConstants
import com.example.jetfit.data.model.MealByCat
import com.example.jetfit.data.model.MealByCategory
import com.example.jetfit.data.model.MealsById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mealRepository: MealRepository): ViewModel() {

    private val _mealByCategoryState = MutableStateFlow(mutableListOf<MealByCategory>())
    val mealByCategoryState: StateFlow<MutableList<MealByCategory>>
        get() = _mealByCategoryState

    private var _meal = MutableStateFlow(MealsById(emptyList()))
    val meal: StateFlow<MealsById>
        get() = _meal

    init {
        viewModelScope.launch {
            val categories = mealRepository.getMealCategories()
            var mealsByCategory: MealByCat
            val allMeals = mutableListOf<MealByCategory>()

            categories.meals.forEach {
                mealsByCategory = mealRepository
                    .getMealByCategory("${MealApiConstants.BASE_URL}${MealApiConstants.FILTER_BY_CATEGORY}${it.strCategory}")

                mealsByCategory.meals.forEach { meal ->
                    allMeals.add(meal)
                }
            }

            _mealByCategoryState.value = allMeals
        }
    }

    fun searchForMeal(search: String, listOfMeals: List<MealByCategory>): List<MealByCategory> {
        return listOfMeals.filter { it.strMeal.lowercase().contains(search.lowercase()) }
    }

    fun getMealById(id: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _meal.value = mealRepository.getMealById("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id")
        }
    }

}

