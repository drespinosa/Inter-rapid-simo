package com.example.interrapidisimo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.interrapidisimo.data.database.daos.SchemaDao
import com.example.interrapidisimo.data.database.daos.UserDao
import com.example.interrapidisimo.data.database.entities.SchemaEntity
import com.example.interrapidisimo.data.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        SchemaEntity::class,
    ],
    version = 2,
    exportSchema = true
)
abstract class TablesDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getSchemaDao(): SchemaDao

    companion object {
        @Volatile
        private var INSTANCE: TablesDatabase? = null
        private const val SCHEMA = "schema_table"
        private const val ID = "Id"
        private const val TABLE = "Table"
        private const val CONTENT = "Content"

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `$SCHEMA` (`$ID` INTEGER NOT NULL, `$TABLE` TEXT, `$CONTENT` TEXT, PRIMARY KEY(`$ID`))"
                )
            }
        }
    }

}