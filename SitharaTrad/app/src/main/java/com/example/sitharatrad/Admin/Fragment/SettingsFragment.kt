package com.example.sitharatrad.Admin.Fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sitharatrad.MainActivity
import com.example.sitharatrad.R
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {
    lateinit var button: Button
    lateinit var firebaseAuth :FirebaseAuth
    private val sharedPrefile = "sharedPref"
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        sharedPreferences = requireActivity().getSharedPreferences(sharedPrefile, Context.MODE_PRIVATE)
        firebaseAuth = FirebaseAuth.getInstance()
        button = view.findViewById(R.id.signOut)
        button.setOnClickListener(View.OnClickListener {
            val editor: SharedPreferences.Editor =  sharedPreferences.edit()
            editor.putString("userType","defaultname")
            editor.apply()
            firebaseAuth.signOut()
            startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        })
        return view
    }

}