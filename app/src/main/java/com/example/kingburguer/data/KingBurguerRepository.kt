package com.example.kingburguer.data

import com.example.kingburguer.api.KingBurguerService
import com.google.gson.Gson

class KingBurguerRepository(
    private val service: KingBurguerService
) {

    suspend fun postUser(userRequest: UserRequest): UserCreateResponse {
        val response = service.postUser(userRequest)

        try {
            // error
            if (!response.isSuccessful) {
                val errorData = response.errorBody()?.toString()?.let { json ->
                    if (response.code() == 401) {
                        Gson().fromJson(json, UserCreateResponse.ErrorAuth::class.java)
                    } else {
                        Gson().fromJson(json, UserCreateResponse.Error::class.java)
                    }
                }

                return errorData ?: UserCreateResponse.Error("internal server error")
            }

            val data = response.body()?.string()?.let {  json ->
                Gson().fromJson(json, UserCreateResponse.Success::class.java)
            }
            return data?: UserCreateResponse.Error("unexpected response success")

        } catch (e: Exception) {
            return UserCreateResponse.Error(e.message ?: "unexpected exception")
        }
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        try {
            val response = service.login(loginRequest)
            if(!response.isSuccessful) {
                val errorData = response.errorBody()?.string()?.let { json ->
                    Gson().fromJson(json, LoginResponse.ErrorAuth::class.java)
                }

                return errorData ?: LoginResponse.Error("internal server error")
            }

            // sucesso
            val data = response.body()?.string()?.let { json ->
                Gson().fromJson(json, LoginResponse.Success::class.java)
            }

            return data ?: LoginResponse.Error("unexpected response success")

        } catch (e: Exception) {
            return LoginResponse.Error(e.message ?: "unexpected exception")
        }
    }
}