package com.example.sitharatrad

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.sitharatrad.databinding.ActivityMainBinding
import com.example.sitharatrad.receivers.InternetCheckReceiver
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var internetCheckReceiver: InternetCheckReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        internetCheckReceiver = object : InternetCheckReceiver(){
            override fun onNetworkConnected() {
               Snackbar.make(binding.root,getString(R.string.internet_con),Snackbar.LENGTH_LONG).show()
            }

            override fun onNetworkDisConnected() {
                Snackbar.make(binding.root,getString(R.string.internet_discon),Snackbar.LENGTH_LONG).show()
            }

        }
        registerReceiver(internetCheckReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(internetCheckReceiver)
    }
}