package com.example.jetfit.data.userexercise

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserExercise::class], version = 2, exportSchema = false)
abstract class UserExerciseDatabase: RoomDatabase() {
    abstract fun userExerciseDao(): UserExerciseDao

    companion object {
        private var INSTANCE: UserExerciseDatabase? = null

        fun getInstance(context: Context): UserExerciseDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserExerciseDatabase::class.java,
                        "user_exercise_db"
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