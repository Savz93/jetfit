package com.example.jetfit.userweightdata

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class UserWeightViewModel @Inject constructor(private val userWeightRepository: UserWeightRepository): ViewModel() {

    suspend fun getAllUserWeight(): List<UserWeight> {
        return userWeightRepository.getAllWeights()
    }

    suspend fun insertUserWeight(userWeight: UserWeight) {
        userWeightRepository.insertWeight(userWeight)
    }

    suspend fun updateUserWeight(userWeight: UserWeight) {
        userWeightRepository.updateWeight(userWeight)
    }

    suspend fun deleteUserWeight(userWeight: UserWeight) {
        userWeightRepository.deleteWeight(userWeight)
    }

}