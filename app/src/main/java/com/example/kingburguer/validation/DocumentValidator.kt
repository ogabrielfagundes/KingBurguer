package com.example.kingburguer.validation

import android.util.Patterns
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kingburguer.R


class DocumentValidator {
    private val pattern = "###.###.###-##"
    lateinit var result: String

    fun validate(currentDocument: String, document: String): TextString? {
        result = Mask(pattern, currentDocument, document)
        
        if (result.isBlank()) {
            return ResourceString(R.string.error_document_blank)
        }

        if (result.length != pattern.length) {
            return ResourceString(R.string.error_document_invalid)
        }
        return null
    }

}