package com.example.jetfit.data.usersleep

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jetfit.data.userweight.UserWeight

@Dao
interface UserSleepDao {

    @Query("SELECT * FROM user_sleep ORDER BY id DESC")
    fun getAllUserSleep(): LiveData<List<UserSleep>>

    @Query("SELECT * FROM user_sleep WHERE id = :id")
    fun getUserSleepById(id: Int): UserSleep?

    @Insert
    suspend fun insert(userSleep: UserSleep)

    @Update
    suspend fun update(userSleep: UserSleep)

    @Delete
    suspend fun delete(userSleep: UserSleep)

    @Query("DELETE FROM user_sleep")
    suspend fun deleteAllUserSleep()
}