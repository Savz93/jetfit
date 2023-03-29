package com.example.jetfit.userdata

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetfit.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(application: Application) : ViewModel() {

    val allUsers: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDb = UserRoomDatabase.getInstance(application)
        val userDao = userDb.userDao()
        repository = UserRepository(userDao)

        allUsers = repository.allUsers
    }

    fun addUser(user: User) {
        repository.addUser(user)
    }

    fun updateUser(user: User) {
        repository.updateUser(user)
    }

    fun deleteUser(user: User) {
        repository.deleteUser(user)
    }

}

class UserViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(application) as T
    }


}

