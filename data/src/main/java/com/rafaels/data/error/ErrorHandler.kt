package com.rafaels.data.error

import com.rafaels.domain.error.ErrorEntity
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

class ErrorHandler {

    operator fun invoke(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException -> ErrorEntity.Network
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                    else -> ErrorEntity.Unknown
                }
            }
            else -> ErrorEntity.Unknown
        }
    }

    operator fun invoke(responseCode: Int, errorBody: ResponseBody?): ErrorEntity {
        return if (errorBody == null) {
            when (responseCode) {
                HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
                HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
                else -> ErrorEntity.Unknown
            }
        } else {
            val errorMessage = parseToError(errorBody)
            ErrorEntity.Message(errorMessage)
        }
    }

    private fun parseToError(errorBody: ResponseBody): String? {
        val json = JSONObject(errorBody.string())
        return json.getString("status")
    }

}