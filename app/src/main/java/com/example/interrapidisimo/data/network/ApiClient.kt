package com.example.interrapidisimo.data.network

import com.example.interrapidisimo.data.model.dto.request.RequestControlVDTO
import com.example.interrapidisimo.data.model.dto.request.RequestUserDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataControlVDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import com.example.interrapidisimo.data.model.Model
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiClient {

    @GET("/.json")
    suspend fun getAllQuotes(): Response<List<Model>>

    @POST("/.json")
    suspend fun postControlVersion(@Body userDTO: RequestControlVDTO): Response<ResponseDataControlVDTO>

    @POST("/.json")
    suspend fun postLogIn(@Body userDTO: RequestUserDTO): Response<ResponseDataUserDTO>

    @GET("/.json")
    suspend fun getSchema(): Response<ResponseDataSchemeDTO>

    @POST("/.json")
    suspend fun getLocalities(): Response<ResponseDataLocalityDTO>

}