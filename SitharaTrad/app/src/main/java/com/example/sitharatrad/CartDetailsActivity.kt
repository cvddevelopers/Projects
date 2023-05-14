package com.example.sitharatrad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.Model.Order
import com.example.sitharatrad.databinding.ActivityCartDetailsBinding
import com.google.firebase.database.*
import java.util.HashMap

class CartDetailsActivity : AppCompatActivity() {
    lateinit var binding : ActivityCartDetailsBinding
    lateinit var databaseReference: DatabaseReference
    lateinit var list : ArrayList<Order>
    lateinit var orderid:String
    lateinit var orderstatus:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart_details)
        val string = intent.getStringExtra("Oid")
        Log.i("OID",string.toString())
        list = ArrayList<Order>()
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart")
        databaseReference.keepSynced(true)
        databaseReference.child(string.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val data = snapshot.getValue(Order::class.java)
                    list.add(data!!)
                    orderid = data.oid.toString()
                    val userid = data.uid.toString()
                    val productname = data.pname.toString()
                    val productcost = data.pcost.toString()
                    val orderdate = data.odate.toString()
                    orderstatus = data.ostatus.toString()
                    val productimage = data.pimage.toString()
                    val orderquantity = data.pquantity.toString()
                    Glide.with(binding.root).load(productimage)
                        .placeholder(R.drawable.ic_launcher_background).into(binding.pimg)
                    binding.orderId.setText("Order Id : " + orderid)
                    binding.orderUid.setText("User Id : " + userid)
                    binding.orderPname.setText("Product Name : " + productname)
                    binding.orderCost.setText("Product Cost : " + productcost)
                    binding.orderDate.setText("Order date : " + orderdate)
                    binding.orderStatus.setText("Order Status : " + orderstatus)
                    binding.orderQuantity.setText("Order Cost : " + orderquantity)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CartDetailsActivity,""+error,Toast.LENGTH_LONG).show()
            }

        })
        binding.orderAccept.setOnClickListener {
            if (orderstatus == "Ordered") {
                val childUpdates = HashMap<String, Any>()
                childUpdates.put("ostatus", "Accepted")
                databaseReference.child(orderid).updateChildren(childUpdates)
                binding.orderAccept.isEnabled = false
            } else {
                binding.orderAccept.isEnabled = false
            }
        }
        binding.orderInprogress.setOnClickListener {
            if (orderstatus == "Accepted") {
                val childUpdates = HashMap<String, Any>()
                childUpdates.put("ostatus", "InProgress")
                databaseReference.child(orderid).updateChildren(childUpdates)
                binding.orderInprogress.isEnabled = false
            } else {
                binding.orderInprogress.isEnabled = false
            }
        }
        binding.orderCancel.setOnClickListener{
            if (orderstatus == "Ordered") {
                val childUpdates = HashMap<String, Any>()
                childUpdates.put("ostatus", "Failed")
                databaseReference.child(orderid).updateChildren(childUpdates)
                binding.orderCancel.isEnabled = false
            }else
            {
                binding.orderCancel.isEnabled = false
            }
        }
    }


}