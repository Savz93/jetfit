package com.example.jetfit.userdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetfit.User

@Database(entities = [(User::class)], version = 1, exportSchema = false)
abstract class UserRoomDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    @Volatile
    private var INSTANCE: UserRoomDatabase? = null

    fun getInstance(context: Context) : UserRoomDatabase {
        synchronized(this) {
            var instance = INSTANCE

            if(instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "user_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
            }
            return instance
        }
    }

}