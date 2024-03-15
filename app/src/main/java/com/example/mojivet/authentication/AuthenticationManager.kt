package com.example.mojivet.authentication

import android.content.Context

class AuthenticationManager(private val context: Context) {

    fun storeToken(token: String?) {
        val sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("authToken", token).apply()
    }

    fun getStoredToken(): String? {
        val sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences.getString("authToken", null)
    }

    fun storeUserId(userId: Int) {
        val sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt("userId", userId).apply()
    }

    fun getStoredUserId(): Int {
        val sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userId", 0)
    }

    fun storeEmail(email: String?) {
        val sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("email", email).apply()
    }

    fun getEmail(): String? {
        val sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPreferences.getString("email", null)
    }
}
