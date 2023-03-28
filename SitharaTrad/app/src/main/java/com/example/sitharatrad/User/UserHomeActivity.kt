package com.example.sitharatrad.User

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.sitharatrad.MainActivity
import com.example.sitharatrad.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class UserHomeActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var firebaseAuth : FirebaseAuth
    private val sharedPrefile = "sharedPref"
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)
        bottomNav = findViewById(R.id.bottom_navig_view)
        sharedPreferences = this.getSharedPreferences(sharedPrefile, Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(R.id.nav_home,R.id.nav_profile,R.id.nav_cart).build()
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
        NavigationUI.setupWithNavController(bottomNav, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu,menu)
        menu!!.getItem(0).icon = ResourcesCompat.getDrawable(resources,R.drawable.ic_logout,null)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action -> {
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("userType","defaultname")
                editor.apply()
                firebaseAuth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}