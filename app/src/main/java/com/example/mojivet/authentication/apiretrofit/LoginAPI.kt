package com.example.mojivet.authentication.apiretrofit

import com.example.mojivet.authentication.data.LoginRequest
import com.example.mojivet.authentication.data.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {
    @POST("/api/login-retrofit")
    fun login(@Body loginRequest: LoginRequest): Call<TokenResponse>
}