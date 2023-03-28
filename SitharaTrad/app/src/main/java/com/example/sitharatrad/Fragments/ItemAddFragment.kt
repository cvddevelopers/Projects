package com.example.sitharatrad.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.sitharatrad.R
import com.example.sitharatrad.databinding.FragmentHomeBinding
import com.example.sitharatrad.databinding.FragmentItemAddBinding


class ItemAddFragment : Fragment() {

    lateinit var binding: FragmentItemAddBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_add, container, false)
        return binding.root
    }

}