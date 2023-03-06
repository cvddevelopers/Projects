package com.example.sitharatrad.User.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class UserProfileFragment : Fragment() {
    lateinit var binding : FragmentUserProfileBinding
    lateinit var reference: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_profile, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        Log.d("USERID",firebaseAuth.currentUser!!.uid)
        reference = FirebaseDatabase.getInstance().getReference("Profiles")
        reference.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val i = snapshot.childrenCount.toInt()
                if (i==0){
                    Toast.makeText(context,"No data Available , Please fill the details",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Welcome Back",Toast.LENGTH_LONG).show()
                    startActivity(Intent(context, UserHomeActivity::class.java))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,""+error,Toast.LENGTH_LONG).show()
            }

        })
        binding.save.setOnClickListener {
            saveDatatoFB();
        }
        return binding.root
    }

    private fun saveDatatoFB() {
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


}