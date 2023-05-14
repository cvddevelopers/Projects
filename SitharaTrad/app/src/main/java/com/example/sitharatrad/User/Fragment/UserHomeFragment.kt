package com.example.sitharatrad.User.Fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.sitharatrad.Admin.pagerAdapter
import com.example.sitharatrad.CompleteDetails
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.Model.Order
import com.example.sitharatrad.R
import com.example.sitharatrad.databinding.FragmentUserHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class UserHomeFragment : Fragment() {
    lateinit var binding: FragmentUserHomeBinding
    lateinit var cart: ArrayList<Cart>
    lateinit var reference: DatabaseReference
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var uid: String
    var currentpage = -1
    val timer = Timer()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_home, container, false)
        cart = ArrayList<Cart>()
        firebaseAuth = FirebaseAuth.getInstance()
        uid = firebaseAuth.currentUser!!.phoneNumber.toString()
        reference = FirebaseDatabase.getInstance().getReference("ItemsData")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val key: String = it.key.toString()
                    if (key == "TopSelling") {
                        reference.child(key).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                cart.clear()
                                snapshot.children.forEach {
                                    val data = it.getValue(Cart::class.java)
                                    cart.add(data!!)
                                }
                                val adapter: CartAdapter = CartAdapter(context, cart)
                                binding.topSellingsrv.adapter = adapter
                                binding.topSellingsrv.layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    } else if (key == "TopBrands") {
                        reference.child(key).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                cart.clear()
                                snapshot.children.forEach {
                                    val data = it.getValue(Cart::class.java)
                                    cart.add(data!!)
                                }
                                val adapter: CartAdapter = CartAdapter(context, cart)
                                binding.topBrandsrv.adapter = adapter
                                binding.topBrandsrv.layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    } else if (key == "TopOffers") {
                        reference.child(key).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                cart.clear()
                                snapshot.children.forEach {
                                    val data = it.getValue(Cart::class.java)
                                    cart.add(data!!)
                                }
                                val adapter: CartAdapter = CartAdapter(context, cart)
                                binding.topBrandsrv.adapter = adapter
                                binding.topBrandsrv.layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")

                            }
                        })
                    } else {
                        reference.child(key).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                cart.clear()
                                snapshot.children.forEach {
                                    val data = it.getValue(Cart::class.java)
                                    cart.add(data!!)
                                }
                                val adapter = pagerAdapter(context, cart)
                                binding.topviewPager.adapter = adapter
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")

                            }
                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val data = snapshot.getValue(Any::class.java)
