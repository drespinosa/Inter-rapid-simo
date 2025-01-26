package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.data.repository.ServiceRepository
import retrofit2.Response
import javax.inject.Inject

class GetSchemaUseCase @Inject constructor(
    private val repository: ServiceRepository
) : ServiceUseCase<Response<List<ResponseDataSchemeDTO>>, Any?>() {
    override suspend fun run(request: Any?): Response<List<ResponseDataSchemeDTO>> {
        return repository.getSchema()
    }
}