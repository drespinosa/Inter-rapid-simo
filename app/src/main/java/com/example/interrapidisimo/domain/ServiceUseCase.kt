package com.example.interrapidisimo.domain

import com.example.interrapidisimo.data.network.exception.traceErrorException
import java.util.concurrent.CancellationException

abstract class ServiceUseCase<Type, in Params>() where Type : Any {

    abstract suspend fun run(request: Params? = null): Type

    suspend operator fun invoke(
        request: Params?,
        onResult: ServiceUseCaseResponse<Type>?
    ) {
        try {
            val result = run(request)
            onResult?.onSuccess(result)
        } catch (e: CancellationException) {
            e.printStackTrace()
            onResult?.onError(traceErrorException(e))
        } catch (e: Exception) {
            e.printStackTrace()
            onResult?.onError(traceErrorException(e))
        }
    }
}