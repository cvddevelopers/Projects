package com.example.sitharatrad.Admin

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sitharatrad.CompleteDetails
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.R
import com.google.firebase.auth.FirebaseAuth

class pagerAdapter(context: Context?, val cart: ArrayList<Cart>) :
    RecyclerView.Adapter<pagerAdapter.ViewHolder>() {
    val ct: Context? = context
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var button: Button
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    var cost: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ct).inflate(R.layout.singleitem, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv1.text = cart.get(position).productname
        holder.tv2.text = cart.get(position).productcost
        holder.tv3.text = cart.get(position).productoffer
        Glide.with(ct!!).load(cart.get(position).productimage)
            .placeholder(R.drawable.ic_launcher_background).into(holder.iv)
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, CompleteDetails::class.java)
            intent.putExtra("pname", cart.get(position).productname)
            intent.putExtra("pcat", cart.get(position).productcat)
            it.context.startActivity(intent)
        }
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

        }
    }
}