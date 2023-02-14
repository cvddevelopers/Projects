package com.example.sitharatrad.User.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sitharatrad.Admin.AdminHomeActivity
import com.example.sitharatrad.R
import com.example.sitharatrad.User.UserHomeActivity
import com.example.sitharatrad.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {
    lateinit var binding : FragmentUserProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_profile, container, false)
        binding.save.setOnClickListener({
            startActivity(Intent(context, UserHomeActivity::class.java))
        })
        return binding.root
    }


}