package com.example.jetfit

import com.example.jetfit.data.model.MealByCategory

sealed class MealDataUiState {
    data class Success(val meals: List<MealByCategory>): MealDataUiState()
    data class Error(val exception: Throwable): MealDataUiState()
}
