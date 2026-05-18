package com.example.kingburguer.compose.profile

import com.example.kingburguer.data.ProfileResponse

data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: ProfileResponse? = null,
    val error: String? = null
)
