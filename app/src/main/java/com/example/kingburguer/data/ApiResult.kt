package com.example.kingburguer.data

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String): ApiResult<Nothing>()

}

data class Error(val detail: String)
data class ErrorAuth(val detail: ErrorDetail)
data class ErrorDetail(val message: String)