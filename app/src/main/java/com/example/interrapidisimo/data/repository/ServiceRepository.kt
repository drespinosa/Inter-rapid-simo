package com.example.interrapidisimo.data.repository

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

    suspend fun getControlVersion(): Response<ResponseDataControlVDTO> {
        return withContext(Dispatchers.IO) {
            api.getControlVersion()
        }
    }

    suspend fun postLogIn(request: RequestUserDTO): Response<ResponseDataUserDTO> {
        return withContext(Dispatchers.IO) {
            api.postLogIn(
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

    }

    suspend fun getSchema(): Response<List<ResponseDataSchemeDTO>> {
        return withContext(Dispatchers.IO) {
            api.getSchema()
        }
    }

    suspend fun getLocalities(): Response<List<ResponseDataLocalityDTO>> {
        return withContext(Dispatchers.IO) {
            api.getLocalities()
        }
    }
}