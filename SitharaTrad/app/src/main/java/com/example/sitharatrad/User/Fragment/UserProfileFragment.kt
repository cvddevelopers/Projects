package com.example.sitharatrad.User.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.sitharatrad.Admin.AdminHomeActivity
import com.example.sitharatrad.Model.User
import com.example.sitharatrad.R
import com.example.sitharatrad.User.UserHomeActivity
import com.example.sitharatrad.databinding.FragmentUserProfileBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserProfileFragment : Fragment() {
    lateinit var binding : FragmentUserProfileBinding
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_profile, container, false)
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Profiles")
        binding.save.setOnClickListener {
            val fname = binding.fname.text.toString()
            val lname = binding.lname.text.toString()
            val email = binding.uemail.text.toString()
            val number = binding.unumber.text.toString()
            val address = binding.uaddress.text.toString()
            val pincode = binding.upincode.text.toString()
            val shopname = binding.ushopname.text.toString()
            val gstin = binding.ugstin.text.toString()
            val user: User = User(fname, lname, email, number, address, pincode,shopname,gstin)
            reference.child(number).setValue(user)
            Toast.makeText(context, "Data Inserted", Toast.LENGTH_LONG).show()
            startActivity(Intent(context, UserHomeActivity::class.java))
        }
        return binding.root
    }


}