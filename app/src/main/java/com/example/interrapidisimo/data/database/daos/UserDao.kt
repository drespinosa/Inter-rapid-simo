package com.example.interrapidisimo.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.interrapidisimo.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Transaction
    suspend fun replaceUser(userEntity: UserEntity) {
        deleteAllUsers()
        insertUser(userEntity)
    }

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getSingleUser(): UserEntity?

}