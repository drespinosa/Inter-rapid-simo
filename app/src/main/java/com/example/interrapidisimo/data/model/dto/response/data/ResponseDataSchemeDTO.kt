package com.example.interrapidisimo.data.model.dto.response.data

import com.google.gson.annotations.SerializedName

data class ResponseDataSchemeDTO(
    @SerializedName("NombreTabla") val name: String = "",
    @SerializedName("Pk") val primaryKey: String = "",
    @SerializedName("QueryCreacion") val query: String = "",
    @SerializedName("BatchSize") val size: String = "",
    @SerializedName("Filtro") val filter: String = "",
    @SerializedName("NumeroCampos") val field: String = "",
    @SerializedName("FechaActualizacionSincro") val updateDate: String = ""
)