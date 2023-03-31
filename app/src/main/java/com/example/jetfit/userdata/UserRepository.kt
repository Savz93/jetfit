package com.example.jetfit.userdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetfit.User
import kotlinx.coroutines.*

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()
    val findUser = MutableLiveData<User>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun addUser(newUser: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.addUser(newUser)
        }
    }

    fun findUser(uid: String) {
        coroutineScope.launch(Dispatchers.Main) {
            findUser.value = asyncFind(uid).await()
        }
    }

    private fun asyncFind(uid: String): Deferred<User> =
        coroutineScope.async(Dispatchers.IO) {
            return@async userDao.findUser(uid)
        }


    fun updateUser(user: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.updateUserDetails(user)
        }
    }

    fun deleteUser(user: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.deleteUser(user)
        }
    }



}