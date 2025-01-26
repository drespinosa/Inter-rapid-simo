package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.mapper.UserMapper
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import com.example.interrapidisimo.data.repository.RoomRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: RoomRepository
) {
    suspend fun insert(user: ResponseDataUserDTO) {
        repository.saveUser(UserMapper.toEntity(user))
    }
}