//                val json = Gson().toJson(data)
//                val jsonArray = JSONArray(json)
//                Toast.makeText(context, "" + json, Toast.LENGTH_LONG).show()
//                for (i in 0 until jsonArray.length()) {
//                    val jsonObject = jsonArray.getJSONObject(i)
//
//                    val productName = jsonObject.getString("Product_name")
//                    val productCost = jsonObject.getString("Product_cost")
//                    val productDiscount = jsonObject.getString("Avaliability")
//                    val productImage = jsonObject.getString("Image link")
//
//                    val carts = Cart(uid,productName,productDiscount,productCost,productImage)
//                    cart.add(carts)
//
//                }
//                val adapter  = CartAdapter(context,cart)
//                binding.rvhome.adapter = adapter
//                binding.rvhome.layoutManager = LinearLayoutManager(context,
//                    LinearLayoutManager.VERTICAL,false)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context,""+error, Toast.LENGTH_LONG).show()
//            }
//
//        })
        binding.topviewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        val handler = Handler()
        val update = Runnable() {
            binding.topviewPager.setCurrentItem(currentpage % 5, true)
            currentpage++
        }

        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 500, 1500)
        return binding.root
    }

    class CartAdapter(context: Context?, list: ArrayList<Cart>) :
        RecyclerView.Adapter<CartAdapter.ViewHolder>() {
        val ct: Context? = context
        val cart = list
        lateinit var ref: DatabaseReference
        lateinit var reference:DatabaseReference
        lateinit var firebaseAuth: FirebaseAuth
        lateinit var uid: String
        lateinit var button: Button
        lateinit var button1: Button
        lateinit var button2: Button
        lateinit var button3: Button
        lateinit var preview: Button
        lateinit var imageView: ImageView
        lateinit var textView: TextView
        lateinit var textView1: TextView
        lateinit var textView2: TextView
        var i: Int = 1
        var cost: Int = 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            firebaseAuth = FirebaseAuth.getInstance()
            uid = firebaseAuth.currentUser!!.phoneNumber.toString()
            //  Toast.makeText(ct,firebaseAuth.currentUser!!.phoneNumber,Toast.LENGTH_LONG).show()
            ref = FirebaseDatabase.getInstance().getReference("Cart")
            reference = FirebaseDatabase.getInstance().getReference("ItemsData")
            return ViewHolder(LayoutInflater.from(ct).inflate(R.layout.item_row, parent, false))
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tv1.text = cart.get(position).productname
            Glide.with(ct!!).load(cart.get(position).productimage)
                .placeholder(R.drawable.ic_launcher_background).into(holder.iv)
            holder.itemView.setOnClickListener {
                bsheetDialog(position)
            }

        }

        override fun getItemCount(): Int {
            return cart.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val tv1: TextView = itemView.findViewById(R.id.ptv)
            val iv: ImageView = itemView.findViewById(R.id.pciv)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bsheetDialog(position: Int) {
            val bottomSheetDailog = BottomSheetDialog(
                ct!!,
                com.google.android.material.R.style.ThemeOverlay_Material3_BottomSheetDialog
            )
            bottomSheetDailog.setContentView(R.layout.bottomsheet_dailog)
            bottomSheetDailog.show()
            button = bottomSheetDailog.findViewById<Button>(R.id.button1)!!
            button1 = bottomSheetDailog.findViewById<Button>(R.id.button2)!!
            button2 = bottomSheetDailog.findViewById<Button>(R.id.button3)!!
            button3 = bottomSheetDailog.findViewById<Button>(R.id.addcart)!!
            imageView = bottomSheetDailog.findViewById<ImageView>(R.id.cartimg)!!
            textView = bottomSheetDailog.findViewById<TextView>(R.id.pname)!!
            textView1 = bottomSheetDailog.findViewById<TextView>(R.id.pcost)!!
            textView2 = bottomSheetDailog.findViewById<TextView>(R.id.pdesc)!!
            preview = bottomSheetDailog.findViewById<Button>(R.id.preview)!!
            button2.setOnClickListener {
                i++
                button1.setText("" + i)
                //  button1.setBackgroundColor(R.color.white)
            }
            button.setOnClickListener {
                if (i <= 0) {
                    Toast.makeText(ct, "Please add some items first", Toast.LENGTH_LONG).show()
                } else {
                    i--
                    button1.setText("" + i)
                }
            }
            Glide.with(ct!!).load(cart.get(position).productimage).into(imageView)
            textView.setText(cart.get(position).productname)
            textView1.setText(cart.get(position).productcost)
            textView2.setText("Item Description")
            preview.setOnClickListener {
                val intent = Intent(it.context, CompleteDetails::class.java)
                intent.putExtra("pname", textView.text.toString())
                intent.putExtra("pcat", cart.get(position).productcat)
                it.context.startActivity(intent)
                // it!!.findNavController().navigate(R.id.action_nav_home_to_productDetailsFragment,bundle)
            }
            button3.setOnClickListener {
                val simpleDateFormat = SimpleDateFormat("yymmss", Locale.getDefault())
                val s = simpleDateFormat.format(Date())
                val n = uid.takeLast(5)
                val order = Order(
                    s + n,
                    cart.get(position).productname,
                    cart.get(position).productcost,
                    i.toString(), LocalDateTime.now().toString(),
                    cart.get(position).productimage, "Ordered", uid
                )
                val q = parseInt(cart.get(position).productquantity) - i
                val childUpdates=HashMap<String,Any>()
                childUpdates.put("productquantity",q.toString())
                reference.child(cart.get(position).productcat).child(cart.get(position).productname).updateChildren(childUpdates)
                ref.child(s + n).setValue(order)
            }
        }
    }
}