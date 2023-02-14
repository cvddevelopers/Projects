package com.example.sitharatrad.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.sitharatrad.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserHomeActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)
        bottomNav = findViewById(R.id.bottom_navig_view)
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(R.id.nav_home,R.id.nav_profile,R.id.nav_cart).build()
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
        NavigationUI.setupWithNavController(bottomNav, navController)
    }

}