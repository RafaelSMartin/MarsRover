package com.rafaels.domain.error

sealed class ErrorEntity(var message: String? = null) {

    object Network : ErrorEntity()

    object NotFound : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Unknown : ErrorEntity()

    class Message(message: String?) : ErrorEntity(message)

}