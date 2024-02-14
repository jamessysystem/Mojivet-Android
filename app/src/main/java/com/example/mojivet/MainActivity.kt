package com.example.mojivet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import com.example.mojivet.nav_bottom.Appointment
import com.example.mojivet.nav_drawer.About_us
import com.example.mojivet.nav_drawer.Contact_us
import com.example.mojivet.nav_bottom.Home
import com.example.mojivet.nav_drawer.Photos
import com.example.mojivet.nav_drawer.Profile
import com.example.mojivet.nav_drawer.Veterinarian
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toogle = ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()
            navigationView.setCheckedItem(R.id.home)
        }
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            handleBottomNavItemClick(menuItem)
            true
        }
    }
    override fun onNavigationItemSelected(item:MenuItem):Boolean {
        when(item.itemId){
            R.id.nav_profile -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Profile()).commit()
            R.id.nav_veterinarian -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Veterinarian()).commit()
            R.id.nav_photos -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Photos()).commit()
            R.id.nav_contact_us -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Contact_us()).commit()
            R.id.nav_about_us -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, About_us()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun handleBottomNavItemClick(item:MenuItem){
        when(item.itemId){
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Home()).commit()
            R.id.nav_appointment -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, Appointment()).commit()
        }
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}