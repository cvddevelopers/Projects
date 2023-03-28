package com.example.sitharatrad.Admin.Fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sitharatrad.Admin.AdminHomeActivity

import com.example.sitharatrad.R
import com.example.sitharatrad.User.UserHomeActivity
import com.example.sitharatrad.databinding.FragmentAdminAuthBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth


class AdminAuthFragment : Fragment() {
    lateinit var binding: FragmentAdminAuthBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var progressDialog: ProgressDialog
    private val sharedPrefile = "sharedPref"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_admin_auth, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please Wait ..... ")
        binding.uname.setText("admin@sitharatrad.in")
        binding.upass.setText("adminsithara")
        if (firebaseAuth.currentUser!=null){
            startActivity(Intent(context, AdminHomeActivity::class.java))
            requireActivity().finish()
        }
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(sharedPrefile,
            Context.MODE_PRIVATE)
        binding.login.setOnClickListener {
            progressDialog.show()
            firebaseAuth
                .signInWithEmailAndPassword(binding.uname.text.toString(),binding.upass.text.toString())
                .addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful){
                        progressDialog.dismiss()
                        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                        editor.putString("userType","Admin")
                        editor.apply()
                        //editor.commit()
                        startActivity(Intent(context,AdminHomeActivity::class.java))
                        activity?.finish()
                    }else{
                        progressDialog.dismiss()
                    Toast.makeText(context,"Please check the Credentials",Toast.LENGTH_LONG).show()
                    }
                })

        }
        return binding.root
    }

}