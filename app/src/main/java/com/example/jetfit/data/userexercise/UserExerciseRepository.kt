package com.example.jetfit.data.userexercise

import androidx.lifecycle.LiveData

class UserExerciseRepository(private val userExerciseDao: UserExerciseDao) {

    val getAllUserExercise: LiveData<List<UserExercise>> = userExerciseDao.getAllUserExercise()

    suspend fun addUserExercise(userExercise: UserExercise) = userExerciseDao.addUserExercise(userExercise)
    suspend fun updateUserExercise(userExercise: UserExercise) = userExerciseDao.updateUserExercise(userExercise)
    suspend fun deleteUserExercise(userExercise: UserExercise) = userExerciseDao.deleteUserExercise(userExercise)
    suspend fun deleteAllUserExercise() = userExerciseDao.deleteAllUserExercise()
}