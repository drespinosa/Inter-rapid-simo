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

    /**
     * Obtiene la versión de control desde el servidor.
     *
     * @return Un objeto [Response] que contiene un [ResponseDataControlVDTO] con el número de versión
     * si la solicitud es exitosa, o un error en caso contrario.
     */
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

    /**
     * Realiza una solicitud de inicio de sesión (LogIn) al servidor.
     *
     * @param request Objeto [RequestUserDTO] que contiene los datos de la solicitud de inicio de sesión.
     * @return Un objeto [Response] que contiene un [ResponseDataUserDTO] si la solicitud es exitosa,
     * o un error en caso contrario.
     */
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

    /**
     * Obtiene un esquema de datos simulado.
     *
     * @return Un objeto [Response] que contiene una lista de [ResponseDataSchemeDTO] con datos simulados.
     */
    suspend fun getSchema(): Response<List<ResponseDataSchemeDTO>> {
        return withContext(Dispatchers.IO) {
            api.getSchema()
        }
    }

    /**
     * Obtiene una lista de localidades desde el servidor.
     *
     * @return Un objeto [Response] que contiene una lista de [ResponseDataLocalityDTO] si la solicitud es exitosa,
     * o un error en caso contrario.
     */
    suspend fun getLocalities(): Response<List<ResponseDataLocalityDTO>> {
        return withContext(Dispatchers.IO) {
           api.getLocalities()
        }
    }
}