package com.example.jetfit.userdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jetfit.User
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userList: LiveData<List<User>> = userRepository.allUsers
    val foundUser: LiveData<User> = userRepository.foundUser

    fun getAllUsers() {
        userRepository.getAllUsers()
    }

    fun addUser(user: User) {
        userRepository.addUser(user)
    }

    fun updateUser(user: User) {
        userRepository.updateUser(user)
    }

    fun deleteUser(user: User) {
        userRepository.deleteUser(user)
    }


}