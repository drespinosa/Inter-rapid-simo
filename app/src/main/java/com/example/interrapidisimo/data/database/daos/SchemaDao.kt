package com.example.interrapidisimo.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.interrapidisimo.data.database.entities.SchemaEntity

@Dao
interface SchemaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserTables(schemaEntity: List<SchemaEntity>)

    @Query("DELETE FROM schema_table")
    suspend fun deleteTables()

    @Transaction
    suspend fun replaceTables(tables: List<SchemaEntity>) {
        deleteTables()
        inserTables(tables)
    }

}