package com.example.interrapidisimo.data.repository

import com.example.interrapidisimo.data.database.daos.LocalityDao
import com.example.interrapidisimo.data.database.daos.SchemaDao
import com.example.interrapidisimo.data.database.daos.UserDao
import com.example.interrapidisimo.data.database.entities.LocalityEntity
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
    private val localityDao: LocalityDao
) {

    /**
     * Guarda o reemplaza un usuario en la base de datos.
     *
     * @param userEntity La entidad de usuario que se va a guardar o reemplazar.
     */
    suspend fun saveUser(userEntity: UserEntity) {
        return withContext(Dispatchers.IO) {
            userDao.replaceUser(userEntity)
        }
    }

    /**
     * Elimina todos los usuarios de la base de datos.
     */
    suspend fun deleteUser() {
        return withContext(Dispatchers.IO) {
            userDao.deleteAllUsers()
        }
    }

    /**
     * Obtiene un único usuario de la base de datos y lo mapea a un DTO (Data Transfer Object).
     *
     * @return Un objeto [ResponseDataUserDTO] que representa al usuario obtenido.
     */
    suspend fun getUser(): ResponseDataUserDTO {
        return withContext(Dispatchers.IO) {
            val userEntity = userDao.getSingleUser()
            userEntity.let { UserMapper.fromEntity(it) }
        }
    }

    /**
     * Guarda o reemplaza una lista de esquemas en la base de datos.
     *
     * @param tables La lista de entidades de esquema que se van a guardar o reemplazar.
     */
    suspend fun saveSchema(tables: List<SchemaEntity>) {
        return withContext(Dispatchers.IO) {
            schemaDao.replaceTables(tables)
        }
    }

    /**
     * Guarda o reemplaza una lista de localidades en la base de datos.
     *
     * @param tables La lista de entidades de localidad que se van a guardar o reemplazar.
     */
    suspend fun saveLocality(tables: List<LocalityEntity>) {
        return withContext(Dispatchers.IO) {
            localityDao.replaceTables(tables)
        }
    }
}