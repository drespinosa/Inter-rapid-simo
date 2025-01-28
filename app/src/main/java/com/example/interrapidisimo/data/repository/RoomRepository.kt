package com.example.interrapidisimo.data.repository

import com.example.interrapidisimo.data.database.daos.SchemaDao
import com.example.interrapidisimo.data.database.daos.UserDao
import com.example.interrapidisimo.data.database.entities.SchemaEntity
import com.example.interrapidisimo.data.database.entities.UserEntity
import com.example.interrapidisimo.data.mapper.UserMapper
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val userDao: UserDao,
    private val schemaDao: SchemaDao,
) {

    suspend fun saveUser(userEntity: UserEntity) {
        return withContext(Dispatchers.IO) {
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
            val userEntity = userDao.getSingleUser()
            userEntity.let { UserMapper.fromEntity(it) }
        }
    }

    suspend fun saveSchema(tables: List<SchemaEntity>) {
        return withContext(Dispatchers.IO) {
            schemaDao.replaceTables(tables)
        }
    }
}