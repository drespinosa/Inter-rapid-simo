package com.example.interrapidisimo.data.repository

import android.util.Log
import com.example.interrapidisimo.data.database.daos.UserDao
import com.example.interrapidisimo.data.database.entities.UserEntity
import com.example.interrapidisimo.data.mapper.UserMapper
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun saveUser(userEntity: UserEntity) {
        return withContext(Dispatchers.IO) {
            Log.d("http ${this::class.java.simpleName}", "R saveUser userEntity: $userEntity")
            userDao.replaceUser(userEntity)
        }
    }

    suspend fun deleteUser() {
        return withContext(Dispatchers.IO) {
            userDao.deleteAllUsers()
        }
    }

    suspend fun getUser(): ResponseDataUserDTO {
        return withContext(Dispatchers.IO) {
            val userEntity = UserEntity(
                user = "user",
                identification = "identification",
                name = "name"
            )
            userEntity.let { UserMapper.fromEntity(it) }
        }
    }
}