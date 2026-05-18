package com.example.kingburguer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.compose.home.CategoryUiState
import com.example.kingburguer.compose.home.HighlightUiState
import com.example.kingburguer.compose.home.HomeUiState
import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.data.KingBurguerLocalStorage
import com.example.kingburguer.data.KingBurguerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: KingBurguerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun fetchCategories() {
        _uiState.update { it.copy(categoryUiState = CategoryUiState(isLoading = true)) }
        viewModelScope.launch {
            val response = repository.fetchFeed()
            when(response) {
                is ApiResult.Error -> {
                    val state = CategoryUiState(isLoading = false, error = response.message)
                    _uiState.update { it.copy(categoryUiState = state) }
                }
                is ApiResult.Success -> {
                    val state = CategoryUiState(isLoading = false, categories = response.data.categories)
                    _uiState.update { it.copy(categoryUiState = state) }
                }
            }
        }
    }

    fun fetchHighlight() {
        _uiState.update { it.copy(highlightUiState = HighlightUiState(isLoading = true)) }
        viewModelScope.launch {
            val response = repository.fetchHighlight()
            when(response) {
                is ApiResult.Error -> {
                    val state = HighlightUiState(isLoading = false, error = response.message)
                    _uiState.update { it.copy(highlightUiState = state) }
                }
                is ApiResult.Success -> {
                    val state = HighlightUiState(isLoading = false, product = response.data)
                    _uiState.update { it.copy(highlightUiState = state) }
                }
            }
        }
    }

    init {
        fetchCategories()
        fetchHighlight()
    }


    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStorage(application)
                val repository = KingBurguerRepository(service, localStorage)
                HomeViewModel(repository)
            }
        }
    }
}