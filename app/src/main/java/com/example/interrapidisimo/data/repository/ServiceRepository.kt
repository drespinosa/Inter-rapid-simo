package com.example.interrapidisimo.data.repository

import com.example.interrapidisimo.data.model.dto.request.RequestUserDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataControlVDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import com.example.interrapidisimo.data.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ServiceRepository @Inject constructor(
    private val api: ApiClient
) {

    suspend fun getControlVersion(): Response<ResponseDataControlVDTO> {
        return withContext(Dispatchers.IO) {
            val response = api.getControlVersion()
            if (response.isSuccessful) {
                val versionNumber = response.body() ?: 0
                Response.success(ResponseDataControlVDTO(versionNumber))
            } else {
                Response.error(response.code(), ResponseBody.create(null, ""))
            }
        }
    }

    suspend fun postLogIn(request: RequestUserDTO): Response<ResponseDataUserDTO> {
        return withContext(Dispatchers.IO) {
            api.postLogIn(
                usuario = "pam.meredy21",
                identificacion = "987204545",
                accept = "text/json",
                idUsuario = "pam.meredy21",
                idCentroServicio = "1295",
                nombreCentroServicio = "PTO/BOGOTA/CUND/COL/OF PRINCIPAL - CRA30#7-45",
                idAplicativoOrigen = "9",
                contentType = "application/json",
                request
            )
        }

    }

    suspend fun getSchema(): Response<List<ResponseDataSchemeDTO>> {
        return withContext(Dispatchers.IO) {
            //api.getSchema()
            val simulatedData = listOf(
                ResponseDataSchemeDTO(name = "TablaUsuarios", content = "Usuario 1"),
                ResponseDataSchemeDTO(name = "TablaProductos", content = "Producto 1"),
                ResponseDataSchemeDTO(name = "TablaPedidos", content = "Pedido 1")
            )
            Response.success(simulatedData)
        }
    }

    suspend fun getLocalities(): Response<List<ResponseDataLocalityDTO>> {
        return withContext(Dispatchers.IO) {
           // api.getLocalities()
            val simulatedData = listOf(
                ResponseDataLocalityDTO(
                    abbreviation = "BOG",
                    city = "Bogota"
                ),
                ResponseDataLocalityDTO(
                    abbreviation = "ANT",
                    city = "Antioquia"
                )
            )
            Response.success(simulatedData)
        }
    }
}