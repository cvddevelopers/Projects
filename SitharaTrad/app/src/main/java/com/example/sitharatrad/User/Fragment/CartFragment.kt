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
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.R
import com.example.sitharatrad.databinding.FragmentCartBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import org.json.JSONArray


class CartFragment : Fragment() {
    lateinit var binding: FragmentCartBinding
    lateinit var cart: ArrayList<Cart>
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
        cart = ArrayList<Cart>()
        reference = FirebaseDatabase.getInstance().getReference("Cart")
        val query = reference.orderByChild("uid").equalTo(uid)
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (datasnapshot in snapshot.children){
                        val data = datasnapshot.getValue(Cart::class.java)
                        cart.add(data!!)
                    }
                }
                val adapter  = CartAdapter(context,cart)
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
        Toast.makeText(context,"No Data Available",Toast.LENGTH_LONG).show()
        return binding.root

    }

    class CartAdapter(context: Context?, cart: ArrayList<Cart>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
        val ct: Context? = context
        val cart:ArrayList<Cart> = cart
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(ct).inflate(R.layout.cart_item,parent,false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tv1.text = cart.get(position).product_name
            holder.tv2.text = cart.get(position).product_cost
            holder.tv3.text = cart.get(position).product_discount
            Glide.with(ct!!).load(cart.get(position).product_image).into(holder.iv)
        }

        override fun getItemCount(): Int {
            return cart.size
        }
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            val tv1: TextView = itemView.findViewById(R.id.pname)
            val tv2: TextView = itemView.findViewById(R.id.pcost)
            val tv3: TextView = itemView.findViewById(R.id.pdiscount)
            val iv: ImageView = itemView.findViewById(R.id.pimage)
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