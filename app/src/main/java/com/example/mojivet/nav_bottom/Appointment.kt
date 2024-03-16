package com.example.mojivet.nav_bottom

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.mojivet.APIAppt.AppointmentAPI
import com.example.mojivet.AppointmentResult
import com.example.mojivet.R
import com.example.mojivet.data.AppointmentDatas
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.time.LocalTime

class Appointment : Fragment() {
    private lateinit var spinnervet: Spinner
    private lateinit var spinnertype: Spinner
    private lateinit var spinnertime: Spinner
    private lateinit var datebut: Button
    private val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointment, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://143.198.95.241/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AppointmentAPI::class.java)

        val aptName = view.findViewById<TextInputEditText>(R.id.apt_name)
        val aptEmail = view.findViewById<TextInputEditText>(R.id.apt_email)
        val aptPetName = view.findViewById<TextInputEditText>(R.id.apt_petname)
        val aptConcern = view.findViewById<TextInputEditText>(R.id.apt_concern)
        val btnSubmit = view.findViewById<Button>(R.id.apt_submit)
        spinnervet = view.findViewById(R.id.spin_vet)
        datebut = view.findViewById(R.id.spin_date)
        spinnertype = view.findViewById(R.id.spin_pettype)
        spinnertime = view.findViewById(R.id.spin_time)

        btnSubmit.setOnClickListener {
            val selectedTime = spinnertime.selectedItem.toString()
            val concern = aptConcern.text?.trim().toString()
            val dateText = datebut.text.toString()
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val date = dateFormat.parse(dateText)
            val email = aptEmail.text?.trim().toString()
            val name = aptName.text?.trim().toString()
            val pet_name = aptPetName.text?.trim().toString()
            val pet_type = spinnertype.selectedItem?.toString()?:""
            val veterinarian = spinnervet.selectedItem?.toString()?:""

            // Remove the redeclaration of appointment_time here

            // Parse the selected time string into a java.sql.Time object
            val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            val parsedTime = timeFormat.parse(selectedTime)
            val appointmentTime = Time(parsedTime.time) // Change variable name to avoid conflict

            // Use appointmentTime in your AppointmentDatas object
            val appointmentDatas = AppointmentDatas(
                name, email, date, appointmentTime, pet_name, pet_type, veterinarian, concern
            )

            service.createAppointment(appointmentDatas)
                .enqueue(object : Callback<AppointmentDatas> {
                    override fun onResponse(
                        call: Call<AppointmentDatas>,
                        response: Response<AppointmentDatas>
                    ) {
                        (response.isSuccessful)///Success(?)
                        val fragmentTransaction = childFragmentManager.beginTransaction()
                        fragmentTransaction.add(R.id.nav_appointment, AppointmentResult())
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }

                    override fun onFailure(call: Call<AppointmentDatas>, t: Throwable) {
                        Log.e("MyTag", "Network Failed: $call")
                    }
                })



        }

        val vetchoices = arrayOf("Veterinarian 1", "Veterinarian 2", "Veterinarian 3")
        val typeChoices = arrayOf("Cat", "Dog", "Hamster", "Rabbit")
        val timeChoices = arrayOf(
            "8:00 AM - 9:00 AM",
            "9:00 AM - 10:00 AM",
            "10:00 AM - 11:00 AM",
            "11:00 AM - 12:00 PM",
            "1:00 PM - 2:00 PM",
            "2:00 PM - 3:00 PM",
            "3:00 PM - 4:00 PM",
            "4:00 PM - 5:00 PM"
        )

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, vetchoices)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnervet.adapter = adapter

        val typeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeChoices)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnertype.adapter = typeAdapter

        val timeAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeChoices)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnertime.adapter = timeAdapter


        // Optionally, you can add a listener to handle spinner item selection
        spinnervet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent?.getItemAtPosition(position).toString()
                // Do something with the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }
        spinnertype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                // Do something with the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }
        spinnertime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                // Do something with the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }
        datebut.setOnClickListener {
            showDatePicker()
        }
        return view
    }

    private fun showDatePicker(){
        val datePickerDialog = DatePickerDialog( requireContext(),{DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            datebut.text = formattedDate
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }
}