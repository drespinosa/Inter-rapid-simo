package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataControlVDTO
import com.example.interrapidisimo.data.repository.ServiceRepository
import retrofit2.Response
import javax.inject.Inject

class PostControlVUseCase @Inject constructor(
    private val repository: ServiceRepository
) : ServiceUseCase<Response<ResponseDataControlVDTO>, Any?>() {
    override suspend fun run(request: Any?): Response<ResponseDataControlVDTO> {
        return repository.postControlVersion()
    }
}