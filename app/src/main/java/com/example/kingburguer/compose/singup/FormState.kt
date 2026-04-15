package com.example.kingburguer.compose.singup

import com.example.kingburguer.validation.TextString


data class FieldState(
    var field: String = "",
    val error: TextString? = null,
    val isValid: Boolean = false
)
data class FormState(
    val name: FieldState = FieldState(),
    val email: FieldState = FieldState(),
    val password: FieldState = FieldState(),
    val confirmPassword: FieldState = FieldState(),
    val document: FieldState = FieldState(),
    val birthday: FieldState = FieldState(),
    val formIsValid: Boolean = false
)