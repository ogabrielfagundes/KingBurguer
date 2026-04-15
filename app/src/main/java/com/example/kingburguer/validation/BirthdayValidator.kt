package com.example.kingburguer.validation


import com.example.kingburguer.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.text.ParseException


class BirthdayValidator {

    val pattern = "##/##/####"
    lateinit var result: String

    fun validate(currentBirthday: String, birthday: String): TextString? {
        result = Mask(pattern, currentBirthday, birthday)
        if (result.isBlank()) {
            return ResourceString(R.string.error_birthday_blank)
        }

        // O número precisa igual da mascara = inválida
        if (result.length != pattern.length) {
            return ResourceString(R.string.error_birthday_invalid)
        }

        // Validar data
        try {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).run {
                isLenient = false
                parse(result)
            }?.also {
                //validar a data futura
                val now = Date()
                if (it.after(now)) {
                    return ResourceString(R.string.error_birthday_future_invalid)
                }
            }

        } catch (e: ParseException) {
            // invalida = 30/02/2000
            return ResourceString(R.string.error_birthday_invalid)
        }


        return null
    }
}

