package com.example.interrapidisimo.data.network

import com.example.interrapidisimo.data.model.dto.request.RequestUserDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataControlVDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataLocalityDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataSchemeDTO
import com.example.interrapidisimo.data.model.dto.response.data.ResponseDataUserDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiClient {

    @GET("/apicontrollerpruebas/api/ParametrosFramework/ConsultarParametrosFramework/VPStoreAppControl")
    suspend fun getControlVersion(): Response<ResponseDataControlVDTO>

    @POST("/FtEntregaElectronica/MultiCanales/ApiSeguridadPruebas/api/Seguridad/AuthenticaUsuarioApp")
    suspend fun postLogIn(
        @Header("Usuario") usuario: String,
        @Header("Identificacion") identificacion: String,
        @Header("IdUsuario") idUsuario: String,
        @Header("IdCentroServicio") idCentroServicio: String,
        @Header("NombreCentroServicio") nombreCentroServicio: String,
        @Header("IdAplicativoOrigen") idAplicativoOrigen: String,
        @Header("Content-Type") contentType: String,
        @Body userDTO: RequestUserDTO
    ): Response<ResponseDataUserDTO>

    @GET("/apicontrollerpruebas/api/SincronizadorDatos/ObtenerEsquema/true")
    suspend fun getSchema(): Response<List<ResponseDataSchemeDTO>>

    @GET("/apicontrollerpruebas/api/ParametrosFramework/ObtenerLocalidadesRecogidas")
    suspend fun getLocalities(): Response<List<ResponseDataLocalityDTO>>
}