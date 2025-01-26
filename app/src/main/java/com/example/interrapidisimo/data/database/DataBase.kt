package com.example.interrapidisimo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.interrapidisimo.data.database.daos.UserDao
import com.example.interrapidisimo.data.database.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class TablesDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

}