package com.example.sitharatrad.User.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.Model.User
import com.example.sitharatrad.R
import com.example.sitharatrad.User.UserHomeActivity
import com.example.sitharatrad.databinding.FragmentCartBinding
import com.example.sitharatrad.databinding.FragmentUserHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import org.json.JSONArray


class UserHomeFragment : Fragment() {
    lateinit var binding: FragmentUserHomeBinding
    lateinit var cart: ArrayList<Cart>
    lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_home, container, false)
        cart = ArrayList<Cart>()
        reference = FirebaseDatabase.getInstance().getReference("data")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(Any::class.java)
                val json = Gson().toJson(data)
                val jsonArray = JSONArray(json)
                Toast.makeText(context, "" + json, Toast.LENGTH_LONG).show()
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val productName = jsonObject.getString("Product_name")
                    val productCost = jsonObject.getString("Product_cost")
                    val productDiscount = jsonObject.getString("Avaliability")
                    val productImage = jsonObject.getString("Image link")
                    val carts = Cart(productName,productDiscount,productCost,productImage)
                    cart.add(carts)

                }
                val adapter  = CartAdapter(context,cart)
                binding.rvhome.adapter = adapter
                binding.rvhome.layoutManager = LinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL,false)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,""+error, Toast.LENGTH_LONG).show()
            }

        })
        return binding.root
    }

    class CartAdapter(context: Context?, cart: ArrayList<Cart>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
        val ct: Context? = context
        val cart:ArrayList<Cart> = cart
        lateinit var ref: DatabaseReference
        lateinit var firebaseAuth: FirebaseAuth

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            firebaseAuth = FirebaseAuth.getInstance()
            Toast.makeText(ct,firebaseAuth.currentUser!!.phoneNumber,Toast.LENGTH_LONG).show()
            ref = FirebaseDatabase.getInstance().getReference("Cart")
            return ViewHolder(LayoutInflater.from(ct).inflate(R.layout.cart_item,parent,false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tv1.text = cart.get(position).product_name
            holder.tv2.text = cart.get(position).product_cost
            holder.tv3.text = cart.get(position).product_discount
            Glide.with(ct!!).load(cart.get(position).product_image).into(holder.iv)
            holder.b.setOnClickListener(View.OnClickListener {
                val carts: Cart = Cart(cart.get(position).product_name,
                    cart.get(position).product_cost,
                    cart.get(position).product_discount,
                    cart.get(position).product_image)
                ref.child(""+firebaseAuth.currentUser!!.phoneNumber).child(cart.get(position).product_name).setValue(carts)
                Toast.makeText(ct, "Added to Cart", Toast.LENGTH_LONG).show()
            })
        }

        override fun getItemCount(): Int {
            return cart.size
        }
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            val tv1: TextView = itemView.findViewById(R.id.pname)
            val tv2: TextView = itemView.findViewById(R.id.pcost)
            val tv3: TextView = itemView.findViewById(R.id.pdiscount)
            val iv: ImageView = itemView.findViewById(R.id.pimage)
            val b : Button = itemView.findViewById(R.id.cartAdd)
            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                v!!.findNavController().navigate(R.id.action_nav_cart_to_productDetailsFragment)
                //findNavController().navigate(R.id.action_nav_cart_to_productDetailsFragment)
            }
        }
    }
}