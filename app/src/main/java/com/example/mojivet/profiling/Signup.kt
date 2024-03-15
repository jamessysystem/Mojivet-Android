package com.example.mojivet.profiling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.mojivet.R
import com.example.mojivet.authentication.apiretrofit.LoginAPI
import com.example.mojivet.authentication.apiretrofit.RegistrationAPI
import com.example.mojivet.authentication.data.RegistrationRequest
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Signup : Fragment() {

    private var isName = false
    private var isEmail = false
    private var isPassword = false
    private var isConfirmPassword = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_signup, container, false)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://143.198.95.241/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(RegistrationAPI::class.java)

        val loginhere = view.findViewById<TextView>(R.id.log_inhere)
        val etName = view.findViewById<TextInputEditText>(R.id.et_signname)
        val etEmail = view.findViewById<TextInputEditText>(R.id.et_signemail)
        val etPassword = view.findViewById<TextInputEditText>(R.id.et_signpass)
        val etrePassword = view.findViewById<TextInputEditText>(R.id.et_confirmPass)
        val nameField = view.findViewById<TextInputLayout>(R.id.namebox)
        val emailField = view.findViewById<TextInputLayout>(R.id.emailbox)
        val passwordField = view.findViewById<TextInputLayout>(R.id.passbox)
        val repasswordField = view.findViewById<TextInputLayout>(R.id.rePassbox)
        val btnregis = view.findViewById<Button>(R.id.btn_regis)

        loginhere.setOnClickListener{
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, Login())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        btnregis.setOnClickListener{
            val name = etName.text?.trim().toString()
            val email = etEmail.text?.trim().toString()
            val password = etPassword.text?.trim().toString()
            val repassword = etrePassword.text?.trim().toString()
            val registrationrequest = RegistrationRequest(name, email, password, repassword)

            if (nameField.editText?.text.isNullOrEmpty()){
                nameField.error = "Field is empty"
                isName = false
            } else if(nameField.editText?.text?.length!!  < 5){
                nameField.error = "Should have at least 5 characters"
                isName = false
            } else {
                nameField.error = null
                isName = true
            }
            if (emailField.editText?.text.isNullOrEmpty()){
                emailField.error = "Field is empty"
                isEmail = false
            } else if(!emailField.editText.toString().contains(".com") && !emailField.editText.toString().contains("@")){
                emailField.error = "The email address is incomplete"
                isEmail = false
            } else {
                emailField.error = null
                isEmail = true
            }

            if (passwordField.editText?.text.isNullOrEmpty()){
                passwordField.error = "Field is empty"
                isPassword = false
            } else if (passwordField.editText?.text?.length!! < 8){
                passwordField.error = "Should have at least 8 characters"
                isPassword = false
            } else {
                isPassword = true
                passwordField.error = null
            }

            if (repasswordField.editText?.text.isNullOrEmpty()){
                repasswordField.error = "Field is empty"
                isConfirmPassword = false
            } else if (repasswordField.editText.toString().trim() != passwordField.editText.toString().trim()){
                repasswordField.error = "Passwords do not match"
                isConfirmPassword = false
            } else {
                isConfirmPassword = true
                repasswordField.error = null
            }

        }
        return view
    }
}