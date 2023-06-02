package com.example.jetfit.data.userexercise

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserExerciseViewModel(application: Application): AndroidViewModel(application) {

    var getAllUserExercise: LiveData<List<UserExercise>>
    private var repository: UserExerciseRepository

    init {
        val userExerciseDao = UserExerciseDatabase.getInstance(application).userExerciseDao()
        repository = UserExerciseRepository(userExerciseDao)
        getAllUserExercise = repository.getAllUserExercise
    }

    fun addUserExercise(userExercise: UserExercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserExercise(userExercise)
        }
    }

    fun updateUserExercise(userExercise: UserExercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserExercise(userExercise)
        }
    }

    fun deleteUserExercise(userExercise: UserExercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserExercise(userExercise)
        }
    }

    fun deleteAllUserExercise() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUserExercise()
        }
    }


}