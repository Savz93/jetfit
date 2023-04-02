package com.example.jetfit.userweightdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserWeight::class], version = 1)
abstract class UserWeightRoomDatabase: RoomDatabase() {
    abstract fun userWeightDao(): UserWeightDao

    companion object {
        private var INSTANCE: UserWeightRoomDatabase? = null

        fun getInstance(context: Context): UserWeightRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserWeightRoomDatabase::class.java,
                        "user_weight_db"
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