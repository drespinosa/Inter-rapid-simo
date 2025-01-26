package com.example.interrapidisimo.data.model

import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("quote") val name: String,
    @SerializedName("author") val description: String
)

data class Response(
    val code: Int,
    val message: String,
    val body: User?
)

data class User(
    val id: String?,
    val name: String?,
    val identification: String?
)
