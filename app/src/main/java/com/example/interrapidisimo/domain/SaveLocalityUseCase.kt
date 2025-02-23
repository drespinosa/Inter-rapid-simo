package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.mapper.LocalityMapper
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.data.repository.RoomRepository
import javax.inject.Inject

class SaveLocalityUseCase @Inject constructor(
    private val repository: RoomRepository
) {
    suspend fun insert(tables: List<ResponseDataLocalityDTO>) {
        repository.saveLocality(LocalityMapper.toEntityList(tables))
    }
}