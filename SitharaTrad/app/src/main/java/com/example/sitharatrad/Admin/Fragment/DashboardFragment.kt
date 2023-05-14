package com.example.sitharatrad.Admin.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.sitharatrad.CartDetailsActivity
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.Model.CartUids
import com.example.sitharatrad.Model.Order
import com.example.sitharatrad.R
import com.example.sitharatrad.User.Fragment.CartFragment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class DashboardFragment : Fragment() {
    lateinit var reference: DatabaseReference
    lateinit var order: ArrayList<Order>
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        order = ArrayList<Order>()
        recyclerView = view.findViewById(R.id.rvs)
        reference = FirebaseDatabase.getInstance().getReference("Cart")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                order.clear()
                if(snapshot.exists()){
                    for (datasnapshot in snapshot.children){
                        val data = datasnapshot.getValue(Order::class.java)
                        order.add(data!!)
                    }
                }
                val adapter  = CartAdapter(context,order)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context,""+error,Toast.LENGTH_LONG).show()
            }
               })

        return view
    }
}

class CartAdapter(context: Context?, val list:  ArrayList<Order>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    val ct: Context? = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(ct).inflate(R.layout.buyer_cart , parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ostatus.setText(list.get(position).ostatus)
       // holder.ostatus.setBackgroundResource(R.color.trans_ordered)
        when(holder.ostatus.text){
            "Ordered" -> {holder.ostatus.setBackgroundResource(R.color.trans_ordered)}
            "Accepted" -> {holder.ostatus.setBackgroundResource(R.color.trans_accepted)}
            "InProgress" -> {holder.ostatus.setBackgroundResource(R.color.trans_inprogress)}
            "Completed" -> {holder.ostatus.setBackgroundResource(R.color.trans_completed)}
            "Failed" -> {holder.ostatus.setBackgroundResource(R.color.trans_failed)}
        }
        holder.oid.setText(list.get(position).oid)
        holder.osname.setText(list.get(position).uid)
        holder.oamount.setText(list.get(position).pcost)
        holder.odate.setText(list.get(position).odate)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val ostatus: TextView = itemView.findViewById(R.id.orderStatus)
        val oid: TextView = itemView.findViewById(R.id.orderId)
        val osname: TextView = itemView.findViewById(R.id.orderSname)
        val oamount: TextView = itemView.findViewById(R.id.orderAmount)
        val odate:TextView = itemView.findViewById(R.id.orderDate)

        init {
            itemView.setOnClickListener(this)
        }

        @SuppressLint("SuspiciousIndentation")
        override fun onClick(v: View?) {
          val i = Intent(v!!.context,CartDetailsActivity::class.java)
            i.putExtra("Oid",oid.text.toString())
            v.context.startActivity(i)
                  }
    }
}