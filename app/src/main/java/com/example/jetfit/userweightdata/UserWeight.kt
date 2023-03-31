package com.example.jetfit.userweightdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_weight_and_date")
data class UserWeight(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "uid")
    var uid: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "weight")
    var weight: Int
)
