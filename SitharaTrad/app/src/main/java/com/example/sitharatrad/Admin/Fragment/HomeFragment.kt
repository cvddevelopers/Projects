package com.example.sitharatrad.Admin.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.R
import com.example.sitharatrad.databinding.FragmentHomeBinding
import com.google.firebase.database.*
import com.google.gson.Gson
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var cart: ArrayList<Cart>
    lateinit var reference: DatabaseReference
    lateinit var adapter:CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        cart = ArrayList<Cart>()
        reference = FirebaseDatabase.getInstance().getReference("data")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(Any::class.java)
                val json = Gson().toJson(data)
                val jsonArray = JSONArray(json)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val productName = jsonObject.getString("Product_name")
                    val productCost = jsonObject.getString("Product_cost")
                    val productDiscount = jsonObject.getString("Avaliability")
                    val productImage = jsonObject.getString("Image link")
                    val carts = Cart(productName, productDiscount, productCost, productImage)
                    cart.add(carts)

                }
                adapter  = CartAdapter(context, cart)
                binding.ahrv.adapter = adapter
                binding.ahrv.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error, Toast.LENGTH_LONG).show()
            }

        })
        binding.sview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return false
            }

        })
        return binding.root
    }
    fun filter(newText: String?) {
        val filteredlist = ArrayList<Cart>()
        for (item in cart) {
            if (item.product_name.toLowerCase().contains(newText!!.lowercase(Locale.getDefault()))) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            adapter.filterList(filteredlist)
        }
    }


    class CartAdapter(context: Context?, var cart: ArrayList<Cart>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
        val ct: Context? = context


        fun filterList(filterlist: ArrayList<Cart>) {
            cart = filterlist
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(ct).inflate(R.layout.cart_item, parent, false))
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
                v!!.findNavController().navigate(R.id.action_navigation_home_to_productDetailsFragment)
            }
        }

    }
}