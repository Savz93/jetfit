package com.example.jetfit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetfit.data.MealRepository
import com.example.jetfit.data.mealapi.MealApiConstants
import com.example.jetfit.data.model.MealByCat
import com.example.jetfit.data.model.MealByCategory
import com.example.jetfit.data.model.MealCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mealRepository: MealRepository): ViewModel() {

    private val _mealCategoryState = MutableStateFlow(MealCategory(emptyList()))
    val mealCategoryState: StateFlow<MealCategory>
        get() = _mealCategoryState

    private val _mealByCategoryState = MutableStateFlow(mutableListOf<String>())
    val mealByCategoryState: StateFlow<MutableList<String>>
        get() = _mealByCategoryState

    init {
        viewModelScope.launch {
            val categories = mealRepository.getMealCategories()
            var mealsByCategory: MealByCat
            val allMeals = mutableListOf<String>()

            categories.meals.forEach {
                mealsByCategory = mealRepository
                    .getMealByCategory("https://www.themealdb.com/api/json/v1/1/filter.php?c=${it.strCategory}")

                mealsByCategory.meals.forEach { meal ->
                    allMeals.add(meal.strMeal)
                }
            }

            _mealCategoryState.value = categories
            _mealByCategoryState.value = allMeals
        }
    }

}