package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.mapper.SchemaMapper
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.data.repository.RoomRepository
import javax.inject.Inject

class SaveSchemaUseCase @Inject constructor(
    private val repository: RoomRepository
) {
    suspend fun insert(tables: List<ResponseDataSchemeDTO>) {
        repository.saveSchema(SchemaMapper.toEntityList(tables))
    }
}