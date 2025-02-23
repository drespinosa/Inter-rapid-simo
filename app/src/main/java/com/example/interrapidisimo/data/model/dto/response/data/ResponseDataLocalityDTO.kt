package com.example.interrapidisimo.data.model.dto.response.data

import com.google.gson.annotations.SerializedName

data class ResponseDataLocalityDTO(
    @SerializedName("AbreviacionCiudad") var abbreviation: String? = null,
    @SerializedName("NombreCompleto") var fullName: String? = null,
    @SerializedName("Nombre") var name: String? = null,
    @SerializedName("CodigoPostal") var postal: String? = null,
    @SerializedName("ValorRecogida") var amount: Float? = null
)