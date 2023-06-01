package com.example.jetfit.data.userweight

import androidx.lifecycle.LiveData

class UserWeightRepository(private val userWeightDao: UserWeightDao) {
    val getAllUserWeights: LiveData<List<UserWeight>> = userWeightDao.getAllUserWeights()

    suspend fun addUserWeight(userWeight: UserWeight) = userWeightDao.insert(userWeight)
    suspend fun updateUserWeight(userWeight: UserWeight) = userWeightDao.update(userWeight)
    suspend fun deleteUserWeight(userWeight: UserWeight) = userWeightDao.delete(userWeight)
    suspend fun deleteAllUserWeights() = userWeightDao.deleteAllUserWeights()

}