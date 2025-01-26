package com.example.interrapidisimo.data.model.dto.request

import com.google.gson.annotations.SerializedName

data class RequestUserDTO(
    @SerializedName("terminalNumber") var terminalNumber: String? = null,
    @SerializedName("versionNumber") var versionNumber: String? = null

)