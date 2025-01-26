package com.example.interrapidisimo.data.network

import javax.inject.Inject

class Service @Inject constructor(
    private val api: ApiClient
) {

   /* suspend fun getQuotes(): List<Model> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllQuotes()
            response.body() ?: emptyList()
        }
    }

    suspend fun postControlVersion(request: RequestControlVDTO): ResponseControlVDTO {
        return withContext(Dispatchers.IO) {
            val response = api.postControlVersion(request)
            response.body() ?: emptyList()
        }
    }

    suspend fun getQuotes(): List<Model> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllQuotes()
            response.body() ?: emptyList()
        }
    }

    suspend fun getQuotes(): List<Model> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllQuotes()
            response.body() ?: emptyList()
        }
    }

    suspend fun getQuotes(): List<Model> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllQuotes()
            response.body() ?: emptyList()
        }
    }

    */


}