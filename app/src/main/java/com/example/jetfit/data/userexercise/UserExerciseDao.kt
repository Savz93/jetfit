package com.example.jetfit.data.userexercise

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserExerciseDao {

    @Query("SELECT * FROM user_exercise ORDER BY id DESC")
    fun getAllUserExercise(): LiveData<List<UserExercise>>

    @Query("SELECT * FROM user_exercise WHERE id = :id")
    fun getUserExerciseById(id: Int): UserExercise?

    @Insert
    suspend fun addUserExercise(userExercise: UserExercise)

    @Update
    suspend fun updateUserExercise(userExercise: UserExercise)

    @Delete
    suspend fun deleteUserExercise(userExercise: UserExercise)

    @Query("DELETE FROM user_exercise")
    suspend fun deleteAllUserExercise()

}