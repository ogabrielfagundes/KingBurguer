package com.example.kingburguer.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.compose.home.Product

class ProductViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val productId: Int = savedStateHandle["productId"] ?: 0
    val product by mutableStateOf(Product(id = productId))

    companion object {
        val factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                ProductViewModel(savedStateHandle)
            }
        }
    }
}