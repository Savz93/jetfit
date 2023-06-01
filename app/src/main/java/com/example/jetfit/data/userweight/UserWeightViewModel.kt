package com.example.jetfit.data.userweight

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UserWeightViewModel(application: Application): AndroidViewModel(application) {
    val getAllUserWeight: LiveData<List<UserWeight>>
    private val repository: UserWeightRepository

    init {
        val userWeightDao = UserWeightDatabase.getInstance(application).userWeightDao()
        repository = UserWeightRepository(userWeightDao)
        getAllUserWeight = repository.getAllUserWeights
    }

    fun addUserWeight(userWeight: UserWeight) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserWeight(userWeight)
        }
    }

    fun updateUserWeight(userWeight: UserWeight) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserWeight(userWeight)
        }

    }

    fun deleteUserWeight(userWeight: UserWeight) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserWeight(userWeight)
        }
    }

    fun deleteAllUserWeights() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUserWeights()
        }
    }

}

class UserWeightViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(UserWeightViewModel::class.java)) {
            return UserWeightViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}