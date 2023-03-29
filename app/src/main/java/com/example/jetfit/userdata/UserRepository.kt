package com.example.jetfit.userdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jetfit.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun addUser(newUser: User) {
        coroutineScope.launch(Dispatchers.IO) {
            userDao.addUser(newUser)
        }
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