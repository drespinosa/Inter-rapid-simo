package com.example.interrapidisimo.data.repository

import android.util.Log
import com.example.interrapidisimo.data.model.Model
import com.example.interrapidisimo.data.model.dto.request.RequestUserDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataControlVDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
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

    suspend fun postControlVersion(): Response<ResponseDataControlVDTO> {
        return api.postControlVersion()
    }

    suspend fun postLogIn(request: RequestUserDTO): Response<ResponseDataUserDTO> {
        return api.postLogIn(
            usuario = "pam.meredy21",
            identificacion = "987204545",
            idUsuario = "pam.meredy21",
            idCentroServicio = "1295",
            nombreCentroServicio = "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA 30 # 7-45",
            idAplicativoOrigen = "9",
            contentType = "application/json",
            request
        )
    }

    suspend fun getSchema(): Response<List<ResponseDataSchemeDTO>> {
        return api.getSchema()
    }

    suspend fun getLocalities(): Response<List<ResponseDataLocalityDTO>> {
        return api.getLocalities()
    }
}