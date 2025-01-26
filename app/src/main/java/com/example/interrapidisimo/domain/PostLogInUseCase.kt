package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.model.dto.request.RequestUserDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import com.example.interrapidisimo.data.repository.ServiceRepository
import retrofit2.Response
import javax.inject.Inject

class PostLogInUseCase @Inject constructor(
    private val repository: ServiceRepository
) : ServiceUseCase<Response<ResponseDataUserDTO>, Any?>() {
    override suspend fun run(request: Any?): Response<ResponseDataUserDTO> {
        return repository.postLogIn(request as RequestUserDTO)
    }
}