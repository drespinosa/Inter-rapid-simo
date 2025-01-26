package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.data.repository.ServiceRepository
import retrofit2.Response
import javax.inject.Inject

class GetLocalitiesUseCase @Inject constructor(
    private val repository: ServiceRepository
) : ServiceUseCase<Response<ResponseDataLocalityDTO>, Any?>() {
    override suspend fun run(request: Any?): Response<ResponseDataLocalityDTO> {
        return repository.getLocalities()
    }
}