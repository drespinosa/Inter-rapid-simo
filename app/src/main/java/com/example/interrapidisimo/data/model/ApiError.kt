package com.example.interrapidisimo.data.model


const val BAD_REQUEST_ERROR_MESSAGE = "Solicitud incorrecta"
const val FORBIDDEN_ERROR_MESSAGE = "Acceso denegado"
const val NOT_FOUND_ERROR_MESSAGE = "Recurso no encontrado"
const val METHOD_NOT_ALLOWED_ERROR_MESSAGE = "Método no permitido"
const val CONFLICT_ERROR_MESSAGE = "Conflicto en la solicitud"
const val UNAUTHORIZED_ERROR_MESSAGE = "No autorizado"
const val INTERNAL_SERVER_ERROR_MESSAGE = "Error interno del servidor"
const val NO_CONNECTION_ERROR_MESSAGE = "No hay conexión a Internet"
const val TIMEOUT_ERROR_MESSAGE = "Tiempo de espera agotado"
const val UNKNOWN_ERROR_MESSAGE = "Error desconocido"
const val SERVICE_UNAVAILABLE = "Servicio no disponible"
const val FAIL_CONNECTION_ERROR_MESSAGE = "No se pudo establecer la conexión"
const val UNKNOWN_IOEXCEPTION_ERROR_MESSAGE = "Error desconocido de entrada/salida"

data class ApiError(val message: String?, val code: Int?, var errorStatus: ErrorStatus) {

    constructor(message: String?, errorStatus: ErrorStatus) : this(message, null, errorStatus)

    fun getErrorMessage(): String {
        return when (errorStatus) {
            ErrorStatus.BAD_REQUEST -> BAD_REQUEST_ERROR_MESSAGE
            ErrorStatus.FORBIDDEN -> FORBIDDEN_ERROR_MESSAGE
            ErrorStatus.NOT_FOUND -> NOT_FOUND_ERROR_MESSAGE
            ErrorStatus.METHOD_NOT_ALLOWED -> METHOD_NOT_ALLOWED_ERROR_MESSAGE
            ErrorStatus.CONFLICT -> CONFLICT_ERROR_MESSAGE
            ErrorStatus.UNAUTHORIZED -> UNAUTHORIZED_ERROR_MESSAGE
            ErrorStatus.INTERNAL_SERVER_ERROR -> INTERNAL_SERVER_ERROR_MESSAGE
            ErrorStatus.NO_CONNECTION -> NO_CONNECTION_ERROR_MESSAGE
            ErrorStatus.TIMEOUT -> TIMEOUT_ERROR_MESSAGE
            ErrorStatus.UNKNOWN_ERROR -> UNKNOWN_ERROR_MESSAGE
            ErrorStatus.SERVICE_UNAVAILABLE -> SERVICE_UNAVAILABLE
            ErrorStatus.FAIL_CONNECTION -> FAIL_CONNECTION_ERROR_MESSAGE
            ErrorStatus.IOEXCEPTION_UNKNOWN_ERROR -> UNKNOWN_IOEXCEPTION_ERROR_MESSAGE
        }
    }

    enum class ErrorStatus {
        BAD_REQUEST,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        METHOD_NOT_ALLOWED,
        CONFLICT,
        INTERNAL_SERVER_ERROR,
        TIMEOUT,
        NO_CONNECTION,
        UNKNOWN_ERROR,
        SERVICE_UNAVAILABLE,
        FAIL_CONNECTION,
        IOEXCEPTION_UNKNOWN_ERROR,
    }
}