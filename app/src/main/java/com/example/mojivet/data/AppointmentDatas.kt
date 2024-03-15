package com.example.mojivet.data

import java.sql.Time
import java.util.Date

data class AppointmentDatas(
    val appointment_time: Time,
    val concern: String,
    val date: Date,
    val email: String,
    val name: String,
    val pet_name: String,
    val pet_type: String,
    val veterinarian: String,
    val user_type: String
)