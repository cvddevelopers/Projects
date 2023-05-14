package com.example.sitharatrad

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.sitharatrad.Model.Order
import com.example.sitharatrad.Model.Pdata
import com.example.sitharatrad.databinding.FragmentProductDetailsBinding
import com.google.firebase.database.*


class ProductDetailsFragment : Fragment() {
    lateinit var reference: DatabaseReference
    lateinit var binding:FragmentProductDetailsBinding
    lateinit var list:ArrayList<Pdata>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)
        list = ArrayList<Pdata>()
       val s = requireArguments().getString("pname").toString()
        reference = FirebaseDatabase.getInstance().getReference("data")
        val query = reference.orderByChild("Product_name").equalTo(s)
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()){
                   val data = snapshot.getValue(Pdata::class.java)
                   list.add(data!!)
                   val productname = data.product_name.toString()
                   val productcost = data.product_cost.toString()
                   val expirydate = data.expiryDate.toString()
                   val availability = data.availability.toString()
                   val productimage = data.imageLink.toString()
                   val productype = data.product_type.toString()
                   Glide.with(binding.root).load(productimage)
                       .placeholder(R.drawable.ic_launcher_background).into(binding.pimg)
                   binding.namep.setText("Product Name : " + productname)
                   binding.costp.setText("Product Cost : " + productcost)
                   binding.datee.setText("Expiry Date : " + expirydate)
                   binding.typep.setText("Product Type : " + productype)
                   binding.quantityp.setText("Availability : " + availability)
               }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return view

    }

}