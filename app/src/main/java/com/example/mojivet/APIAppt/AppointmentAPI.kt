package com.example.mojivet.APIAppt

import com.example.mojivet.data.AppointmentDatas
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface AppointmentAPI {
    @GET("/appointments")
    fun getAppointments(): Call<List<AppointmentDatas>>

    @POST("/appointments")
    fun createAppointment(@Body appointmentDatas: AppointmentDatas): Call<AppointmentDatas>

    @PUT("/appointments/{id}")
    fun updateAppointment(@Path("id") id: Int, @Body appointmentDatas: AppointmentDatas): Call<AppointmentDatas>

    @DELETE("/appointments/{id}")
    fun deleteAppointment(@Path("id") id: Int): Call<Void>
}