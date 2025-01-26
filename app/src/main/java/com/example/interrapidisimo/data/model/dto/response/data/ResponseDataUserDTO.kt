package com.example.interrapidisimo.data.model.dto.response.data

import com.google.gson.annotations.SerializedName

data class ResponseDataUserDTO(
    @SerializedName("usuario") var user: String? = null,
    @SerializedName("identification") var identification: String? = null,
    @SerializedName("nombre") var name: String? = null
)