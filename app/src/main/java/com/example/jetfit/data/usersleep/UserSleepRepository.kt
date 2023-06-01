package com.example.jetfit.data.usersleep

import androidx.lifecycle.LiveData

class UserSleepRepository(private val userSleepDao: UserSleepDao) {
    val getAllUserSleep: LiveData<List<UserSleep>> = userSleepDao.getAllUserSleep()

    suspend fun addUserSleep(userSleep: UserSleep) = userSleepDao.insert(userSleep)
    suspend fun updateUserSleep(userSleep: UserSleep) = userSleepDao.update(userSleep)
    suspend fun deleteUserSleep(userSleep: UserSleep) = userSleepDao.delete(userSleep)
    suspend fun deleteAllUserSleep() = userSleepDao.deleteAllUserSleep()
}