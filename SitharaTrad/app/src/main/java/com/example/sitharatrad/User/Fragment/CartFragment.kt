package com.example.sitharatrad.User.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sitharatrad.Admin.Fragment.CartAdapter
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.Model.Order
import com.example.sitharatrad.R
import com.example.sitharatrad.databinding.FragmentCartBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import org.json.JSONArray


class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    lateinit var order: ArrayList<Order>
    lateinit var reference: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var uid:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        uid = firebaseAuth.currentUser!!.phoneNumber.toString()
        order = ArrayList<Order>()
        reference = FirebaseDatabase.getInstance().getReference("Cart")
        val query = reference.orderByChild("uid").equalTo(uid)
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (datasnapshot in snapshot.children){
                        val data = datasnapshot.getValue(Order::class.java)
                        order.add(data!!)
                    }
                }
                val adapter  = CartAdapter(context,order)
                binding.rvcart.adapter = adapter
                binding.rvcart.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)


            }

            override fun onCancelled(error: DatabaseError) {
              Toast.makeText(context,""+error,Toast.LENGTH_LONG).show()
            }

        })
//
//        reference.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val data = snapshot.getValue(Any::class.java)
//                val json = Gson().toJson(data)
//                val jsonArray = JSONArray(json)
//                Toast.makeText(context, "" + json, Toast.LENGTH_LONG).show()
//                for (i in 0 until jsonArray.length()) {
//                    val jsonObject = jsonArray.getJSONObject(i)
//                    val productName = jsonObject.getString("Product_name")
//                    val productCost = jsonObject.getString("Product_cost")
//                    val productDiscount = jsonObject.getString("Avaliability")
//                    val productImage = jsonObject.getString("Image link")
//                    val carts = Cart(productName,productDiscount,productCost,productImage)
//                    cart.add(carts)
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context,""+error, Toast.LENGTH_LONG).show()
//            }
//
//        })
        return binding.root

    }


}