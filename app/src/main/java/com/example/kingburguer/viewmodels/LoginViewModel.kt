package com.example.kingburguer.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.compose.login.LoginUiState
import com.example.kingburguer.compose.login.FormState
import com.example.kingburguer.compose.singup.FieldState
import com.example.kingburguer.data.KingBurguerRepository
import com.example.kingburguer.data.LoginRequest
import com.example.kingburguer.data.LoginResponse
import com.example.kingburguer.data.UserCreateResponse
import com.example.kingburguer.data.UserRequest
import com.example.kingburguer.validation.EmailValidator
import com.example.kingburguer.validation.PasswordValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class LoginViewModel(
    private val repository: KingBurguerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    var formState by mutableStateOf(FormState())

    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun reset() {
        _uiState.update { LoginUiState() }
    }


    fun updateEmail(newEmail: String) {

        val textString = emailValidator.validate(newEmail)
        // Aqui é o sucesso
        formState = formState.copy(
            email = FieldState(field = newEmail, error = textString, isValid = textString == null),
        )
        updateButton()
    }


    fun updatePassword(newPassword: String) {

        val textString = passwordValidator.validate(newPassword)
        // Aqui é o sucesso
        formState = formState.copy(
            password = FieldState(
                field = newPassword,
                error = textString,
                isValid = textString == null
            ),
        )

        updateButton()
    }

    fun updateRememberMe(rememberMe: Boolean) {
        formState = formState.copy(
            rememberMe = rememberMe
        )
    }

    private fun updateButton() {
        val formIsValid = with(formState) {
            email.isValid && password.isValid
        }

        formState = formState.copy(
            formIsValid = formIsValid
        )
    }

    fun send() {
        _uiState.update { it.copy(isLoading = true) }

        // simulação de latencia de rede / atraso de chamado
        viewModelScope.launch {
            with(formState) {

                val loginRequest = LoginRequest(
                    username = email.field,
                    password = password.field,
                )
                val service = KingBurguerService.create()
                val repository = KingBurguerRepository(service)

                val result = repository.login(loginRequest)
                Log.i("Teste", "content is $result")

                when (result) {
                    is LoginResponse.Success -> {
                        _uiState.update { it.copy(isLoading = false, goToHome = true) }
                    }

                    is LoginResponse.ErrorAuth -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.detail.message
                            )
                        }
                    }

                    is LoginResponse.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = result.detail) }
                    }
                }
            }
        }

    }
    companion object {
        val factory = viewModelFactory {
            initializer {
                val service = KingBurguerService.create()
                val repository = KingBurguerRepository(service)
                LoginViewModel(repository)
            }
        }
    }
}