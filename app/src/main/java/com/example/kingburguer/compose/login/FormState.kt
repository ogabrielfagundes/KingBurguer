package com.example.kingburguer.compose.login

import com.example.kingburguer.compose.singup.FieldState


data class FormState(
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val rememberMe: Boolean = false,
    val formIsValid: Boolean = false
)