package com.example.mojivet.authentication.apiretrofit

import com.example.mojivet.authentication.data.ForgetPasswordRequest
import com.example.mojivet.authentication.data.LaravelResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotPasswordAPI {
    @POST("/api/forgotpassword-retrofit")
    fun forgotPassword(@Body forgotPasswordRequest: ForgetPasswordRequest): Call<LaravelResponse>
}