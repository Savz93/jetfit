package com.example.jetfit.userweightdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserWeightDao {

    @Query("SELECT * FROM user_weight")
    fun getAllUserWeight(): LiveData<List<UserWeight>>

    @Query("SELECT * FROM user_weight WHERE uid = :uid")
    fun getUserByUid(uid: String): List<UserWeight>

    @Query("DELETE FROM user_weight")
    fun deleteUserWeightDb()

    @Insert
    suspend fun insertWeight(userWeight: UserWeight)

    @Update
    fun updateWeight(userWeight: UserWeight)

    @Delete
    fun deleteWeight(userWeight: UserWeight)


}