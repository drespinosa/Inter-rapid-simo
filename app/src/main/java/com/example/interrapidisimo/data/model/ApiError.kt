package com.example.interrapidisimo.data.model


const val BAD_REQUEST_ERROR_MESSAGE = "Bad Request"
const val FORBIDDEN_ERROR_MESSAGE = "Forbidden"
const val NOT_FOUND_ERROR_MESSAGE = "Not Found"
const val METHOD_NOT_ALLOWED_ERROR_MESSAGE = "Method Not Allowed"
const val CONFLICT_ERROR_MESSAGE = "Conflict"
const val UNAUTHORIZED_ERROR_MESSAGE = "Unauthorized"
const val INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server error"
const val NO_CONNECTION_ERROR_MESSAGE = "No Connection"
const val TIMEOUT_ERROR_MESSAGE = "Time Out"
const val UNKNOWN_ERROR_MESSAGE = "Unknown Error"
const val SERVICE_UNAVAILABLE = "Service Unavailable"
const val FAIL_CONNECTION_ERROR_MESSAGE = "Failed Connection"
const val UNKNOWN_IOEXCEPTION_ERROR_MESSAGE = "IOException Unknown Error"

/**
 * Designed to show different types of errors through error status & message
 *
 * */
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

    /**
     * Various error status to know what happened if something goes wrong with a repository call
     */
    enum class ErrorStatus {
        /**
         * Any case where a parameter is invalid, or a required parameter is missing.
         * This includes the case where no OAuth token is provided and
         * the case where a resource ID is specified incorrectly in a path.
         */
        BAD_REQUEST,

        /**
         * The OAuth token was provided but was invalid.
         */
        UNAUTHORIZED,

        /**
         * The requested information cannot be viewed by the acting user, for example,
         * because they are not friends with the user whose data they are trying to read.
         * It could also indicate privileges or access has been revoked.
         */
        FORBIDDEN,

        /**
         * Endpoint does not exist.
         */
        NOT_FOUND,

        /**
         * Attempting to use POST with a GET-only endpoint, or vice-versa.
         */
        METHOD_NOT_ALLOWED,

        /**
         * The request could not be completed as it is. Use the information included in the response to modify the request and retry.
         */
        CONFLICT,

        /**
         * There is either a bug on our side or there is an outage.
         * The request is probably valid but needs to be retried later.
         */
        INTERNAL_SERVER_ERROR,

        /**
         * Time out  error
         */
        TIMEOUT,

        /**
         * Error in connecting to repository (Server or Database)
         */
        NO_CONNECTION,

        /**
         * When error is not known
         */
        UNKNOWN_ERROR,

        /**
         * When error is service not available
         */
        SERVICE_UNAVAILABLE,

        /**
         * Error in connecting to repository (Server or Database), there was a connection failure
         */
        FAIL_CONNECTION,

        /**
         * Error in IOException and error is not known
         */
        IOEXCEPTION_UNKNOWN_ERROR,
    }
}