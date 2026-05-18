package com.example.kingburguer.data

data class UserCredentials(
    val accessToken: String = "",
    val refreshToken: String = "",
    val expiresTimestamp: Long = 0,
    val tokenType: String = ""
)