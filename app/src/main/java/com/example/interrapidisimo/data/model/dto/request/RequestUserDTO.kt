package com.example.interrapidisimo.data.model.dto.request

import com.google.gson.annotations.SerializedName

data class RequestUserDTO(
    @SerializedName("Mac") var mac: String? = null,
    @SerializedName("NomAplicacion") var nameApp: String? = null,
    @SerializedName("Password") var password: String? = null,
    @SerializedName("Path") var path: String? = null,
    @SerializedName("Usuario") var user: String? = null
)