package com.example.interrapidisimo.data.repository

import android.util.Log
import com.example.interrapidisimo.data.model.dto.request.RequestControlVDTO
import com.example.interrapidisimo.data.model.dto.request.RequestUserDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataControlVDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import com.example.interrapidisimo.data.model.Model
import com.example.interrapidisimo.data.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val api: ApiClient
) {

    suspend fun getQuotes(): Response<List<Model>> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllQuotes()
            Log.d("http ${this::class.java.simpleName}", "R getQuotes response: $response")
            response
        }
    }

    suspend fun postControlVersion(request: RequestControlVDTO): Response<ResponseDataControlVDTO> {
        return api.postControlVersion(request)
    }

    suspend fun postLogIn(request: RequestUserDTO): Response<ResponseDataUserDTO> {
        return api.postLogIn(request)
    }

    suspend fun getSchema(): Response<List<ResponseDataSchemeDTO>> {
        //return api.getSchema()
        return Response.success(
            listOf(
                ResponseDataSchemeDTO(
                    name = "Tabla 1",
                    primaryKey = "1",
                    query = "1",
                    size = "1",
                    filter = "1",
                    field = "1",
                    updateDate = "1997-04-17'T'17:17:17"
                )
            )
        )
    }

    suspend fun getLocalities(): Response<ResponseDataLocalityDTO> {
        return api.getLocalities()
    }
}