package com.example.jetfit.userweightdata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserWeightDao {

    @Query("SELECT * FROM user_weight")
    fun getAllUserWeight(): List<UserWeight>

    @Insert
    fun insertWeight(userWeight: UserWeight)

    @Update
    fun updateWeight(userWeight: UserWeight)

    @Delete
    fun deleteWeight(userWeight: UserWeight)
}