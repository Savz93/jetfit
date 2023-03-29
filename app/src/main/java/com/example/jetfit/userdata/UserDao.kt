package com.example.jetfit.userdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetfit.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<User>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUserDetails(user: User)

    @Delete
    suspend fun deleteUser(user: User)

}