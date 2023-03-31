package com.example.jetfit.userweightdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetfit.User

@Dao
interface UserWeightDao {

    @Query("SELECT * FROM user_weight_and_date")
    fun getAllUserWeight(): LiveData<List<UserWeight>>

    @Query("SELECT * FROM user_weight_and_date WHERE uid = :uid")
    fun getUserByUid(uid: String): List<UserWeight>

    @Insert
    fun insertWeight(userWeight: UserWeight)

    @Update
    fun updateWeight(userWeight: UserWeight)

    @Delete
    fun deleteWeight(userWeight: UserWeight)
}