package com.example.interrapidisimo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.interrapidisimo.data.database.daos.LocalityDao
import com.example.interrapidisimo.data.database.daos.SchemaDao
import com.example.interrapidisimo.data.database.daos.UserDao
import com.example.interrapidisimo.data.database.entities.LocalityEntity
import com.example.interrapidisimo.data.database.entities.SchemaEntity
import com.example.interrapidisimo.data.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        SchemaEntity::class,
        LocalityEntity::class,
    ],
    version = 3,
    exportSchema = true
)
abstract class TablesDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getSchemaDao(): SchemaDao
    abstract fun getLocalityDao(): LocalityDao

    companion object {
        @Volatile
        private var INSTANCE: TablesDatabase? = null
        private const val SCHEMA = "schema_table"
        private const val ID = "Id"
        private const val TABLE = "Table"
        private const val CONTENT = "Content"
        private const val LOCALITY = "locality_table"
        private const val ABBREVIATION = "Abbreviation"
        private const val FULL_NAME = "FullName"

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `$SCHEMA` (`$ID` INTEGER NOT NULL, `$TABLE` TEXT, `$CONTENT` TEXT, PRIMARY KEY(`$ID`))"
                )
            }
        }

        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `$LOCALITY` (`$ID` INTEGER NOT NULL, `$ABBREVIATION` TEXT, `$FULL_NAME` TEXT, PRIMARY KEY(`$ID`))"
                )
            }
        }
    }

}