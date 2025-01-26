package com.example.interrapidisimo.data.model.dto.response.data

import com.google.gson.annotations.SerializedName

data class ResponseDataLocalityDTO(
    @SerializedName("abreviacion") var abbreviation: String? = null,
    @SerializedName("ciudad") var city: String? = null,
)