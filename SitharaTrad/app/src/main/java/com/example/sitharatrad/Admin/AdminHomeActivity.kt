package com.example.sitharatrad.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.sitharatrad.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminHomeActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var fab:FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        bottomNav = findViewById(R.id.bottom_nav_view)
        fab = findViewById(R.id.fab)
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(R.id.navigation_home,R.id.navigation_dashboard,R.id.navigation_profile,R.id.navigation_settings).build()
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNav.background = null
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
        NavigationUI.setupWithNavController(bottomNav, navController)
        fab.setOnClickListener(View.OnClickListener {
            Toast.makeText(this,"Yet to Implement", Toast.LENGTH_LONG).show()
        })
    }
}