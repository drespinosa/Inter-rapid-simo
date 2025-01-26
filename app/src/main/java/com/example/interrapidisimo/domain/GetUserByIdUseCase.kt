package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.mapper.UserMapper
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import com.example.interrapidisimo.data.model.vo.UserVO
import com.example.interrapidisimo.data.repository.RoomRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(private val repository: RoomRepository) {
    suspend fun get(): UserVO? {
        return UserMapper.toVO(repository.getUser())
    }
}