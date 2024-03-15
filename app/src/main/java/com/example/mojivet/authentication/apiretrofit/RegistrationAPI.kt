package com.example.mojivet.authentication.apiretrofit

import com.example.mojivet.authentication.data.LoginRequest
import com.example.mojivet.authentication.data.RegistrationRequest
import com.example.mojivet.authentication.data.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationAPI {
    @POST("/api/registration-retrofit")
    fun registration(@Body registration: RegistrationRequest): Call<TokenResponse>

}