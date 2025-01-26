package com.example.interrapidisimo.data.network.exception

import com.example.interrapidisimo.data.model.ApiError
import com.example.interrapidisimo.data.model.UNKNOWN_ERROR_MESSAGE
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun traceErrorException(throwable: Throwable?): ApiError {

    return when (throwable) {

        is HttpException -> {
            when (throwable.code()) {
                400 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ApiError.ErrorStatus.BAD_REQUEST
                )
                401 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ApiError.ErrorStatus.UNAUTHORIZED
                )
                403 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ApiError.ErrorStatus.FORBIDDEN
                )
                404 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ApiError.ErrorStatus.NOT_FOUND
                )
                405 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ApiError.ErrorStatus.METHOD_NOT_ALLOWED
                )
                409 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ApiError.ErrorStatus.CONFLICT
                )
                500 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ApiError.ErrorStatus.INTERNAL_SERVER_ERROR
                )
                503 -> ApiError(
                    throwable.message(),
                    throwable.code(),
                    ApiError.ErrorStatus.SERVICE_UNAVAILABLE
                )
                else -> ApiError(
                    UNKNOWN_ERROR_MESSAGE,
                    0,
                    ApiError.ErrorStatus.UNKNOWN_ERROR
                )
            }
        }

        is SocketTimeoutException -> {
            ApiError(throwable.message, ApiError.ErrorStatus.TIMEOUT)
        }

        is IOException -> {
            var messageStr = throwable.message ?:""

            when {
                (messageStr.contains("Unable to resolve host", true)) ->
                    ApiError(throwable.message, ApiError.ErrorStatus.NO_CONNECTION)

                (messageStr.contains("Failed to connect", true)) ->
                    ApiError(throwable.message, ApiError.ErrorStatus.FAIL_CONNECTION)

                else ->
                    ApiError(throwable.message, ApiError.ErrorStatus.IOEXCEPTION_UNKNOWN_ERROR)
            }
        }

        else -> ApiError(UNKNOWN_ERROR_MESSAGE, 0, ApiError.ErrorStatus.UNKNOWN_ERROR)
    }
}