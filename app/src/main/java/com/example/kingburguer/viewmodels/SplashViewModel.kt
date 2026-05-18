package com.example.kingburguer.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.data.ApiResult
import com.example.kingburguer.data.KingBurguerLocalStorage
import com.example.kingburguer.data.KingBurguerRepository
import com.example.kingburguer.data.LoginResponse
import com.example.kingburguer.data.RefreshTokenRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: KingBurguerRepository
): ViewModel() {


    private val _hasSessionState = MutableStateFlow<Boolean?>(null)
    val hasSession: StateFlow<Boolean?> = _hasSessionState

    init {
        viewModelScope.launch {
            with(repository.fetchInitialCredentials()) {
                _hasSessionState.value = when {
                    accessToken.isEmpty() -> false
                    System.currentTimeMillis() < expiresTimestamp -> true
                    else -> {
                        Log.d("SplashViewModel", "Token expired. Try refresh token.")
                        val response = repository.refreshToken(RefreshTokenRequest(refreshToken))
                        when(response) {
                            is ApiResult.Success -> true
                            else -> false
                        }
                    }
                }
            }
        }
    }


    companion object {

        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStorage(application)
                val repository = KingBurguerRepository(service, localStorage)
                SplashViewModel(repository)
            }
        }
    }
}