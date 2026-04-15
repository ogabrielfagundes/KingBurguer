package com.example.kingburguer.validation

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kingburguer.R

class PasswordValidator {

    fun validate(confirmPassword: String, password: String): TextString? {
        if (password.isBlank()) {
            return ResourceString(R.string.error_password_blank)
        }

        if (password.length < 8) {
            return ResourceString(R.string.error_password_invalid)
        }

        if (confirmPassword.isNotBlank() && confirmPassword != password) {
            return ResourceString(R.string.error_confirm_password_invalid)
        }

        return null
    }

}