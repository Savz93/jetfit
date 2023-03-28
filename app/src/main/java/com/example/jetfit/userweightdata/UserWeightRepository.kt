package com.example.jetfit.userweightdata

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserWeightRepository(val userWeightDao: UserWeightDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun getAllWeights(): List<UserWeight> {
        return userWeightDao.getAllUserWeight()
    }

    suspend fun insertWeight(userWeight: UserWeight) {
        coroutineScope.launch(Dispatchers.IO) {
            userWeightDao.insertWeight(userWeight)
        }
    }

    suspend fun updateWeight(userWeight: UserWeight) {
        coroutineScope.launch(Dispatchers.IO) {
            userWeightDao.updateWeight(userWeight)
        }
    }

    suspend fun deleteWeight(userWeight: UserWeight) {
        coroutineScope.launch(Dispatchers.IO) {
            userWeightDao.deleteWeight(userWeight)
        }
    }

}