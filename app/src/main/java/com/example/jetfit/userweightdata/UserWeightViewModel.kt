package com.example.jetfit.userweightdata

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetfit.User
import com.example.jetfit.userdata.UserRepository
import com.example.jetfit.userdata.UserRoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserWeightViewModel @Inject constructor(application: Application): ViewModel() {

    val allWeights: LiveData<List<UserWeight>>
    val getUserByUid: MutableLiveData<List<UserWeight>>
    private val repository: UserWeightRepository

    init {
        val userWeightDb = UserWeightRoomDatabase.getInstance(application)
        val userWeightDao = userWeightDb.userWeightDao()
        repository = UserWeightRepository(userWeightDao)

        allWeights = repository.allWeights
        getUserByUid = repository.findUserByUid
    }

    fun insertUserWeight(userWeight: UserWeight) {
        repository.insertWeight(userWeight)
    }

    fun getUserByUid(uid: String) {
        repository.findUserByUid(uid)
    }

    fun updateUserWeight(userWeight: UserWeight) {
        repository.updateWeight(userWeight)
    }

    fun deleteUserWeight(userWeight: UserWeight) {
        repository.deleteWeight(userWeight)
    }

    fun deleteUserWeightDb() {
        repository.deleteUserWeightDb()
    }

}