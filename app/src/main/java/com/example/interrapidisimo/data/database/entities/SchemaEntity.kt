package com.example.interrapidisimo.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schema_table")
data class SchemaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id") var id: Int = 0,
    @ColumnInfo(name = "Table") var table: String?,
    @ColumnInfo(name = "Content") var content: String?,
)


