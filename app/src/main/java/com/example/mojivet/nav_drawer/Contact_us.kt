package com.example.mojivet.nav_drawer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mojivet.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Contact_us.newInstance] factory method to
 * create an instance of this fragment.
 */
class Contact_us : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false)
    }
    override fun onResume() {
        super.onResume()
        // Hide the bottom navigation view
        requireActivity().findViewById<View>(R.id.bottomNavigationView).visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        // Show the bottom navigation view
        requireActivity().findViewById<View>(R.id.bottomNavigationView).visibility = View.VISIBLE
    }
}