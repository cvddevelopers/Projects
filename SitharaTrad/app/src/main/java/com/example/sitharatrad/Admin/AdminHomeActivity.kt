package com.example.sitharatrad.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.sitharatrad.MainActivity
import com.example.sitharatrad.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class AdminHomeActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var fab:FloatingActionButton
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        bottomNav = findViewById(R.id.bottom_nav_view) as BottomNavigationView
        firebaseAuth = FirebaseAuth.getInstance()
        fab = findViewById(R.id.fab)
        bottomNav.background = null
       // val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(R.id.navigation_home,R.id.navigation_dashboard,R.id.navigation_profile,R.id.navigation_settings).build()
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navController)
        fab.setOnClickListener(View.OnClickListener {
           Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.action_navigation_home_to_navigation_ItemAdd)
        })

    }
}

