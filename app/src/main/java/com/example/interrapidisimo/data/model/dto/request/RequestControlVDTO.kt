package com.example.interrapidisimo.data.model.dto.request

import com.google.gson.annotations.SerializedName

data class RequestControlVDTO(
    @SerializedName("terminalNumber") var terminalNumber: String? = null,
    @SerializedName("versionNumber") var versionNumber: Int? = 1

)