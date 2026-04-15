package com.example.kingburguer.validation

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kingburguer.R

interface TextString {
    @get:Composable
    val value: String
//    @Composable
//    fun asString(): String
}

class ResourceString(@StringRes private val input: Int): TextString {
    override val value: String
        @Composable
        get() = stringResource(input)
//    @Composable
//    override fun asString(): String {
//        return stringResource(input)
//    }
}

class RawString(private val input: String): TextString {
    override val value: String
        @Composable
        get() = input
//    @Composable
//    override fun asString(): String {
//        return input
//    }
}

class EmailValidator {

    fun validate(email: String): TextString? {
        if (email.isBlank()) {
            return ResourceString(R.string.error_email_blank)
        }

        if (!isEmailValid(email)) {
            return ResourceString(R.string.error_email_invalid)
        }
        return null
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}