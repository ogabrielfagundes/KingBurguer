package com.example.kingburguer.data

import com.google.gson.annotations.SerializedName


sealed class LoginResponse {

    data class Success(
        @SerializedName("access_token")
        val accessToken: String,

        @SerializedName("refresh_token")
        val refreshToken: String,

        @SerializedName("token_type")
        val tokenType: String,

        @SerializedName("expires_seconds")
        val expiresSeconds: Double

    ) : LoginResponse()

    data class Error(val detail: String) : LoginResponse()

    data class  ErrorAuth(val detail: ErrorDetail) : LoginResponse()

}