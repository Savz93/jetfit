package com.example.jetfit.userweightdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetfit.User
import kotlinx.coroutines.*

class UserWeightRepository(private val userWeightDao: UserWeightDao) {

    val allWeights: LiveData<List<UserWeight>> = userWeightDao.getAllUserWeight()
    val findUserByUid = MutableLiveData<List<UserWeight>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertWeight(userWeight: UserWeight) {
        coroutineScope.launch(Dispatchers.IO) {
            userWeightDao.insertWeight(userWeight)
        }
    }

    fun findUserByUid(uid: String) {
        coroutineScope.launch(Dispatchers.Main) {
            findUserByUid.value = asyncFind(uid).await()
        }
    }

    private fun asyncFind(uid: String): Deferred<List<UserWeight>> =
        coroutineScope.async(Dispatchers.IO) {
            return@async userWeightDao.getUserByUid(uid)
        }

    fun updateWeight(userWeight: UserWeight) {
        coroutineScope.launch(Dispatchers.IO) {
            userWeightDao.updateWeight(userWeight)
        }
    }

    fun deleteWeight(userWeight: UserWeight) {
        coroutineScope.launch(Dispatchers.IO) {
            userWeightDao.deleteWeight(userWeight)
        }
    }

    fun deleteUserWeightDb() {
        coroutineScope.launch(Dispatchers.IO) {
            userWeightDao.deleteUserWeightDb()
        }
    }

}