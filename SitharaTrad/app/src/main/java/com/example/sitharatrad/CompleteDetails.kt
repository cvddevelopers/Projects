package com.example.sitharatrad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.Model.Pdata
import com.example.sitharatrad.databinding.ActivityCompleteDetailsBinding
import com.google.firebase.database.*

class CompleteDetails : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    lateinit var binding:ActivityCompleteDetailsBinding
    lateinit var list : ArrayList<Cart>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_complete_details)
        val s = intent.getStringExtra("pname").toString()
        val c = intent.getStringExtra("pcat").toString()
        list = ArrayList<Cart>()
        databaseReference = FirebaseDatabase.getInstance().getReference("ItemsData")
        databaseReference.child(c).child(s).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val data = snapshot.getValue(Cart::class.java)
                    list.add(data!!)
                    val productname = data.productname.toString()
                    val producttype = data.producttype.toString()
                    val productquanity = data.productquantity.toString()
                    val productoffer = data.productoffer.toString()
                    val productimage = data.productimage.toString()
                    val productcost = data.productcost.toString()
                    Glide.with(binding.root).load(productimage).placeholder(R.drawable.ic_launcher_background).into(binding.pimg)
                    binding.namep.setText("Product Name : "+productname)
                    binding.typep.setText("Product Type : "+producttype)
                    binding.costp.setText("Product Cost : "+productcost)
                    binding.quantityp.setText("Product Quantity : "+productquanity)
                    binding.offerp.setText("Product Offer : "+productoffer)


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}