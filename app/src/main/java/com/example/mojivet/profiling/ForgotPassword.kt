package com.example.mojivet.profiling

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.mojivet.R
import com.example.mojivet.authentication.apiretrofit.ForgotPasswordAPI
import com.example.mojivet.authentication.apiretrofit.RegistrationAPI
import com.example.mojivet.authentication.data.ForgetPasswordRequest
import com.example.mojivet.authentication.data.LaravelResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForgotPassword : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://143.198.95.241/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val etEmail = view.findViewById<TextInputEditText>(R.id.etEmail)
        val fieldEmail = view.findViewById<TextInputLayout>(R.id.emailField)

        val service = retrofit.create(ForgotPasswordAPI::class.java)
        val email = etEmail.text?.trim().toString()
        val btnforgot = view.findViewById<Button>(R.id.btn_forgot)

        val forgotPasswordRequest = ForgetPasswordRequest(email)

        btnforgot.setOnClickListener {
            service.forgotPassword(forgotPasswordRequest).enqueue(object : Callback<LaravelResponse>{
                override fun onResponse(
                    call: Call<LaravelResponse>,
                    response: Response<LaravelResponse>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        Log.e("MyTag", "${response.code()} ${response.message()}")
                    } else {
                        Log.e(
                            "MyTag",
                            "Forgot Password Failed: ${response.code()} ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<LaravelResponse>, t: Throwable) {
                    Log.e("MyTag", "Network Failed: $call", t)
                }
            })


        }
        return view
    }
}
