package com.example.mojivet.data

import java.sql.Time
import java.util.Date

data class AppointmentDatas(
    val name: String,
    val email: String,
    val date: Date,
    val appointment_time: Time,
    val pet_name: String,
    val pet_type: String,
    val veterinarian: String,
    val concern: String
)