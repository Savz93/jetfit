package com.example.jetfit.data.favoritemeal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMeal::class], version = 1, exportSchema = false)
abstract class FavoriteMealDatabase: RoomDatabase() {

    abstract fun favoriteMealDao(): FavoriteMealDao

    companion object {
        private var INSTANCE: FavoriteMealDatabase? = null

        fun getInstance(context: Context): FavoriteMealDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteMealDatabase::class.java,
                        "favorite_meal_db"
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