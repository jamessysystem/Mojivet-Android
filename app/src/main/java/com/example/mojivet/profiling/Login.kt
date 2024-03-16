package com.example.mojivet.profiling

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.mojivet.MainActivity
import com.example.mojivet.R
import com.example.mojivet.authentication.AuthenticationManager
import com.example.mojivet.authentication.apiretrofit.LoginAPI
import com.example.mojivet.authentication.data.LoginRequest
import com.example.mojivet.authentication.data.TokenResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : Fragment() {

    private var isEmail = false
    private var isPassword = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://143.198.95.241/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val etEmail = view.findViewById<TextInputEditText>(R.id.et_email)
        val etPassword = view.findViewById<TextInputEditText>(R.id.et_password)
        val emailField = view.findViewById<TextInputLayout>(R.id.lay_email)
        val passField = view.findViewById<TextInputLayout>(R.id.lay_pass)

        val service = retrofit.create(LoginAPI::class.java)

        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val tvsignup = view.findViewById<TextView>(R.id.sign_uphere)
        val tvforgot = view.findViewById<TextView>(R.id.tv_forgot)

        tvforgot.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, ForgotPassword())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text?.trim().toString()
            val password = etPassword.text?.trim().toString()
            val loginRequest = LoginRequest(email, password)

            Log.e("MyTag", "asd $loginRequest")
            if (etEmail.text.isNullOrEmpty()) {
                emailField.error = "Field is empty"
                isEmail = false
            } else if (!etEmail.text.toString()
                    .contains(".com") && !emailField.editText.toString().contains("@")
            ) {
                Log.e("MyTag", emailField.editText.toString().contains(".com").toString())
                emailField.error = "The email address is incomplete"
                isEmail = false
            } else {
                Log.e("MyTagg", emailField.editText.toString().contains(".com").toString())
                emailField.error = null
                isEmail = true
            }
            if (passField.editText?.text.isNullOrEmpty()) {
                passField.error = "Field is empty"
                isPassword = false
            } else {
                isPassword = true
                passField.error = null
            }
            if (isEmail && isPassword) {
                service.login(loginRequest)
                    .enqueue(object : Callback<TokenResponse> {
                        override fun onResponse(
                            call: Call<TokenResponse>,
                            response: Response<TokenResponse>
                        ) {
                            if (response.isSuccessful && response.code() == 200) {
                                Log.e(
                                    "MyTag",
                                    "Login Success: $call || ${response.body()?.token}"
                                )
                                val receivedToken = response.body()?.token
                                val receivedEmail = response.body()?.email
                                val receivedUserId = response.body()?.userId
                                Log.d(
                                    "MyTag",
                                    "$receivedToken || $receivedEmail || $receivedUserId"
                                )
                                val authManager = AuthenticationManager(requireContext())
                                if (receivedToken != null) {
                                    authManager.storeToken(receivedToken)
                                }
                                if (receivedEmail != null) {
                                    authManager.storeEmail(receivedEmail)
                                }
                                if (receivedUserId != null) {
                                    authManager.storeUserId(receivedUserId)
                                }
                                val intent = Intent(activity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                activity?.finish()
                            } else {
                                emailField.error = " "
                                passField.error = "The email or password you entered is incorrect"
                                Log.e("MyTag", "Login Failed: $call || ${response.raw()}")
                            }
                        }

                        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                            Log.e("MyTag", "Network Failed: $call", t)
                        }
                    })
            }
        }
            tvsignup.setOnClickListener {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, Signup())
                transaction.addToBackStack(null) // Optional: Adds the transaction to the back stack
                transaction.commit()
            }

            return view
        }
    }
