package com.example.interrapidisimo.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locality_table")
data class LocalityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") var id: Int = 0,
    @ColumnInfo(name = "Abbreviation") var abbreviation: String?,
    @ColumnInfo(name = "FullName") var fullName: String?,
)


