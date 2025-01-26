package com.example.interrapidisimo.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "user") var user: String?,
    @ColumnInfo(name = "identification") var identification:  String?,
    @ColumnInfo(name = "name") var name:  String?
)


