package com.example.kingburguer.compose.singup

data class SignUpUiState(
    val isLoading: Boolean = false,
    val goToLogin: Boolean = false,
    val error: String? = null
)