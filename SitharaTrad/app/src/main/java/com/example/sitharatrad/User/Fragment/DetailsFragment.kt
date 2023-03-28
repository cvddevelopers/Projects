package com.example.sitharatrad.User.Fragment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.sitharatrad.Model.User
import com.example.sitharatrad.R
import com.example.sitharatrad.databinding.FragmentDetailsBinding
import com.example.sitharatrad.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener as ValueEventListener1


class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    lateinit var database: FirebaseDatabase
    lateinit var reference: DatabaseReference
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_details, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Profiles")
        reference.child("8106410134").addValueEventListener(object :
            ValueEventListener1 {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user==null){
                    Toast.makeText(context,"No Data Available",Toast.LENGTH_LONG).show()
                }else{
                    binding.ufname.setText(user.fname+" "+user.lname)
                    binding.uemail.setText(user.email)
                    binding.unumber.setText(user.pnumber)
                    binding.uaddress.setText(user.address)
                    binding.upincode.setText(user.pin)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,""+error,Toast.LENGTH_LONG).show()
            }

        })
        return binding.root
    }

}