package com.example.jetfit.userweightdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserWeight::class], version = 1)
abstract class UserWeightAndDateRoomDatabase: RoomDatabase() {
    abstract fun userWeightAndDateDao(): UserWeightDao

    companion object {
        private var INSTANCE: UserWeightAndDateRoomDatabase? = null

        fun getInstance(context: Context): UserWeightAndDateRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserWeightAndDateRoomDatabase::class.java,
                        "user_weight_database"
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