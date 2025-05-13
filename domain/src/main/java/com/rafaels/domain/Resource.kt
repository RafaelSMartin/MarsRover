package com.rafaels.domain

import com.rafaels.domain.error.ErrorEntity

sealed class Resource<out R> {

    data class Success<out T>(val data: T) : Resource<T>()

    data class Error(val error: ErrorEntity) : Resource<Nothing>()
}