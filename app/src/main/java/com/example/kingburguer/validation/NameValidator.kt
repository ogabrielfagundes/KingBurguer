package com.example.kingburguer.validation

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kingburguer.R


class NameValidator {

    fun validate(name: String): TextString? {
        if (name.isBlank()) {
            return ResourceString(R.string.error_name_blank)
        }

        if (name.length < 3) {
            return ResourceString(R.string.error_name_invalid)
        }
        return null
    }

}