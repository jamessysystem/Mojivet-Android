package com.example.mojivet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import com.example.mojivet.authentication.AuthenticationManager
import com.example.mojivet.nav_bottom.Appointment
import com.example.mojivet.nav_drawer.About_us
import com.example.mojivet.nav_drawer.Contact_us
import com.example.mojivet.nav_bottom.Home
import com.example.mojivet.nav_drawer.Photos
import com.example.mojivet.nav_drawer.Profile
import com.example.mojivet.profiling.Login
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authManager = AuthenticationManager(this)
        val storedToken = authManager.getStoredToken()

        drawerLayout = findViewById(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toogle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        if (storedToken == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, Login())
                .commit()
            bottomNavigationView.visibility = View.GONE
            toolbar.visibility = View.GONE
        } else if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()
            bottomNavigationView.visibility =
                View.VISIBLE // Ensure the bottom navigation view is visible
            toolbar.visibility = View.VISIBLE // Ensure the toolbar is visible
        }


        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            handleBottomNavItemClick(menuItem)
            true
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()

            R.id.nav_profile -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Profile()).commit()

            R.id.nav_photos -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Photos()).commit()

            R.id.nav_contact_us -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Contact_us()).commit()

            R.id.nav_about_us -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, About_us()).commit()
            R.id.nav_logout ->{
                val authManager = AuthenticationManager(this) // Update to get instance
                authManager.storeToken(null)
                authManager.storeUserId(0)
                authManager.storeEmail(null)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Close the current activity
            }

            }
            drawerLayout.closeDrawer(GravityCompat.START)
                return true
        }

    private fun handleBottomNavItemClick(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()

            R.id.nav_appointment -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Appointment()).commit()
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}