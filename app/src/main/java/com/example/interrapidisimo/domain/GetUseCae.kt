package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.model.Model
import com.example.interrapidisimo.data.repository.ServiceRepository
import retrofit2.Response
import javax.inject.Inject

class GetUseCae @Inject constructor(
    private val repository: ServiceRepository
) : ServiceUseCase<Response<List<Model>>, Any?>() {

    override suspend fun run(request: Any?): Response<List<Model>> {
        return repository.getQuotes()
    }
}