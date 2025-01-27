package com.example.interrapidisimo.data.model.dto.response.data

import com.google.gson.annotations.SerializedName

data class ResponseDataUserDTO(
    @SerializedName("Usuario") var user: String? = null,
    @SerializedName("Identificacion") var identification: String? = null,
    @SerializedName("Nombre") var name: String? = null
)