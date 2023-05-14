package com.example.sitharatrad.Admin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.sitharatrad.MainActivity
import com.example.sitharatrad.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class AdminHomeActivity : AppCompatActivity() {

    lateinit var bottomNav: BottomNavigationView
    lateinit var fab: FloatingActionButton
    lateinit var firebaseAuth: FirebaseAuth
    private val sharedPrefile = "sharedPref"
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        bottomNav = findViewById(R.id.bottom_nav_view) as BottomNavigationView

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = this.getSharedPreferences(sharedPrefile, Context.MODE_PRIVATE)
        fab = findViewById(R.id.fab)
        bottomNav.background = null

        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        bottomNav.setupWithNavController(navController)
        fab.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(findViewById(R.id.nav_host_fragment))
                .navigate(R.id.navigation_ItemAdd)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        menu!!.getItem(0).icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_logout, null)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action -> {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("userType", "defaultname")
                editor.apply()
                firebaseAuth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

