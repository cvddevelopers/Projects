package com.example.sitharatrad.Admin.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.Model.CartUids
import com.example.sitharatrad.R
import com.example.sitharatrad.User.Fragment.CartFragment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class DashboardFragment : Fragment() {
    lateinit var reference: DatabaseReference
    lateinit var cart: ArrayList<CartUids>
    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        cart = ArrayList<CartUids>()
        list = ArrayList<String>()
        recyclerView = view.findViewById(R.id.rvs)
        reference = FirebaseDatabase.getInstance().getReference("Cart")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (datasnapshot in snapshot.children){
                        val data = datasnapshot.getValue(CartUids::class.java)
                        val keyValue = datasnapshot.ref.key.toString()
                        list.add(keyValue)
                        cart.add(data!!)
//                        for(data in datasnapshot.children){
//                            Toast.makeText(context,""+data,Toast.LENGTH_LONG).show()
//                            for(snap in data.children){
//                                val post = snap.getValue(Cart::class.java)
//                                cart.add(post!!)
//                            }
//                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context,""+error,Toast.LENGTH_LONG).show()
            }
               })
        val adapter  = CartAdapter(context,list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        return view
    }
}

class CartAdapter(context: Context?, val list:  ArrayList<String>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    val ct: Context? = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ct).inflate(R.layout.singleitem, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv1.text = list.get(position)
//        holder.tv2.text = cart.get(position).product_cost
//        holder.tv3.text = cart.get(position).product_discount
//       // Glide.with(ct!!).load(cart.get(position).product_image).into(holder.iv)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tv1: TextView = itemView.findViewById(R.id.tvsinleitem)
//        val tv2: TextView = itemView.findViewById(R.id.pcost)
//        val tv3: TextView = itemView.findViewById(R.id.pdiscount)
//        val iv: ImageView = itemView.findViewById(R.id.pimage)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            v!!.findNavController().navigate(R.id.action_nav_cart_to_productDetailsFragment)
            //findNavController().navigate(R.id.action_nav_cart_to_productDetailsFragment)
        }
    }
}