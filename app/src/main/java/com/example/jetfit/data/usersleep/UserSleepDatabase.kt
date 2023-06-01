package com.example.jetfit.data.usersleep

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetfit.data.userweight.UserWeightDatabase

@Database(entities = [UserSleep::class], version = 2, exportSchema = false)
abstract class UserSleepDatabase: RoomDatabase() {
    abstract fun userSleepDao(): UserSleepDao

    companion object {
        private var INSTANCE: UserSleepDatabase? = null

        fun getInstance(context: Context): UserSleepDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserSleepDatabase::class.java,
                        "user_sleep_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}