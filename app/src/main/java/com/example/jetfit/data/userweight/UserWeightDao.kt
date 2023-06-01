package com.example.jetfit.data.userweight

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserWeightDao {

    @Query("SELECT * FROM user_weight ORDER BY id DESC")
    fun getAllUserWeights(): LiveData<List<UserWeight>>

    @Query("SELECT * FROM user_weight WHERE id = :id")
    fun getUserWeightById(id: Int): UserWeight?

    @Insert
    suspend fun insert(userWeight: UserWeight)

    @Update
    suspend fun update(userWeight: UserWeight)

    @Delete
    suspend fun delete(userWeight: UserWeight)

    @Query("DELETE FROM user_weight")
    suspend fun deleteAllUserWeights()
}