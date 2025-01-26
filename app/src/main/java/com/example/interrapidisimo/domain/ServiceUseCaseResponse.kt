package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.model.ApiError

interface ServiceUseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(apiError: ApiError?)
}