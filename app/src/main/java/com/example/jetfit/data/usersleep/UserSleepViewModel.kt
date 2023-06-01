package com.example.jetfit.data.usersleep

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserSleepViewModel(private val application: Application): AndroidViewModel(application) {
    val getAllUserSleep: LiveData<List<UserSleep>>
    private val repository: UserSleepRepository

    init {
        val userSleepDao = UserSleepDatabase.getInstance(application).userSleepDao()
        repository = UserSleepRepository(userSleepDao)
        getAllUserSleep = repository.getAllUserSleep

    }

    fun addUserSleep(userSleep: UserSleep) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserSleep(userSleep)
        }
    }

    fun updateUserWeight(userSleep: UserSleep) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserSleep(userSleep)
        }

    }

    fun deleteUserSleep(userSleep: UserSleep) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserSleep(userSleep)
        }
    }

    fun deleteAllUserSleep() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUserSleep()
        }
    }
}