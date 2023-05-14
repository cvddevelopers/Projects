package com.example.sitharatrad.Admin.Fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.example.sitharatrad.MainActivity
import com.example.sitharatrad.Model.Cart
import com.example.sitharatrad.Model.User
import com.example.sitharatrad.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class SettingsFragment : Fragment() {
    lateinit var button: Button
    lateinit var reference: DatabaseReference
    lateinit var recyclerView: RecyclerView
    lateinit var list : ArrayList<User>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        recyclerView = view.findViewById(R.id.userrv)
        list = ArrayList<User>()
        reference = FirebaseDatabase.getInstance().getReference("Profiles")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()){
                   snapshot.children.forEach {
                        val data = it.getValue(User::class.java)
                        list.add(data!!)
                        val adapter = UserAdapter(context,list)
                        recyclerView.adapter = adapter
                       recyclerView.layoutManager =LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                   }
               }else{
                   Toast.makeText(context,"No Data Available",Toast.LENGTH_LONG).show()
               }
            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context,""+error,Toast.LENGTH_LONG).show()
            }

        })
        return view
    }

}
class UserAdapter(context: Context?, list: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    val ct: Context? = context
    val user = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      return ViewHolder(LayoutInflater.from(ct).inflate(R.layout.user_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.setText(user.get(position).pnumber)
        holder.tv.setText(user.get(position).fname+" "+user.get(position).lname)
       // Glide.with(ct).load(user.get())
    }

    override fun getItemCount(): Int {
       return user.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView:TextView = itemView.findViewById(R.id.userid)
            val tv:TextView = itemView.findViewById(R.id.username)
            val imageView:ImageView = itemView.findViewById(R.id.userpic)
    }
}