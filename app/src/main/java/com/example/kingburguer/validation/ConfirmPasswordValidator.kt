package com.example.kingburguer.validation

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kingburguer.R


class ConfirmPasswordValidator {

    fun validate(password: String, confirmPassword: String): TextString? {
        if (confirmPassword.isBlank()) {
            return ResourceString(R.string.error_confirm_password_blank)
        }

        if (password.isNotBlank() && confirmPassword != password) {
            return ResourceString(R.string.error_confirm_password_invalid)
        }

        return null
    }

}