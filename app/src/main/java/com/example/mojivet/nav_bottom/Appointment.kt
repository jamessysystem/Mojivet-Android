package com.example.mojivet.nav_bottom

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.mojivet.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_appointment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnervet = view.findViewById(R.id.spin_vet)
        datebut = view.findViewById(R.id.spin_date)
        spinnertype = view.findViewById(R.id.spin_pettype)
        spinnertime = view.findViewById(R.id.spin_time)


        val vetchoices = arrayOf("Veterinarian 1", "Veterinarian 2", "Veterinarian 3")
        val typeChoices = arrayOf("Cat", "Dog", "Hamster", "Rabbit")
        val timeChoices = arrayOf("8:00AM - 9:00 AM", "9:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM", "1:00 PM - 2:00 PM", "2:00 PM - 3:00 PM", "3:00 PM - 4:00 PM", "4:00 PM - 5:00 PM")

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, vetchoices)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnervet.adapter = adapter

        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeChoices)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnertype.adapter = typeAdapter

        val timeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeChoices)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnertime.adapter = timeAdapter


        // Optionally, you can add a listener to handle spinner item selection
        spinnervet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                // Do something with the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }
        spinnertype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                // Do something with the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do something when nothing is selected
            }
        }
        spinnertime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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
    }
    private fun showDatePicker(){
        val datePickerDialog = DatePickerDialog( requireContext(),{DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, monthOfYear, dayOfMonth)
            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.time)
            datebut.text = "Selected Date:" + formattedDate
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }
}