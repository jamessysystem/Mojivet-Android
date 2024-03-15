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
    import com.example.mojivet.authentication.apiretrofit.RegistrationAPI
    import com.example.mojivet.authentication.data.RegistrationRequest
    import com.example.mojivet.authentication.data.TokenResponse
    import com.google.android.material.textfield.TextInputEditText
    import com.google.android.material.textfield.TextInputLayout
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response
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
                val passwordConfirmation = etrePassword.text?.trim().toString()
                val registrationRequest = RegistrationRequest(name, email, password, passwordConfirmation)
    
                if (etName.text.isNullOrEmpty()){
                    nameField.error = "Field is empty"
                    isName = false
                } else if(etName.text?.length!!  < 5){
                    nameField.error = "Should have at least 5 characters"
                    isName = false
                } else {
                    nameField.error = null
                    isName = true
                }
                if (etEmail.text.isNullOrEmpty()){
                    emailField.error = "Field is empty"
                    isEmail = false
                } else if(!etEmail.text.toString().contains(".com") && !emailField.editText.toString().contains("@")){
                    emailField.error = "Invalid email format"
                    isEmail = false
                } else {
                    emailField.error = null
                    isEmail = true
                }
    
                if (etPassword.text.isNullOrEmpty()){
                    passwordField.error = "Field is empty"
                    isPassword = false
                } else if (etPassword.text?.length!! < 8){
                    passwordField.error = "Should have at least 8 characters"
                    isPassword = false
                } else {
                    isPassword = true
                    passwordField.error = null
                }
    
                if (etrePassword.text.isNullOrEmpty()){
                    repasswordField.error = "Field is empty"
                    isConfirmPassword = false
                } else if (etrePassword.text.toString().trim() != etPassword.text.toString().trim()){
                    repasswordField.error = "Passwords do not match"
                    isConfirmPassword = false
                } else {
                    isConfirmPassword = true
                    repasswordField.error = null
                }
                if(isEmail && isConfirmPassword && isName && isPassword) {
                    service.registration(registrationRequest).enqueue(object : Callback<TokenResponse>{
                        override fun onResponse(
                            call: Call<TokenResponse>,
                            response: Response<TokenResponse>
                        ) {
                            if (response.isSuccessful && response.code() == 201) {
                                val receivedToken = response.body()?.token
                                val receivedEmail = response.body()?.email
                                val receivedUserId = response.body()?.userId
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
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                activity?.finish()
                            } else{
                                Log.e("MyTag", "Registration Failed: $call || ${response.body()}")
                            }
                        }
                        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                            emailField.error = "Email already taken"
                            Log.e("MyTag", "Network Failed: $call")
                        }
    
                    })
                }
            }
            return view
        }
    }