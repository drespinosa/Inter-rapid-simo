package com.example.interrapidisimo.data.model.dto.response.data

import com.google.gson.annotations.SerializedName

data class ResponseDataLocalityDTO(
    @SerializedName("AbreviacionCiudad") var abbreviation: String? = null,
    @SerializedName("NombreCompleto") var city: String? = null,
)