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
import com.example.kingburguer.compose.singup.FieldState
import com.example.kingburguer.compose.singup.FormState
import com.example.kingburguer.compose.singup.SignUpUiState
import com.example.kingburguer.data.KingBurguerRepository
import com.example.kingburguer.data.UserCreateResponse
import com.example.kingburguer.data.UserRequest
import com.example.kingburguer.validation.BirthdayValidator
import com.example.kingburguer.validation.ConfirmPasswordValidator
import com.example.kingburguer.validation.DocumentValidator
import com.example.kingburguer.validation.EmailValidator
import com.example.kingburguer.validation.NameValidator
import com.example.kingburguer.validation.PasswordValidator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class SignUpViewModel(private val repository: KingBurguerRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()
    var formState by mutableStateOf(FormState())
    private val nameValidator = NameValidator()
    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()
    private val confirmPasswordValidator = ConfirmPasswordValidator()
    private val documentValidator = DocumentValidator()
    private val birthdayValidator = BirthdayValidator()

    fun reset() {
        _uiState.update { SignUpUiState() }
    }

    fun updateName(newName: String) {

        val textString = nameValidator.validate(newName)
        // Aqui é o sucesso
        formState = formState.copy(
            name = FieldState(field = newName, error = textString, isValid = textString == null),
        )
        updateButton()
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

        var textString = passwordValidator.validate(formState.confirmPassword.field, newPassword)
        // Aqui é o sucesso
        formState = formState.copy(
            password = FieldState(
                field = newPassword,
                error = textString,
                isValid = textString == null
            ),
        )

        textString = confirmPasswordValidator.validate(newPassword, formState.confirmPassword.field)
        formState = formState.copy(
            confirmPassword = FieldState(
                field = formState.confirmPassword.field,
                error = textString,
                isValid = textString == null
            )
        )

        updateButton()
    }

    fun updatePasswordConfirm(newConfirmPassword: String) {

        var textString = confirmPasswordValidator.validate(
            confirmPassword = newConfirmPassword,
            password = formState.password.field
        )
        // Aqui é o sucesso
        formState = formState.copy(
            confirmPassword = FieldState(
                field = newConfirmPassword,
                error = textString,
                isValid = textString == null
            ),
        )

        textString = passwordValidator.validate(newConfirmPassword, formState.password.field)
        formState = formState.copy(
            password = FieldState(
                field = formState.password.field,
                error = textString,
                isValid = textString == null
            )
        )
        updateButton()
    }

    fun updateDocument(newDocument: String) {

        val textString = documentValidator.validate(formState.document.field, newDocument)
        formState = formState.copy(
            document = FieldState(
                field = documentValidator.result,
                error = textString,
                isValid = textString == null
            ),
        )
        updateButton()
    }

    fun updateBirthday(newBirthday: String) {

        val textString = birthdayValidator.validate(formState.birthday.field, newBirthday)
        formState = formState.copy(
            birthday = FieldState(
                field = birthdayValidator.result,
                error = textString,
                isValid = textString == null
            ),
        )
        updateButton()
    }

    private fun updateButton() {
        val formIsValid = with(formState) {
            email.isValid &&
                    name.isValid &&
                    password.isValid &&
                    confirmPassword.isValid &&
                    document.isValid &&
                    birthday.isValid
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
                val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .parse(birthday.field)

                val dateFormatted = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(date!!) // unwrap

                // 20/02/2000 (string) =>
                // Date (objeto) =>
                // 2000-02-20 formatada (string)

                val documentFormatted = document.field.filter { it.isDigit() }

                val userRequest = UserRequest(
                    name = name.field,
                    email = email.field,
                    password = password.field,
                    document = documentFormatted,
                    birthday = dateFormatted
                )
                val service = KingBurguerService.create()
                val repository = KingBurguerRepository(service)

                val result = repository.postUser(userRequest)
                Log.i("Teste", "content is $result")

                when(result) {
                    is UserCreateResponse.Success -> {
                        _uiState.update { it.copy(isLoading = false, goToLogin = true) }
                    }
                    is UserCreateResponse.ErrorAuth -> {
                        _uiState.update { it.copy(isLoading = false, error = result.detail.message) }
                    }
                    is UserCreateResponse.Error -> {
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
                SignUpViewModel(repository)
            }
        }
    }
}