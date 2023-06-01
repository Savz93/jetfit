package com.example.jetfit.data.userweight

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserWeight::class], version = 1, exportSchema = false)
abstract class UserWeightDatabase: RoomDatabase() {
    abstract fun userWeightDao(): UserWeightDao

    companion object {
        private var INSTANCE: UserWeightDatabase? = null

        fun getInstance(context: Context): UserWeightDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserWeightDatabase::class.java,
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